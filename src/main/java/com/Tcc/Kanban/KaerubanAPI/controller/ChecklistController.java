package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.checklist.item.ChecklistItemRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.checklist.item.ChecklistItemResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.checklist.ChecklistRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.checklist.ChecklistResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checklists")
@CrossOrigin
public class ChecklistController {
    private final ChecklistService checklistService;

    @PostMapping
    public ResponseEntity<ChecklistResponseDTO> save(@RequestBody ChecklistRequestDTO requestDTO) {
        return new ResponseEntity<>(checklistService.save(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<ChecklistResponseDTO>> getByCard(@PathVariable UUID cardId) {
        return ResponseEntity.ok(checklistService.findByCard(cardId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChecklistResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(checklistService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChecklistResponseDTO> update(@PathVariable UUID id, @RequestBody ChecklistRequestDTO requestDTO) {
        return ResponseEntity.ok(checklistService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        checklistService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/items")
    public ResponseEntity<ChecklistItemResponseDTO> saveItem(@RequestBody ChecklistItemRequestDTO requestDTO) {
        return new ResponseEntity<>(checklistService.saveItem(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{checklistId}/items")
    public ResponseEntity<List<ChecklistItemResponseDTO>> getItems(@PathVariable UUID checklistId) {
        return ResponseEntity.ok(checklistService.findItemsByChecklist(checklistId));
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<ChecklistItemResponseDTO> updateItem(@PathVariable UUID id, @RequestBody ChecklistItemRequestDTO requestDTO) {
        return ResponseEntity.ok(checklistService.updateItem(id, requestDTO));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID id) {
        checklistService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
