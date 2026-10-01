package flight.org.Mapper;

import flight.org.DTO.Request.PaymentRequestDTO;
import flight.org.DTO.Response.PassengerResponseDTO;
import flight.org.DTO.Response.PaymentResponseDTO;
import flight.org.Entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity(PaymentRequestDTO dto);
    @Mapping(source = "booking.bookId", target = "bookingId")
    PaymentResponseDTO toDTO(Payment payment);
    List<PaymentResponseDTO> toDTOList(List<Payment> payments);
}
