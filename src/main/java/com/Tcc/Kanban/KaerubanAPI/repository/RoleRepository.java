package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByTitle(String title);
}
