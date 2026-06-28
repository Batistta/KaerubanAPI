package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findByOwner_IdUser(Integer ownerId);
    List<Board> findByMembers_IdUser(Integer userId);

    List<Board> findByOwner_IdUserAndTitleContainingIgnoreCase(Integer userId, String title);
    List<Board> findByMembers_IdUserAndTitleContainingIgnoreCase(Integer userId, String title);
}