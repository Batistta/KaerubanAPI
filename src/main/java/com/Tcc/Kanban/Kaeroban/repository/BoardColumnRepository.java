package com.Tcc.Kanban.Kaeroban.repository;

import com.Tcc.Kanban.Kaeroban.model.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Integer> {
    List<BoardColumn> findByBoard_IdBoardOrderByPosition(Integer boardId);
    int countByBoard_IdBoard(Integer boardId);
}