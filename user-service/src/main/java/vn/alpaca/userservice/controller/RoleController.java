package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.common.jpa.util.ExtractParam;
import vn.alpaca.common.object.dto.request.RoleRequest;
import vn.alpaca.common.object.dto.response.RoleResponse;
import vn.alpaca.common.object.wrapper.SuccessResponse;
import vn.alpaca.userservice.entity.Role;
import vn.alpaca.userservice.mapper.RoleMapper;
import vn.alpaca.userservice.service.RoleService;

import java.util.Optional;


@RestController
@RequestMapping(value = "/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_READ')")
    @GetMapping
    public SuccessResponse<Page<RoleResponse>> getAllRoles(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy
    ) {
        Pageable pageable = ExtractParam.getPageable(
                pageNumber, pageSize,
                ExtractParam.getSort(sortBy)
        );

        Page<RoleResponse> dtoPage = new PageImpl<>(
                roleService.findAllRoles(pageable)
                        .map(roleMapper::convertToResponseDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_READ')")
    @GetMapping("/{id}")
    public SuccessResponse<RoleResponse> getRole(@PathVariable("id") int id) {
        Role role = roleService.findRoleById(id);
        RoleResponse dto = roleMapper.convertToResponseDTO(role);
        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_CREATE')")
    @PostMapping
    public SuccessResponse<RoleResponse>
    createRole(@RequestBody RoleRequest form) {
        Role role = roleService.createNewRole(form);
        RoleResponse dto = roleMapper.convertToResponseDTO(role);
        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_UPDATE')")
    @PutMapping(value = "/{id}")
    public SuccessResponse<RoleResponse>
    updateRole(@PathVariable("id") int id, @RequestBody RoleRequest form) {
        Role role = roleService.updateRole(id, form);
        RoleResponse dto = roleMapper.convertToResponseDTO(role);
        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_DELETE')")
    @DeleteMapping("/{id}")
    public SuccessResponse<Void> deleteRole(@PathVariable("id") int id) {
        roleService.deleteRole(id);
        return new SuccessResponse<>(null);
    }
}