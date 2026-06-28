package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Integer> {
    List<BoardColumn> findByBoard_IdBoardOrderByPosition(Integer boardId);
    int countByBoard_IdBoard(Integer boardId);

    // coluna se move -> direita
    List<BoardColumn> findByBoard_idBoardAndPositionGreaterThanAndPositionLessThanEqual(
            Integer boardId, Integer oldPosition, Integer newPosition);
    // coluna se move <- esquerda
    List<BoardColumn> findByBoard_idBoardAndPositionLessThanAndPositionGreaterThanEqual(
            Integer boardId, Integer oldPosition, Integer newPosition);

}