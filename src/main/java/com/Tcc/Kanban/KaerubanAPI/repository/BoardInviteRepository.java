package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.BoardInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardInviteRepository extends JpaRepository<BoardInvite, Integer> {
    Optional<BoardInvite> findByCode(String code);
    boolean existsByCode(String code);
}
