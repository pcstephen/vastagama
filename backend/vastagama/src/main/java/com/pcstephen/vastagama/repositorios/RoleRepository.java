package com.pcstephen.vastagama.repositorios;

import com.pcstephen.vastagama.entidades.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}
