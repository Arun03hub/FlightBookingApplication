package flight.org.DTO.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightRequestDTO {
    @NotBlank(message = "Airline is required")
    private String airline;

    @NotBlank(message = "Source is required")
    private String source;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Departure date and time is required")
    @Future(message = "Departure date must be in the future")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date and time is required")
    @Future(message = "Arrival date must be in the future")
    private LocalDateTime arrivalDateTime;

    @Min(value = 1, message = "Available seats must be at least 1")
    private int availableSeats;

    @Min(value = 1, message = "Total seats must be at least 1")
    private int totalSeats;

    @Positive(message = "Price must be greater than 0")
    private double price;
}
