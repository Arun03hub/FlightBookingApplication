package flight.org.Service;

import flight.org.DTO.Request.FlightRequestDTO;
import flight.org.DTO.Response.FlightResponseDTO;
import flight.org.Entity.Flight;
import flight.org.Exception.FlightDeletionNotAllowedException;
import flight.org.Exception.FlightNotFoundException;
import flight.org.Mapper.FlightMapper;
import flight.org.Repository.BookingRepository;
import flight.org.Repository.FlightRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    @Autowired
    private FlightMapper mapper;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public FlightResponseDTO addFlight(FlightRequestDTO dto) {
        Flight flight=mapper.toEntity(dto);
        Flight saveflight=flightRepository.save(flight);
        FlightResponseDTO flightResponseDTO=mapper.toDTO(saveflight);
        return flightResponseDTO;
    }

    public List<FlightResponseDTO> getAllFlights() {
        List<Flight> flights=flightRepository.findAll();
        List<FlightResponseDTO> responseDTO=mapper.toDTOList(flights);
        return responseDTO;
    }

    public FlightResponseDTO getFlightById(Long id) {
        Flight flight=flightRepository.findById(id).orElseThrow(
                ()-> new FlightNotFoundException("Flight not found by this id: "+id));
        return mapper.toDTO(flight);
    }

    public List<FlightResponseDTO> getFlightBySourceDestination(String source, String destination) {
        List<Flight> flight=flightRepository.findBySourceAndDestination(source,destination);
        if(flight.isEmpty()){
            throw new FlightNotFoundException("No flights found from " + source + " to " + destination);
        }
        return mapper.toDTOList(flight);
    }

    public List<FlightResponseDTO> getFlightByAirline(String airline) {
        List<Flight> flights=flightRepository.getFlightsByAirline(airline);
        if(flights.isEmpty()){
            throw new FlightNotFoundException("No flights found in "+airline);
        }
        return mapper.toDTOList(flights);
    }

    public FlightResponseDTO updateFlight(long id ,FlightRequestDTO dto) {
        Flight flight=flightRepository.findById(id).orElseThrow(()-> new FlightNotFoundException("Flight not found in this id: "+id));
        flight.setAirline(dto.getAirline());
        flight.setSource(dto.getSource());
        flight.setDestination(dto.getDestination());
        flight.setDepartureDateTime(dto.getDepartureDateTime());
        flight.setArrivalDateTime(dto.getArrivalDateTime());
        flight.setPrice(dto.getPrice());
        Flight updateflight=flightRepository.save(flight);
        return  mapper.toDTO(updateflight);
    }

    @Transactional
    public FlightResponseDTO deleteFlight(long id) {
        Flight flight=flightRepository.findById(id).orElseThrow(()-> new FlightNotFoundException("Flight not found in this id: "+id));
        boolean bookingExists=bookingRepository.existsByFlightFlightId(id);
        if(bookingExists){
            throw new FlightDeletionNotAllowedException("Flight cannot be deleted because booking exist for this flight");
        }
        flightRepository.delete(flight);
        return  mapper.toDTO(flight);
    }


    public List<FlightResponseDTO> findWithinPriceRange(double minPrice, double maxPrice) {
        List<Flight> flights=flightRepository.findByPriceBetween(minPrice,maxPrice);
        if(flights.isEmpty()){
            throw  new FlightNotFoundException("Flight not found between price "+minPrice+" and "+maxPrice);
        }
        return mapper.toDTOList(flights);
    }


    public FlightResponseDTO findFlightBetweenTwoCities(String source, String destination) {
        Flight flight=flightRepository.findFirstBySourceAndDestinationOrderByPriceAsc(source,destination);
        if(flight==null){
            throw  new FlightNotFoundException("No flight found between "+source+" and "+destination);
        }
        return mapper.toDTO(flight);
    }


    public List<FlightResponseDTO> findFlightsWithMoreSeats(int seats) {
        List<Flight> flights = flightRepository.findByAvailableSeatsGreaterThan(seats);
        if (flights.isEmpty()) {
            throw new FlightNotFoundException("No flights found with more than " + seats + " available seats");
        }
        return mapper.toDTOList(flights);
    }


    public Page<FlightResponseDTO> getFlights(int page, int size, String sortBy, String direction) {
        Sort sort;
        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Flight> flights = flightRepository.findAll(pageable);
        return flights.map(mapper::toDTO);
    }
}
