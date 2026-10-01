package flight.org.Repository;

import flight.org.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {
    List<Flight> findBySourceAndDestination(String source, String destination);

    List<Flight> getFlightsByAirline(String airline);

    List<Flight> findByPriceBetween(double minPrice, double maxPrice);

    List<Flight> findByAvailableSeatsGreaterThan(int seats);

    Flight findFirstBySourceAndDestinationOrderByPriceAsc(String source, String destination);
}
