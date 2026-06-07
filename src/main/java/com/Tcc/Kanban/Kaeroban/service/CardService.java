package com.Tcc.Kanban.Kaeroban.service;

import com.Tcc.Kanban.Kaeroban.dto.CardRequestDTO;
import com.Tcc.Kanban.Kaeroban.dto.CardResponseDTO;
import com.Tcc.Kanban.Kaeroban.dto.LabelResponseDTO;
import com.Tcc.Kanban.Kaeroban.model.BoardColumn;
import com.Tcc.Kanban.Kaeroban.model.Card;
import com.Tcc.Kanban.Kaeroban.model.Label;
import com.Tcc.Kanban.Kaeroban.model.Priority;
import com.Tcc.Kanban.Kaeroban.repository.BoardColumnRepository;
import com.Tcc.Kanban.Kaeroban.repository.CardRepository;
import com.Tcc.Kanban.Kaeroban.repository.LabelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final BoardColumnRepository boardColumnRepository;
    private final LabelRepository labelRepository;

    public CardResponseDTO save(CardRequestDTO requestDTO) {
        BoardColumn column = boardColumnRepository.findById(requestDTO.columnId())
                .orElseThrow(() -> new EntityNotFoundException("Column not found"));
        Card card = new Card();
        card.setTitle(requestDTO.title());
        card.setDescription(requestDTO.description());
        card.setPosition(requestDTO.position());
        card.setPriority(requestDTO.priority() != null ? Priority.valueOf(requestDTO.priority()) : null);
        card.setDueDate(requestDTO.dueDate());
        card.setColumn(column);
        return toDTO(cardRepository.save(card));
    }

    public List<CardResponseDTO> findByColumn(Integer columnId) {
        return cardRepository.findByColumn_IdColumnOrderByPosition(columnId)
                .stream().map(this::toDTO).toList();
    }

    public CardResponseDTO findById(Integer id) {
        return toDTO(cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card not found")));
    }

    public CardResponseDTO update(Integer id, CardRequestDTO requestDTO) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        card.setTitle(requestDTO.title());
        card.setDescription(requestDTO.description());
        card.setPosition(requestDTO.position());
        card.setPriority(requestDTO.priority() != null ? Priority.valueOf(requestDTO.priority()) : null);
        card.setDueDate(requestDTO.dueDate());
        if (requestDTO.columnId() != null) {
            BoardColumn column = boardColumnRepository.findById(requestDTO.columnId())
                    .orElseThrow(() -> new EntityNotFoundException("Column not found"));
            card.setColumn(column);
        }
        return toDTO(cardRepository.save(card));
    }

    public void delete(Integer id) {
        cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        cardRepository.deleteById(id);
    }


    public CardResponseDTO addLabel(Integer idCard, Integer idLabel){
        Card card = cardRepository.findById(idCard)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        Label label = labelRepository.findById(idLabel)
                .orElseThrow(() -> new EntityNotFoundException("Label not found"));
        card.getLabels().add(label);
        return toDTO(cardRepository.save(card));
    }

    public CardResponseDTO removeLabel(Integer idCard, Integer idLabel){
        Card card = cardRepository.findById(idCard)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        card.getLabels().removeIf(label -> label.getIdLabel().equals(idLabel));
        return toDTO(cardRepository.save(card));
    }

    private LabelResponseDTO labelToDTO(Label label) {
        return new LabelResponseDTO(
                label.getBoard().getIdBoard(),
                label.getIdLabel(),
                label.getName(),
                label.getColor());

    }

    private CardResponseDTO toDTO(Card card) {
        return new CardResponseDTO(
                card.getIdCard(),
                card.getTitle(),
                card.getDescription(),
                card.getPosition(),
                card.getPriority() != null ? card.getPriority().name() : null,
                card.getDueDate(),
                card.getCreatedAt(),
                card.getColumn().getIdColumn(),
                card.getLabels().stream().map(this::labelToDTO).toList()

        );
    }
}