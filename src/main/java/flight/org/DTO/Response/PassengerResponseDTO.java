package flight.org.DTO.Response;

import flight.org.Entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerResponseDTO{
    private Long passengerId;
    private String name;
    private int age;
    private Gender gender;
    private int seatNumber;
    private String contact;
}
