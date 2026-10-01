package flight.org.Service;

import flight.org.DTO.Request.PassengerRequestDTO;
import flight.org.DTO.Response.PassengerResponseDTO;
import flight.org.Entity.Booking;
import flight.org.Entity.Flight;
import flight.org.Entity.Passenger;
import flight.org.Entity.enums.Gender;
import flight.org.Exception.BookingNotFoundException;
import flight.org.Exception.FlightNotFoundException;
import flight.org.Exception.PassengerNotFoundException;
import flight.org.Mapper.PassengerMapper;
import flight.org.Repository.BookingRepository;
import flight.org.Repository.FlightRepository;
import flight.org.Repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PassengerService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerMapper mapper;

    public PassengerResponseDTO savePassenger(PassengerRequestDTO dto) {
        Passenger passenger = mapper.toEntity(dto);
        Passenger savePassenger = passengerRepository.save(passenger);
        PassengerResponseDTO responseDTO = mapper.toDTO(savePassenger);
        return responseDTO;
    }


    public List<PassengerResponseDTO> getAllPassengers() {
        List<Passenger> passengers = passengerRepository.findAll();
        List<PassengerResponseDTO> responseDTOS = mapper.toDTOList(passengers);
        return responseDTOS;
    }


    public PassengerResponseDTO getPassengerById(long id) {
        Passenger passenger = passengerRepository.findById(id).orElseThrow(() -> new PassengerNotFoundException("Passenger not found with id: " + id));
        PassengerResponseDTO responseDTO = mapper.toDTO(passenger);
        return responseDTO;
    }


    public PassengerResponseDTO getPassengerByContact(String contact) {
        Passenger passenger = passengerRepository.findByContact(contact).orElseThrow(() -> new PassengerNotFoundException("Passenger not found with contact: " + contact));
        PassengerResponseDTO responseDTO = mapper.toDTO(passenger);
        return responseDTO;
    }


    public List<PassengerResponseDTO> getPassengerByGender(Gender gender) {
        List<Passenger> passenger = passengerRepository.findByGender(gender);
        if (passenger.isEmpty()) {
            throw new PassengerNotFoundException("Passengers are not found with gender: " + gender);
        }
        List<PassengerResponseDTO> responseDTO = mapper.toDTOList(passenger);
        return responseDTO;
    }


    public PassengerResponseDTO updatePassenger(long id, PassengerRequestDTO dto) {
        Passenger passenger = passengerRepository.findById(id).orElseThrow(() -> new PassengerNotFoundException("Passenger not found with id: " + id));
        passenger.setName(dto.getName());
        passenger.setAge(dto.getAge());
        passenger.setGender(dto.getGender());
        passenger.setContact(dto.getContact());
        Passenger updatePassenger = passengerRepository.save(passenger);
        PassengerResponseDTO responseDTO = mapper.toDTO(updatePassenger);
        return responseDTO;
    }


    public List<PassengerResponseDTO> getPassengerByFlight(long id) {
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new FlightNotFoundException("Flight is not found with id: " + id));
        List<Passenger> passengers = passengerRepository.findByBookingFlightFlightId(id);
        if (passengers.isEmpty()) {
            throw new PassengerNotFoundException("No Passengers are found with id: " + id);
        }
        List<PassengerResponseDTO> responseDTO = mapper.toDTOList(passengers);
        return responseDTO;
    }


    public List<PassengerResponseDTO> getPassengerByBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));
        List<Passenger> passengers = booking.getPassengers();
        List<PassengerResponseDTO> responseDTOS = mapper.toDTOList(passengers);
        return responseDTOS;
    }
}