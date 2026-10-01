package flight.org.DTO.Response;

import flight.org.Entity.enums.ModeOfPayment;
import flight.org.Entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO{
    private Long paymentId;
    private Long bookingId;
    private LocalDateTime paymentDateTime;
    private double price;
    private double refundAmount;
    private ModeOfPayment modeOfPayment;
    private PaymentStatus paymentStatus;
}
