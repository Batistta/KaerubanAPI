package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.column.BoardColumnResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.column.BoardColumnSaveDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.column.BoardColumnUpdateDTO;
import com.Tcc.Kanban.KaerubanAPI.model.Board;
import com.Tcc.Kanban.KaerubanAPI.model.BoardColumn;
import com.Tcc.Kanban.KaerubanAPI.repository.BoardColumnRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardColumnService {
    private final BoardColumnRepository boardColumnRepository;
    private final BoardRepository boardRepository;

    public BoardColumnResponseDTO save(BoardColumnSaveDTO requestDTO) {
        Board board = boardRepository.findById(requestDTO.boardId())
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));

        int nextPosition = boardColumnRepository.countByBoard_Id(requestDTO.boardId());

        BoardColumn column = new BoardColumn();
        column.setTitle(requestDTO.title());
        column.setColor(requestDTO.color() != null ? requestDTO.color() : "#ffffff");
        column.setPosition(nextPosition);
        column.setWipLimit(requestDTO.wipLimit());
        column.setBoard(board);
        return toDTO(boardColumnRepository.save(column));
    }

    public List<BoardColumnResponseDTO> findByBoard(UUID boardId) {
        return boardColumnRepository.findByBoard_IdOrderByPosition(boardId)
                .stream().map(this::toDTO).toList();
    }

    public BoardColumnResponseDTO findById(UUID id) {
        return toDTO(boardColumnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Column not found")));
    }

    @Transactional
    public BoardColumnResponseDTO update(UUID id, BoardColumnUpdateDTO requestDTO) {
        BoardColumn column = boardColumnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Column not found"));
        column.setTitle(requestDTO.title());
        column.setColor(requestDTO.color());
        column.setWipLimit(requestDTO.wipLimit());

        if (!column.getPosition().equals(requestDTO.position())) {
            if (requestDTO.position() > column.getPosition()) {
                List<BoardColumn> shifted = boardColumnRepository.findByBoard_IdAndPositionGreaterThanAndPositionLessThanEqual(
                        column.getBoard().getId(), column.getPosition(), requestDTO.position());
                shifted.forEach(c -> c.setPosition(c.getPosition() - 1));
                boardColumnRepository.saveAll(shifted);
            } else {
                List<BoardColumn> shifted = boardColumnRepository.findByBoard_IdAndPositionLessThanAndPositionGreaterThanEqual(
                        column.getBoard().getId(), column.getPosition(), requestDTO.position());
                shifted.forEach(c -> c.setPosition(c.getPosition() + 1));
                boardColumnRepository.saveAll(shifted);
            }
            column.setPosition(requestDTO.position());
        }

        return toDTO(boardColumnRepository.save(column));
    }

    public void delete(UUID id) {
        boardColumnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Column not found"));
        boardColumnRepository.deleteById(id);
    }

    private BoardColumnResponseDTO toDTO(BoardColumn column) {
        return new BoardColumnResponseDTO(
                column.getId(),
                column.getTitle(),
                column.getColor(),
                column.getPosition(),
                column.getWipLimit(),
                column.getBoard().getId()
        );
    }
}