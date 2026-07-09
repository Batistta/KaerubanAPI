package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.checklist.item.ChecklistItemRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.checklist.item.ChecklistItemResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.checklist.ChecklistRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.checklist.ChecklistResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.model.Card;
import com.Tcc.Kanban.KaerubanAPI.model.Checklist;
import com.Tcc.Kanban.KaerubanAPI.model.ChecklistItem;
import com.Tcc.Kanban.KaerubanAPI.repository.CardRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.ChecklistItemRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.ChecklistRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChecklistService {
    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;
    private final CardRepository cardRepository;

    public ChecklistResponseDTO save(ChecklistRequestDTO requestDTO) {
        Card card = cardRepository.findById(requestDTO.cardId())
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        Checklist checklist = new Checklist();
        checklist.setTitle(requestDTO.title());
        checklist.setPosition(requestDTO.position());
        checklist.setCard(card);
        return toDTO(checklistRepository.save(checklist));
    }

    public List<ChecklistResponseDTO> findByCard(UUID cardId) {
        return checklistRepository.findByCard_IdOrderByPosition(cardId)
                .stream().map(this::toDTO).toList();
    }

    public ChecklistResponseDTO findById(UUID id) {
        return toDTO(checklistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checklist not found")));
    }

    public ChecklistResponseDTO update(UUID id, ChecklistRequestDTO requestDTO) {
        Checklist checklist = checklistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checklist not found"));
        checklist.setTitle(requestDTO.title());
        checklist.setPosition(requestDTO.position());
        return toDTO(checklistRepository.save(checklist));
    }

    public void delete(UUID id) {
        checklistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checklist not found"));
        checklistRepository.deleteById(id);
    }



    public ChecklistItemResponseDTO saveItem(ChecklistItemRequestDTO requestDTO) {
        Checklist checklist = checklistRepository.findById(requestDTO.checklistId())
                .orElseThrow(() -> new EntityNotFoundException("Checklist not found"));
        ChecklistItem item = new ChecklistItem();
        item.setContent(requestDTO.content());
        item.setIsChecked(requestDTO.isChecked() != null ? requestDTO.isChecked() : false);
        item.setPosition(requestDTO.position());
        item.setChecklist(checklist);
        return itemToDTO(checklistItemRepository.save(item));
    }

    public List<ChecklistItemResponseDTO> findItemsByChecklist(UUID checklistId) {
        return checklistItemRepository.findByChecklist_IdOrderByPosition(checklistId)
                .stream().map(this::itemToDTO).toList();
    }

    public ChecklistItemResponseDTO updateItem(UUID id, ChecklistItemRequestDTO requestDTO) {
        ChecklistItem item = checklistItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        item.setContent(requestDTO.content());
        item.setIsChecked(requestDTO.isChecked());
        item.setPosition(requestDTO.position());
        return itemToDTO(checklistItemRepository.save(item));
    }

    public void deleteItem(UUID id) {
        checklistItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        checklistItemRepository.deleteById(id);
    }


    private ChecklistResponseDTO toDTO(Checklist checklist) {
        List<ChecklistItemResponseDTO> items = checklistItemRepository
                .findByChecklist_IdOrderByPosition(checklist.getId())
                .stream().map(this::itemToDTO).toList();
        return new ChecklistResponseDTO(
                checklist.getId(),
                checklist.getTitle(),
                checklist.getPosition(),
                checklist.getCard().getId(),
                items
        );
    }

    private ChecklistItemResponseDTO itemToDTO(ChecklistItem item) {
        return new ChecklistItemResponseDTO(
                item.getId(),
                item.getContent(),
                item.getIsChecked(),
                item.getPosition(),
                item.getChecklist().getId()
        );
    }
}