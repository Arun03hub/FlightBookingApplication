package flight.org.Entity;

import flight.org.Entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private LocalDateTime bookingDateTime;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private double totalAmount;
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @OneToMany(mappedBy = "booking")
    private List<Passenger> passengers;

    @OneToOne(mappedBy = "booking")
    private Payment payment;


}
