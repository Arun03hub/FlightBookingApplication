package flight.org.Repository;
import java.util.List;
import java.util.Optional;
import flight.org.Entity.Passenger;
import flight.org.Entity.enums.BookingStatus;
import flight.org.Entity.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {
    Optional<Passenger> findByContact(String contact);

    List<Passenger> findByGender(Gender gender);

    List<Passenger> findByBookingFlightFlightId(long id);

    boolean existsByBookingFlightFlightIdAndBookingStatusAndSeatNumber(Long flightId, BookingStatus confirmed, int seatNumber);
}
