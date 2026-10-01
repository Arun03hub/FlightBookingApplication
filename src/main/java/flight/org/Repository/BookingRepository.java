package flight.org.Repository;

import flight.org.Entity.Booking;
import flight.org.Entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByFlightFlightId(Long id);

    List<Booking> findByBookingDateTimeGreaterThanEqualAndBookingDateTimeLessThan(LocalDateTime start, LocalDateTime end);

    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByPassengersPassengerId(Long passengerId);

    boolean existsByFlightFlightId(long id);
}
