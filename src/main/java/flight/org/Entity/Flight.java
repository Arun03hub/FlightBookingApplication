package flight.org.Entity;

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
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;
    private String airline;
    private String source;
    private String destination;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private int totalSeats;
    private int availableSeats;
    private double price;
    @OneToMany(mappedBy = "flight")
    private List<Booking> bookingList;
}
