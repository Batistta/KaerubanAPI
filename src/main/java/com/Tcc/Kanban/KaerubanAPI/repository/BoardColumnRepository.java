package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, UUID> {
    int countByBoard_Id(UUID boardId);
    List<BoardColumn> findByBoard_IdOrderByPosition(UUID boardId);
    List<BoardColumn> findByBoard_IdAndPositionGreaterThanAndPositionLessThanEqual(UUID boardId, Integer from, Integer to);
    List<BoardColumn> findByBoard_IdAndPositionLessThanAndPositionGreaterThanEqual(UUID boardId, Integer from, Integer to);

}