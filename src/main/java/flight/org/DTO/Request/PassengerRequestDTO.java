package flight.org.DTO.Request;

import flight.org.Entity.enums.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age cannot be negative")
    private int age;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Contact is required")
    @Pattern(regexp = "^[0-9]{10}$" ,message="contact must contain exact 10 digits")
    private String contact;

    @NotNull(message = "Seat number is required")
    @Min(value = 1, message = "Seat number must be at least 1")
    private Integer seatNumber;

}
