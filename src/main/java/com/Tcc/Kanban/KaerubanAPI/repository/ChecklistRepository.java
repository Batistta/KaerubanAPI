package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {
    List<Checklist> findByCard_IdCardOrderByPosition(Integer cardId);
}
