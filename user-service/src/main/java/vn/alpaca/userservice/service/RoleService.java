package vn.alpaca.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.common.object.dto.request.RoleRequest;
import vn.alpaca.common.object.exception.ResourceNotFoundException;
import vn.alpaca.userservice.entity.Role;
import vn.alpaca.userservice.repository.AuthorityRepository;
import vn.alpaca.userservice.repository.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Page<Role> findAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Role findRoleById(int id) {
        Optional<Role> role = roleRepository.findById(id);

        return role.orElseThrow(() -> new ResourceNotFoundException(
                "There's no role match with id: " + id
        ));
    }

    public Role createNewRole(RoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());

        return roleRepository.save(role);
    }

    public Role updateRole(int id, RoleRequest request) {
        Role role = findRoleById(id);
        role.setName(request.getName());

        return roleRepository.save(role);
    }

    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}
