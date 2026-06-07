package com.Tcc.Kanban.Kaeroban.repository;

import com.Tcc.Kanban.Kaeroban.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Integer> {
    Optional<Label> findByBoard_idBoard(Integer boardId);
    List<Label> findByNameContaining(String name);
}
