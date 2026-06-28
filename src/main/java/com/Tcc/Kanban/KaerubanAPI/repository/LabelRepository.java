package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Integer> {
    Optional<Label> findByBoard_idBoard(Integer boardId);
    List<Label> findByNameContaining(String name);
}
