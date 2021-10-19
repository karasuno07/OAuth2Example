package vn.alpaca.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.common.object.dto.response.AuthorityResponse;
import vn.alpaca.userservice.entity.Authority;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface AuthorityMapper {
    AuthorityResponse convertToResponseDTO(Authority authority);
}
