package flight.org.DTO.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO{
    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotEmpty(message = "At least one passenger is required")
    private List<Long> passengerIds;

    @NotNull(message = "Payment4 ID is required")
    private Long paymentId;
}
