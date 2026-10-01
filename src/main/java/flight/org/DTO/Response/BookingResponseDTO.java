package flight.org.DTO.Response;

import flight.org.Entity.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO{
    private Long bookId;
    private Long flightId;
    private LocalDateTime bookingDateTime;
    private BookingStatus status;
    private double totalAmount;
    private List<PassengerResponseDTO> passengers;
}
