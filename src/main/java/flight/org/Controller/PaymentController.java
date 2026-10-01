package flight.org.Controller;

import flight.org.DTO.Request.PaymentRequestDTO;
import flight.org.DTO.Response.PaymentResponseDTO;
import flight.org.Entity.enums.ModeOfPayment;
import flight.org.Entity.enums.PaymentStatus;
import flight.org.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/add")
    public ResponseEntity<PaymentResponseDTO> addPayment(@RequestBody PaymentRequestDTO requestDTO){
        PaymentResponseDTO responseDTO=paymentService.addPayment(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping()
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments(){
        List<PaymentResponseDTO> responseDTO=paymentService.getAllPayments();
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable("id") long id){
        PaymentResponseDTO responseDTO=paymentService.getPaymentById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PaymentResponseDTO> updatePaymentStatus(@PathVariable("id") long id, @RequestParam PaymentStatus status) {
        PaymentResponseDTO responseDTO = paymentService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/status")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByStatus(@RequestParam PaymentStatus paymentStatus){
        List<PaymentResponseDTO> responseDTOS=paymentService.getPaymentsByStatus(paymentStatus);
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/mode-of-payment")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByMode(@RequestParam ModeOfPayment modeOfPayment){
        List<PaymentResponseDTO> responseDTOS=paymentService.getPaymentsByMode(modeOfPayment);
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/flight/{flightId}/total-amount")
    public ResponseEntity<Double> getTotalAmountPaidByFlight(@PathVariable("flightId") long flightId) {
        double totalAmount = paymentService.getTotalAmountPaidByFlight(flightId);
        return ResponseEntity.ok(totalAmount);
    }
}
