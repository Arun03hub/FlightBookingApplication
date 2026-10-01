package flight.org.Mapper;

import flight.org.DTO.Request.PassengerRequestDTO;
import flight.org.DTO.Response.PassengerResponseDTO;
import flight.org.Entity.Passenger;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface PassengerMapper {
    Passenger toEntity(PassengerRequestDTO dto);
    PassengerResponseDTO toDTO(Passenger passenger);
    List<PassengerResponseDTO> toDTOList(List<Passenger> passengers);
}
