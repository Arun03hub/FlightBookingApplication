package flight.org.Entity;

import flight.org.Entity.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;
    private String name;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int seatNumber;
    private String contact;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;


}
