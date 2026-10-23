package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardMemberRepository extends JpaRepository<BoardMember, UUID> {
    List<BoardMember> findTop4ByUser_IdOrderByLastAccessDesc(UUID userId);
    BoardMember findByUser_Id(UUID userId);

    Optional<BoardMember> findByBoard_IdAndUser_Id(UUID boardId, UUID userId);
}
