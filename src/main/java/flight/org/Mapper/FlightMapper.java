package flight.org.Mapper;

import flight.org.DTO.Request.FlightRequestDTO;
import flight.org.DTO.Response.FlightResponseDTO;
import flight.org.Entity.Flight;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    Flight toEntity(FlightRequestDTO dto);
    FlightResponseDTO toDTO(Flight flight);

    List<FlightResponseDTO> toDTOList(List<Flight> flights);
}
