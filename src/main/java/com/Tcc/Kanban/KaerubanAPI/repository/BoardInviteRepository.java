package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.BoardInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardInviteRepository extends JpaRepository<BoardInvite, UUID> {
    Optional<BoardInvite> findByCode(String code);
    boolean existsByCode(String code);
    List<BoardInvite> findByBoard_Id(UUID idBoard);
}
