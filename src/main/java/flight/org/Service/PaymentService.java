package flight.org.Service;

import flight.org.DTO.Request.PaymentRequestDTO;
import flight.org.DTO.Response.PaymentResponseDTO;
import flight.org.Entity.Booking;
import flight.org.Entity.Payment;
import flight.org.Entity.enums.ModeOfPayment;
import flight.org.Entity.enums.PaymentStatus;
import flight.org.Exception.BookingNotFoundException;
import flight.org.Exception.InvalidPaymentStatusException;
import flight.org.Exception.PaymentNotFoundException;
import flight.org.Mapper.PaymentMapper;
import flight.org.Repository.BookingRepository;
import flight.org.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentMapper mapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<PaymentResponseDTO> getAllPayments() {
        List<Payment> payments=paymentRepository.findAll();
        List<PaymentResponseDTO> responseDTOS=mapper.toDTOList(payments);
        return responseDTOS;
    }


    public PaymentResponseDTO getPaymentById(long id) {
        Payment payment=paymentRepository.findById(id).orElseThrow(()->new PaymentNotFoundException("Payment not found with id: "+id));
        PaymentResponseDTO responseDTO=mapper.toDTO(payment);
        return responseDTO;
    }


    public PaymentResponseDTO updatePaymentStatus(long id, PaymentStatus status) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
        PaymentStatus currentStatus=payment.getPaymentStatus();
        if(currentStatus==status){
            throw new InvalidPaymentStatusException("Payment is already "+ status);
        }
        if(currentStatus==PaymentStatus.PENDING && status==PaymentStatus.SUCCESS){
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
        }else if(currentStatus==PaymentStatus.PENDING && status==PaymentStatus.FAILED){
            payment.setPaymentStatus(PaymentStatus.FAILED);
        }else{
            throw  new RuntimeException("Invalid payment status transition: "+ currentStatus+"->"+status);
        }
        Payment updatedPayment = paymentRepository.save(payment);
        return mapper.toDTO(updatedPayment);
    }


    public List<PaymentResponseDTO> getPaymentsByStatus(PaymentStatus paymentStatus) {
        List<Payment> payments=paymentRepository.findByPaymentStatus(paymentStatus);
        List<PaymentResponseDTO> responseDTOS=mapper.toDTOList(payments);
        return  responseDTOS;
    }


    public List<PaymentResponseDTO> getPaymentsByMode(ModeOfPayment modeOfPayment) {
        List<Payment> payments=paymentRepository.findByModeOfPayment(modeOfPayment);
        List<PaymentResponseDTO> responseDTOS=mapper.toDTOList(payments);
        return  responseDTOS;
    }


    public double getTotalAmountPaidByFlight(long flightId) {
        return paymentRepository.getTotalAmountPaidByFlight(flightId, PaymentStatus.SUCCESS);
    }


    public PaymentResponseDTO getPaymentByBooking(Long bookingId) {
        Payment payment=paymentRepository.findByBookingBookId(bookingId).orElseThrow(()-> new PaymentNotFoundException("Payment not found for booking id : "+bookingId));
        PaymentResponseDTO responseDTO=mapper.toDTO(payment);
        return responseDTO;
    }

    public PaymentResponseDTO addPayment(PaymentRequestDTO requestDTO) {
        Payment payment=mapper.toEntity(requestDTO);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentDateTime(LocalDateTime.now());
        Payment payment1=paymentRepository.save(payment);
        PaymentResponseDTO paymentResponseDTO=mapper.toDTO(payment1);
        return paymentResponseDTO;
    }
}
