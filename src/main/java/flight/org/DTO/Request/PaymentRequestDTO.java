package flight.org.DTO.Request;

import flight.org.Entity.enums.ModeOfPayment;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Mode of payment is required")
    private ModeOfPayment modeOfPayment;

}
