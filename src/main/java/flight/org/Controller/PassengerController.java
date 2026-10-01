package flight.org.Controller;

import flight.org.DTO.Request.PassengerRequestDTO;
import flight.org.DTO.Response.PassengerResponseDTO;
import flight.org.Entity.enums.Gender;
import flight.org.Service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Passenger")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    @PostMapping()
    public ResponseEntity<PassengerResponseDTO> savePassenger( @Valid @RequestBody PassengerRequestDTO dto) {
        PassengerResponseDTO responseDTO = passengerService.savePassenger(dto);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping()
    public ResponseEntity<List<PassengerResponseDTO>> getAllPassengers(){
        List<PassengerResponseDTO> responseDTOS=passengerService.getAllPassengers();
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PassengerResponseDTO> getPassengerById(@PathVariable("id") long id){
        PassengerResponseDTO responseDTO=passengerService.getPassengerById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/contact/{contact}")
    public ResponseEntity<PassengerResponseDTO> getPassengerById(@PathVariable("contact") String contact){
        PassengerResponseDTO responseDTO=passengerService.getPassengerByContact(contact);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<PassengerResponseDTO>> getPassengerByGender(@PathVariable("gender") Gender gender){
        List<PassengerResponseDTO> responseDTO=passengerService.getPassengerByGender(gender);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PassengerResponseDTO> updatePassenger(@PathVariable long id,@Valid @RequestBody PassengerRequestDTO dto){
        PassengerResponseDTO responseDTO=passengerService.updatePassenger(id,dto);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PassengerResponseDTO>> getPassengerByFlight(@PathVariable("id") long id){
        List<PassengerResponseDTO> responseDTO=passengerService.getPassengerByFlight(id);
        return ResponseEntity.ok(responseDTO);
    }
}
