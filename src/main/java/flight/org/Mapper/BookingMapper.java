package flight.org.Mapper;

import flight.org.DTO.Request.BookingRequestDTO;
import flight.org.DTO.Response.BookingResponseDTO;
import flight.org.Entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking toEntity(BookingRequestDTO dto);
    @Mapping(source = "bookId", target = "bookId")
    @Mapping(source = "flight.flightId", target = "flightId")
    BookingResponseDTO toDTO(Booking booking);

    List<BookingResponseDTO> toDTOList(List<Booking> bookingList);
}
