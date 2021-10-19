package vn.alpaca.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.common.object.dto.response.RoleResponse;
import vn.alpaca.userservice.entity.Role;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper {
    RoleResponse convertToResponseDTO(Role role);
}
