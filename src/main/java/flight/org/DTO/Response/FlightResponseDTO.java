package flight.org.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponseDTO{
    private Long flightId;
    private String airline;
    private String source;
    private String destination;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private int totalSeats;
    private int availableSeats;
    private double price;
}
