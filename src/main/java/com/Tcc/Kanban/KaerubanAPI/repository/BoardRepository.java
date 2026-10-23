package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {
    List<Board> findByMembers_User_Id(UUID userId);
    // BoardMemberRepository

    List<Board> findByMembers_User_IdAndTitleContainingIgnoreCase(UUID userId, String title);
}