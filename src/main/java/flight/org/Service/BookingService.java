package flight.org.Service;

import flight.org.DTO.Request.BookingRequestDTO;
import flight.org.DTO.Response.BookingResponseDTO;
import flight.org.Entity.Booking;
import flight.org.Entity.Flight;
import flight.org.Entity.Passenger;
import flight.org.Entity.Payment;
import flight.org.Entity.enums.BookingStatus;
import flight.org.Entity.enums.PaymentStatus;
import flight.org.Exception.*;
import flight.org.Mapper.BookingMapper;
import flight.org.Repository.BookingRepository;
import flight.org.Repository.FlightRepository;
import flight.org.Repository.PassengerRepository;
import flight.org.Repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BookingMapper mapper;

    @Transactional
    public BookingResponseDTO saveBooking(BookingRequestDTO dto) {
        Flight flight=flightRepository.findById(dto.getFlightId()).orElseThrow(()-> new FlightNotFoundException("Flight not Found with id:"+dto.getFlightId()));

        Payment payment=paymentRepository.findById(dto.getPaymentId()).orElseThrow(()-> new PaymentNotFoundException("Payment not fount exception with id: "+dto.getPaymentId()));
        if(payment.getPaymentStatus() != PaymentStatus.SUCCESS){
            throw  new PaymentFailedException("Booking failed because payment is failed");
        }
        List<Passenger> passengers=passengerRepository.findAllById(dto.getPassengerIds());
        if(passengers.size()!=dto.getPassengerIds().size()){
            throw  new PassengerNotFoundException("One or more passengers do not exist");
        }
        int numberOfSeats=passengers.size();
        if(flight.getAvailableSeats()< numberOfSeats){
            throw new InsufficientSeatException("Not enough available seats");
        }
        for(int i = 0; i < passengers.size(); i++){
            int seatNumber=passengers.get(i).getSeatNumber();
            if(seatNumber < 1 || seatNumber > flight.getTotalSeats()){
                throw  new InvalidSeatException("Invalid seat Number: "+ seatNumber);
            }
            for(int j=i+1;j<passengers.size();j++){
                if(seatNumber==passengers.get(j).getSeatNumber()){
                    throw new SeatAlreadyBookedException("Seat "+seatNumber+" is selected for multiple passengers");
                }
            }
            boolean seatAlreadyBooked = passengerRepository.existsByBookingFlightFlightIdAndBookingStatusAndSeatNumber(flight.getFlightId(), BookingStatus.CONFIRMED, seatNumber);
            if(seatAlreadyBooked){
                throw new SeatAlreadyBookedException("Seat "+ seatNumber+" is already booked");
            }
        }
        double totalAmount=flight.getPrice()*numberOfSeats;
        if(payment.getPrice()!=totalAmount){
            throw new PaymentAmountMismatchException("Payment amount does not match booking amount");
        }
        flight.setAvailableSeats(flight.getAvailableSeats()-numberOfSeats);
        flightRepository.save(flight);

        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setPayment(payment);
        booking.setPassengers(passengers);
        payment.setBooking(booking);
        booking.setBookingDateTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setTotalAmount(totalAmount);
        for (Passenger passenger : passengers) {
            passenger.setBooking(booking);
        }
        Booking saveBooking=bookingRepository.save(booking);
        passengerRepository.saveAll(passengers);
        BookingResponseDTO responseDTO=mapper.toDTO(saveBooking);
        return responseDTO;
    }



    public List<BookingResponseDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingResponseDTO> responseDTOS=mapper.toDTOList(bookings);
        return  responseDTOS;
    }



    public BookingResponseDTO getBookingById(Long id) {
        Booking booking=bookingRepository.findById(id).orElseThrow(()->new BookingNotFoundException("Booking not found with id :"+id));
        BookingResponseDTO responseDTO=mapper.toDTO(booking);
        return responseDTO;
    }



    public List<BookingResponseDTO>  getBookingByFlight(Long id) {
        List<Booking> booking=bookingRepository.findByFlightFlightId(id);
        List<BookingResponseDTO> responseDTO=mapper.toDTOList(booking);
        return responseDTO;
    }



    public List<BookingResponseDTO> getBookingsByDateTime(LocalDate date) {
        LocalDateTime start=date.atStartOfDay();
        LocalDateTime end=date.plusDays(1).atStartOfDay();
        List<Booking> bookings=bookingRepository.findByBookingDateTimeGreaterThanEqualAndBookingDateTimeLessThan(start,end);
        List<BookingResponseDTO> responseDTOS=mapper.toDTOList(bookings);
        return responseDTOS;
    }


    public List<BookingResponseDTO> getBookingsByStatus(BookingStatus status) {
        List<Booking> bookings = bookingRepository.findByStatus(status);
        List<BookingResponseDTO> responseDTOS=mapper.toDTOList(bookings);
        return responseDTOS;
    }


    @Transactional
    public BookingResponseDTO updateBookingStatus(Long bookingId, BookingStatus newStatus) {
        Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new BookingNotFoundException("Booking not found with id: "+bookingId));
        BookingStatus currentStatus=booking.getStatus();
        if(currentStatus == newStatus){
            throw new InvalidBookingStatusException("Booking is already "+newStatus);
        }

        if(currentStatus==BookingStatus.PENDING && newStatus==BookingStatus.CONFIRMED){
            booking.setStatus(BookingStatus.CONFIRMED);
        }
        else if(currentStatus==BookingStatus.CONFIRMED&& newStatus==BookingStatus.COMPLETED){
            booking.setStatus(BookingStatus.COMPLETED);
        }
        else if(currentStatus==BookingStatus.CONFIRMED && newStatus==BookingStatus.CANCELLED){
            Flight flight=booking.getFlight();
            int numberOfPassengers=booking.getPassengers().size();
            flight.setAvailableSeats(flight.getAvailableSeats()+numberOfPassengers);
            flightRepository.save(flight);
            Payment payment=booking.getPayment();
            if (payment != null && (payment.getPaymentStatus() == PaymentStatus.SUCCESS || payment.getPaymentStatus() == PaymentStatus.PARTIALLY_REFUNDED)) {
                payment.setPaymentStatus(PaymentStatus.REFUNDED);
                paymentRepository.save(payment);
            }
            booking.setStatus(BookingStatus.CANCELLED);
        }
        else {
            throw new InvalidBookingStatusTransactionException("Invalid status transition: " + currentStatus + " → " + newStatus);
        }
        Booking updatedBooking=bookingRepository.save(booking);
        BookingResponseDTO responseDTO=mapper.toDTO(updatedBooking);
        return responseDTO;
    }


    @Transactional
    public void deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));
        Payment payment = booking.getPayment();
        if (payment != null) {
            payment.setBooking(null);
            paymentRepository.saveAndFlush(payment);
        }
        if (booking.getPassengers() != null) {
            for (Passenger passenger : booking.getPassengers()) {
                passenger.setBooking(null);
            }
            passengerRepository.saveAll(booking.getPassengers());
        }
        bookingRepository.delete(booking);
    }



    @Transactional
    public BookingResponseDTO cancelPassengerFromBooking(Long bookingId, Long passengerId) {
        Booking booking=bookingRepository.findById(bookingId).orElseThrow(()-> new BookingNotFoundException("Booking not found with id : "+bookingId));
        if(booking.getStatus()!=BookingStatus.CONFIRMED){
            throw  new InvalidBookingStatusException("Passenger cannot be cancelled from this booking");
        }

        Passenger passengerToCancel = null;
        for (Passenger passenger : booking.getPassengers()) {
            if (passenger.getPassengerId().equals(passengerId)) {
                passengerToCancel = passenger;
                break;
            }
        }
        if (passengerToCancel == null) {
            throw new PassengerNotFoundException("Passenger not found in this booking");
        }

        Flight flight=booking.getFlight();
        Payment payment=booking.getPayment();
        if (payment == null) {
            throw new PaymentNotFoundException("Payment not found for this booking");
        }
        booking.getPassengers().remove(passengerToCancel);
        passengerToCancel.setBooking(null);

        flight.setAvailableSeats(flight.getAvailableSeats()+1);
        flightRepository.save(flight);

        double newTotalAmount=flight.getPrice()*booking.getPassengers().size();
        booking.setTotalAmount(newTotalAmount);

        if(booking.getPassengers().isEmpty()){
            booking.setStatus(BookingStatus.CANCELLED);
            payment.setPaymentStatus(PaymentStatus.REFUNDED);
        }else{
            booking.setStatus(BookingStatus.CONFIRMED);
            payment.setPaymentStatus(
                    PaymentStatus.PARTIALLY_REFUNDED
            );
        }
        paymentRepository.save(payment);
        passengerRepository.save(passengerToCancel);
        Booking savedBooking = bookingRepository.save(booking);
        return mapper.toDTO(savedBooking);
    }



    public List<BookingResponseDTO> getBookingHistoryOfPassenger(Long passengerId) {
        passengerRepository.findById(passengerId).orElseThrow(()-> new PassengerNotFoundException("Passenger Not found with id :"+passengerId));
        List<Booking> bookings=bookingRepository.findByPassengersPassengerId(passengerId);
        List<BookingResponseDTO> responseDTOS=mapper.toDTOList(bookings);
        return responseDTOS;
    }
}
