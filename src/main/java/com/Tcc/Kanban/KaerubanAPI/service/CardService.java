package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.CardRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.CardResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.LabelResponseDTO;
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

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final BoardColumnRepository boardColumnRepository;
    private final LabelRepository labelRepository;

    public CardResponseDTO save(CardRequestDTO requestDTO) {
        BoardColumn column = boardColumnRepository.findById(requestDTO.columnId())
                .orElseThrow(() -> new EntityNotFoundException("Column not found"));

        int nextPosition = cardRepository.countByColumn_IdColumn(requestDTO.columnId()).intValue();
        Card card = new Card();
        card.setTitle(requestDTO.title());
        card.setDescription(requestDTO.description());
        card.setPosition(nextPosition);
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

    @Transactional //tambem garante o save automatico
    public CardResponseDTO update(Integer id, CardRequestDTO requestDTO) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        card.setTitle(requestDTO.title());
        card.setDescription(requestDTO.description());
        card.setPriority(requestDTO.priority() != null ? Priority.valueOf(requestDTO.priority()) : null);
        card.setDueDate(requestDTO.dueDate());
        Integer oldPosition = card.getPosition();
        Integer oldColumnId = card.getColumn().getIdColumn();
        Integer newColumnId = requestDTO.columnId() != null ? requestDTO.columnId() : oldColumnId;
        Integer newPosition = requestDTO.position() != null
                ? requestDTO.position()
                :  newColumnId.equals(card.getColumn().getIdColumn()) ? oldPosition : cardRepository.countByColumn_IdColumn(newColumnId).intValue();
        if (!newColumnId.equals(oldColumnId)) { //mudou coluna
            BoardColumn newColumn = boardColumnRepository.findById(newColumnId)
                    .orElseThrow(() -> new EntityNotFoundException("Column not found"));
            //Coluna de origem -> atualiza as position para preencher vão
            List<Card> originColumn = cardRepository.findByColumn_IdColumnAndPositionGreaterThan(oldColumnId, oldPosition);
            originColumn.forEach(c -> c.setPosition(c.getPosition() - 1));
            //Coluna de destino -> atualiza as positions para abrir espaço
            List<Card>  destinationColumn = cardRepository.findByColumn_IdColumnAndPositionGreaterThanEqual(newColumnId, newPosition);
            destinationColumn.forEach(c -> c.setPosition(c.getPosition() + 1));
            //Card
            card.setColumn(newColumn);
            card.setPosition(newPosition);

        } else if (!newPosition.equals(oldPosition)) { //mudou apenas a posição
            if(newPosition > oldPosition) {
                // cards que vão se mover para antes do card que mudou
                List<Card> decrementCards = cardRepository.findByColumn_IdColumnAndPositionGreaterThanAndPositionLessThanEqual(
                        oldColumnId, oldPosition,newPosition);
                decrementCards.forEach(c -> c.setPosition(c.getPosition() - 1));
            } else  {
                // cards que vão se mover para depois do card que mudou
                List<Card> incrementCards = cardRepository.findByColumn_IdColumnAndPositionLessThanAndPositionGreaterThanEqual(
                        oldColumnId, oldPosition,newPosition);
                incrementCards.forEach(c -> c.setPosition(c.getPosition() + 1));
            }
            card.setPosition(newPosition);
        }
        return toDTO(card);
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