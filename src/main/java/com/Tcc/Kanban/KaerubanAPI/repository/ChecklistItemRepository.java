package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Integer> {
    List<ChecklistItem> findByChecklist_IdChecklistsOrderByPosition(Integer checklistId);
}
