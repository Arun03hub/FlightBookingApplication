package flight.org.Controller;

import flight.org.DTO.Request.FlightRequestDTO;
import flight.org.DTO.Response.FlightResponseDTO;
import flight.org.Service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @PostMapping("/add")
    public ResponseEntity<FlightResponseDTO> addFlight(@Valid @RequestBody FlightRequestDTO dto){
        FlightResponseDTO responseDTO = flightService.addFlight(dto);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/flight")
    public ResponseEntity<List<FlightResponseDTO>> getAllFlights(){
        List<FlightResponseDTO> responseDTO= flightService.getAllFlights();
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FlightResponseDTO> getFlightById(@PathVariable("id") Long id){
        FlightResponseDTO responseDTO=flightService.getFlightById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/src/dst")
    public ResponseEntity<List<FlightResponseDTO>> getFlightBySourceDestination(
            @RequestParam String source,
            @RequestParam String destination){
        List<FlightResponseDTO> responseDTO=flightService.getFlightBySourceDestination(source, destination);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/airline")
    public ResponseEntity<List<FlightResponseDTO>> getFlightByAirline(@RequestParam String airline ){
        List<FlightResponseDTO> responseDTOS=flightService.getFlightByAirline(airline);
        return ResponseEntity.ok(responseDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> updateFlight(@PathVariable("id") long id, @Valid @RequestBody FlightRequestDTO dto){
        FlightResponseDTO responseDTO=flightService.updateFlight(id,dto);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> deleteFlight(@PathVariable("id") long id){
        FlightResponseDTO responseDTO=flightService.deleteFlight(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<FlightResponseDTO>> findWithinPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice){
        List<FlightResponseDTO> responseDTOS=flightService.findWithinPriceRange(minPrice,maxPrice);
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/cheapest")
    public ResponseEntity<FlightResponseDTO> findCheapestFlightBetweenTwoCities(@RequestParam String source, @RequestParam String destination){
        FlightResponseDTO responseDTO=flightService.findFlightBetweenTwoCities(source,destination);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/seats")
    public ResponseEntity<List<FlightResponseDTO>> findFlightsWithMoreSeats(@RequestParam int seats) {
        List<FlightResponseDTO> responseDTO = flightService.findFlightsWithMoreSeats(seats);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<FlightResponseDTO>> getFlights(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "price") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
        Page<FlightResponseDTO> response = flightService.getFlights(page, size, sortBy, direction);
        return ResponseEntity.ok(response);
    }
}
