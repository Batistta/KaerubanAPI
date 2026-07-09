package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.card.CardSaveDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.card.CardResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.card.CardUpdateDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.label.LabelResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.model.BoardColumn;
import com.Tcc.Kanban.KaerubanAPI.model.Card;
import com.Tcc.Kanban.KaerubanAPI.model.Label;
import com.Tcc.Kanban.KaerubanAPI.model.Priority;
import com.Tcc.Kanban.KaerubanAPI.repository.BoardColumnRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.CardRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.LabelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final BoardColumnRepository boardColumnRepository;
    private final LabelRepository labelRepository;

    public CardResponseDTO save(CardSaveDTO requestDTO) {
        BoardColumn column = boardColumnRepository.findById(requestDTO.columnId())
                .orElseThrow(() -> new EntityNotFoundException("Column not found"));
        Card card = new Card();
        card.setTitle(requestDTO.title());
        card.setDescription(requestDTO.description());
        if (requestDTO.position() != null) {
            card.setPosition(requestDTO.position());
        } else {
            int nextPosition = cardRepository.countByColumn_Id(requestDTO.columnId()).intValue();
            card.setPosition(nextPosition);
        }
        card.setPriority(requestDTO.priority() != null ? Priority.valueOf(requestDTO.priority()) : null);
        card.setDueDate(requestDTO.dueDate());
        card.setColumn(column);
        return toDTO(cardRepository.save(card));
    }

    public List<CardResponseDTO> findByColumn(UUID columnId) {
        return cardRepository.findByColumn_IdOrderByPosition(columnId)
                .stream().map(this::toDTO).toList();
    }

    public CardResponseDTO findById(UUID id) {
        return toDTO(cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card not found")));
    }

    @Transactional
    public CardResponseDTO update(UUID id, CardUpdateDTO requestDTO) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        card.setTitle(requestDTO.title() != null ? requestDTO.title() : card.getTitle());
        card.setDescription(requestDTO.description() != null ? requestDTO.description() : card.getDescription());
        card.setPriority(requestDTO.priority() != null ? Priority.valueOf(requestDTO.priority()) : card.getPriority());
        card.setDueDate(requestDTO.dueDate() != null ? requestDTO.dueDate() : card.getDueDate());
        Integer oldPosition = card.getPosition();
        UUID oldColumnId = card.getColumn().getId();
        UUID newColumnId = requestDTO.columnId() != null ? requestDTO.columnId() : oldColumnId;
        Integer newPosition = requestDTO.position() != null
                ? requestDTO.position()
                : newColumnId.equals(oldColumnId) ? oldPosition : cardRepository.countByColumn_Id(newColumnId).intValue();
        List<Card> affectedCards = List.of();
        if (!newColumnId.equals(oldColumnId)) { // mudou coluna
            BoardColumn newColumn = boardColumnRepository.findById(newColumnId)
                    .orElseThrow(() -> new EntityNotFoundException("Column not found"));
            List<Card> originColumn = cardRepository.findByColumn_IdAndPositionGreaterThan(oldColumnId, oldPosition);
            originColumn.forEach(c -> c.setPosition(c.getPosition() - 1));
            List<Card> destinationColumn = cardRepository.findByColumn_IdAndPositionGreaterThanEqual(newColumnId, newPosition);
            destinationColumn.forEach(c -> c.setPosition(c.getPosition() + 1));
            card.setColumn(newColumn);
            card.setPosition(newPosition);
            affectedCards = Stream.concat(originColumn.stream(), destinationColumn.stream()).toList();
        } else if (!newPosition.equals(oldPosition)) { // mudou apenas a posição
            if (newPosition > oldPosition) {
                affectedCards = cardRepository.findByColumn_IdAndPositionGreaterThanAndPositionLessThanEqual(
                        oldColumnId, oldPosition, newPosition);
                affectedCards.forEach(c -> c.setPosition(c.getPosition() - 1));
            } else {
                affectedCards = cardRepository.findByColumn_IdAndPositionLessThanAndPositionGreaterThanEqual(
                        oldColumnId, oldPosition, newPosition);
                affectedCards.forEach(c -> c.setPosition(c.getPosition() + 1));
            }
            card.setPosition(newPosition);
        }
        cardRepository.saveAll(affectedCards);
        return toDTO(cardRepository.save(card));
    }

    public void delete(UUID id) {
        cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        cardRepository.deleteById(id);
    }

    @Transactional
    public CardResponseDTO addLabel(UUID idCard, UUID idLabel){
        Card card = cardRepository.findById(idCard)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        Label label = labelRepository.findById(idLabel)
                .orElseThrow(() -> new EntityNotFoundException("Label not found"));
        if (!card.getLabels().contains(label)) {
            card.getLabels().add(label);
        }
        return toDTO(cardRepository.save(card));
    }

    @Transactional
    public CardResponseDTO removeLabel(UUID idCard, UUID idLabel){
        Card card = cardRepository.findById(idCard)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        card.getLabels().removeIf(label -> label.getId().equals(idLabel));
        return toDTO(cardRepository.save(card));
    }

    private LabelResponseDTO labelToDTO(Label label) {
        return new LabelResponseDTO(
                label.getBoard().getId(),
                label.getId(),
                label.getName(),
                label.getColor());

    }

    private CardResponseDTO toDTO(Card card) {
        return new CardResponseDTO(
                card.getId(),
                card.getTitle(),
                card.getDescription(),
                card.getPosition(),
                card.getPriority() != null ? card.getPriority().name() : null,
                card.getDueDate(),
                card.getCreatedAt(),
                card.getColumn().getId(),
                card.getLabels().stream().map(this::labelToDTO).toList()

        );
    }
}