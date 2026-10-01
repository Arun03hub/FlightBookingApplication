package flight.org.Entity;

import flight.org.Entity.enums.ModeOfPayment;
import flight.org.Entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private LocalDateTime paymentDateTime;
    private double price;
    private double refundedAmount;
    @Enumerated(EnumType.STRING)
    private ModeOfPayment modeOfPayment;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @OneToOne // one payment for one booking
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
