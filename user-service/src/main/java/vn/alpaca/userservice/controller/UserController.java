package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.common.jpa.util.ExtractParam;
import vn.alpaca.common.jpa.util.NullAware;
import vn.alpaca.common.object.dto.request.UserFilter;
import vn.alpaca.common.object.dto.request.UserRequest;
import vn.alpaca.common.object.dto.response.AuthenticationResponse;
import vn.alpaca.common.object.dto.response.UserResponse;
import vn.alpaca.common.object.wrapper.SuccessResponse;
import vn.alpaca.userservice.entity.Role;
import vn.alpaca.userservice.entity.User;
import vn.alpaca.userservice.mapper.UserMapper;
import vn.alpaca.userservice.service.RoleService;
import vn.alpaca.userservice.service.UserService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping
    public SuccessResponse<Page<UserResponse>> getAllUsers(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "limit", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody Optional<UserFilter> filter
    ) {
        Pageable pageable = ExtractParam.getPageable(
                pageNumber,
                pageSize,
                ExtractParam.getSort(sortBy)
        );
        Page<UserResponse> dtoPage = userService.findAllUsers(
                filter.orElse(new UserFilter()),
                pageable
        ).map(userMapper::convertToUserResponseDTO);

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/{userId}")
    public SuccessResponse<UserResponse> getUserById(
            @PathVariable("userId") int id
    ) {
        UserResponse dto = userMapper
                .convertToUserResponseDTO(userService.findUserById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/search/username")
    public SuccessResponse<AuthenticationResponse> getUserByUsername(
            @RequestParam("val") String username
    ) {
        AuthenticationResponse dto = userMapper
                .convertToAuthResponseDTO(
                        userService.findUserByUsername(username));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping
    public SuccessResponse<UserResponse> createNewUser(
            @Valid @RequestBody UserRequest formData
    ) {
        User user = userMapper.convertToEntity(formData);

        if (formData.getRoleId() != null) {
            Role userRole = roleService.findRoleById(formData.getRoleId());
            user.setRole(userRole);
        }

        User savedUser = userService.saveUser(user);
        UserResponse dto = userMapper.convertToUserResponseDTO(savedUser);

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PutMapping(value = "/{userId}")
    public SuccessResponse<UserResponse> updateUser(
            @PathVariable("userId") int id,
            @RequestBody @Valid UserRequest formData
    ) throws InvocationTargetException, IllegalAccessException {
        User target = userService.findUserById(id);
        NullAware.getInstance()
                .copyProperties(target, formData);

        if (formData.getRoleId() != null) {
            Role userRole = roleService.findRoleById(formData.getRoleId());
            target.setRole(userRole);
        }

        User savedUser = userService.saveUser(target);
        UserResponse dto = userMapper.convertToUserResponseDTO(savedUser);

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @PatchMapping(value = "/{userId}/activate")
    public SuccessResponse<Boolean> activateUser(
            @PathVariable("userId") int id
    ) {
        userService.activateUser(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @PatchMapping(value = "/{userId}/deactivate")
    public SuccessResponse<Boolean> deactivateUser(
            @PathVariable("userId") int id
    ) {
        userService.deactivateUser(id);

        return new SuccessResponse<>(true);
    }
}
