package flight.org.Repository;

import flight.org.Entity.Payment;
import flight.org.Entity.enums.ModeOfPayment;
import flight.org.Entity.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    List<Payment> findByModeOfPayment(ModeOfPayment modeOfPayment);

    @Query("""
    SELECT SUM(p.price)
    FROM Payment p
    WHERE p.booking.flight.flightId = :flightId
    AND p.paymentStatus = :status
    """)
    double getTotalAmountPaidByFlight(@Param("flightId") long flightId, @Param("status") PaymentStatus status);

    Optional<Payment> findByBookingBookId(Long bookingId);
}
