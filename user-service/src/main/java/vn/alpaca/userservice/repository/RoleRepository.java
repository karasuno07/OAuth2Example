package vn.alpaca.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.userservice.entity.Role;

public interface RoleRepository extends
        JpaRepository<Role, Integer> {
}
