package vn.alpaca.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.common.object.dto.request.UserRequest;
import vn.alpaca.common.object.dto.response.AuthenticationResponse;
import vn.alpaca.common.object.dto.response.UserResponse;
import vn.alpaca.userservice.entity.User;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "role", expression = "java(user.getRole().getName())")
    @Mapping(target = "roleId", expression = "java(user.getRole().getId())")
    UserResponse convertToUserResponseDTO(User user);

    @Mapping(target = "roleId", expression = "java(user.getRole().getId())")
    AuthenticationResponse convertToAuthResponseDTO(User user);

    User convertToEntity(UserRequest userRequest);

}
