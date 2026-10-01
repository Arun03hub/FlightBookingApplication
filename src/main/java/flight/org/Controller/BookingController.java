package flight.org.Controller;

import flight.org.DTO.Request.BookingRequestDTO;
import flight.org.DTO.Response.BookingResponseDTO;
import flight.org.DTO.Response.PassengerResponseDTO;
import flight.org.DTO.Response.PaymentResponseDTO;
import flight.org.Entity.enums.BookingStatus;
import flight.org.Service.BookingService;
import flight.org.Service.PassengerService;
import flight.org.Service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<BookingResponseDTO> saveBooking(@Valid @RequestBody BookingRequestDTO dto){
        BookingResponseDTO responseDTO=bookingService.saveBooking(dto);
        return  ResponseEntity.ok(responseDTO);
    }

    @GetMapping()
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings(){
        List<BookingResponseDTO> responseDTOS=bookingService.getAllBookings();
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id){
        BookingResponseDTO responseDTO=bookingService.getBookingById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/flight/{id}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingByFlight(@PathVariable Long id){
        List<BookingResponseDTO> responseDTO=bookingService.getBookingByFlight(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/date")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByDate(@RequestParam LocalDate date){
        List<BookingResponseDTO> responseDTOS=bookingService.getBookingsByDateTime(date);
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/status")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByStatus(@RequestParam BookingStatus status) {
        List<BookingResponseDTO> responseDTOs = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<PassengerResponseDTO>>  getPassengersByBooking(@PathVariable("bookingId") Long bookingId){
        List<PassengerResponseDTO> responseDTOS=passengerService.getPassengerByBooking(bookingId);
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{bookingId}/payment")
    public ResponseEntity<PaymentResponseDTO> getPaymentByBooking(@PathVariable Long bookingId) {
        PaymentResponseDTO responseDTO=paymentService.getPaymentByBooking(bookingId);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{bookingId}/status")
    public ResponseEntity<BookingResponseDTO> updateBookingStatus(@PathVariable("bookingId") Long bookingId, @RequestParam BookingStatus newStatus){
        BookingResponseDTO responseDTO=bookingService.updateBookingStatus(bookingId,newStatus);
        return  ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("bookingId") Long bookingId){
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{bookingId}/passenger/{passengerId}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelPassengerFromBooking(@PathVariable("bookingId") Long bookingId, @PathVariable("passengerId") Long passengerId){
        BookingResponseDTO responseDTO=bookingService.cancelPassengerFromBooking(bookingId,passengerId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/passenger/{passengerId}/history")
    public ResponseEntity<List<BookingResponseDTO>> getBookingHistoryOfPassenger(@PathVariable("passengerId") Long passengerId){
        List<BookingResponseDTO> responseDTOS=bookingService.getBookingHistoryOfPassenger(passengerId);
        return ResponseEntity.ok(responseDTOS);
    }
}
