package com.Tcc.Kanban.Kaeroban.service;

import com.Tcc.Kanban.Kaeroban.dto.ChecklistItemRequestDTO;
import com.Tcc.Kanban.Kaeroban.dto.ChecklistItemResponseDTO;
import com.Tcc.Kanban.Kaeroban.dto.ChecklistRequestDTO;
import com.Tcc.Kanban.Kaeroban.dto.ChecklistResponseDTO;
import com.Tcc.Kanban.Kaeroban.model.Card;
import com.Tcc.Kanban.Kaeroban.model.Checklist;
import com.Tcc.Kanban.Kaeroban.model.ChecklistItem;
import com.Tcc.Kanban.Kaeroban.repository.CardRepository;
import com.Tcc.Kanban.Kaeroban.repository.ChecklistItemRepository;
import com.Tcc.Kanban.Kaeroban.repository.ChecklistRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ChecklistResponseDTO> findByCard(Integer cardId) {
        return checklistRepository.findByCard_IdCardOrderByPosition(cardId)
                .stream().map(this::toDTO).toList();
    }

    public ChecklistResponseDTO findById(Integer id) {
        return toDTO(checklistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checklist not found")));
    }

    public ChecklistResponseDTO update(Integer id, ChecklistRequestDTO requestDTO) {
        Checklist checklist = checklistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checklist not found"));
        checklist.setTitle(requestDTO.title());
        checklist.setPosition(requestDTO.position());
        return toDTO(checklistRepository.save(checklist));
    }

    public void delete(Integer id) {
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

    public List<ChecklistItemResponseDTO> findItemsByChecklist(Integer checklistId) {
        return checklistItemRepository.findByChecklist_IdChecklistsOrderByPosition(checklistId)
                .stream().map(this::itemToDTO).toList();
    }

    public ChecklistItemResponseDTO updateItem(Integer id, ChecklistItemRequestDTO requestDTO) {
        ChecklistItem item = checklistItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        item.setContent(requestDTO.content());
        item.setIsChecked(requestDTO.isChecked());
        item.setPosition(requestDTO.position());
        return itemToDTO(checklistItemRepository.save(item));
    }

    public void deleteItem(Integer id) {
        checklistItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        checklistItemRepository.deleteById(id);
    }


    private ChecklistResponseDTO toDTO(Checklist checklist) {
        List<ChecklistItemResponseDTO> items = checklistItemRepository
                .findByChecklist_IdChecklistsOrderByPosition(checklist.getIdChecklists())
                .stream().map(this::itemToDTO).toList();
        return new ChecklistResponseDTO(
                checklist.getIdChecklists(),
                checklist.getTitle(),
                checklist.getPosition(),
                checklist.getCard().getIdCard(),
                items
        );
    }

    private ChecklistItemResponseDTO itemToDTO(ChecklistItem item) {
        return new ChecklistItemResponseDTO(
                item.getIdItem(),
                item.getContent(),
                item.getIsChecked(),
                item.getPosition(),
                item.getChecklist().getIdChecklists()
        );
    }
}