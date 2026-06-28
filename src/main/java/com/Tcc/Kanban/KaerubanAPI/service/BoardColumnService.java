package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.BoardColumnRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.BoardColumnResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.model.Board;
import com.Tcc.Kanban.KaerubanAPI.model.BoardColumn;
import com.Tcc.Kanban.KaerubanAPI.repository.BoardColumnRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardColumnService {
    private final BoardColumnRepository boardColumnRepository;
    private final BoardRepository boardRepository;

    public BoardColumnResponseDTO save(BoardColumnRequestDTO requestDTO) {
        Board board = boardRepository.findById(requestDTO.boardId())
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));

        int nextPosition = boardColumnRepository.countByBoard_IdBoard(requestDTO.boardId());

        BoardColumn column = new BoardColumn();
        column.setTitle(requestDTO.title());
        column.setColor(requestDTO.color() != null ? requestDTO.color() : "#ffffff");
        column.setPosition(nextPosition);
        column.setWipLimit(requestDTO.wipLimit());
        column.setBoard(board);
        return toDTO(boardColumnRepository.save(column));
    }

    public List<BoardColumnResponseDTO> findByBoard(Integer boardId) {
        return boardColumnRepository.findByBoard_IdBoardOrderByPosition(boardId)
                .stream().map(this::toDTO).toList();
    }

    public BoardColumnResponseDTO findById(Integer id) {
        return toDTO(boardColumnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Column not found")));
    }

    @Transactional
    public BoardColumnResponseDTO update(Integer id, BoardColumnRequestDTO requestDTO) {
        BoardColumn column = boardColumnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Column not found"));
        column.setTitle(requestDTO.title());
        column.setColor(requestDTO.color());
        column.setWipLimit(requestDTO.wipLimit());

        if (!column.getPosition().equals(requestDTO.position())) {
            if (requestDTO.position() > column.getPosition()) {
                List<BoardColumn> decrementColumns = boardColumnRepository.findByBoard_idBoardAndPositionGreaterThanAndPositionLessThanEqual(
                        column.getBoard().getIdBoard(), column.getPosition(), requestDTO.position());
                decrementColumns.forEach(c -> c.setPosition(c.getPosition() - 1));
            }
            else {
                List<BoardColumn> incrementColumns = boardColumnRepository.findByBoard_idBoardAndPositionLessThanAndPositionGreaterThanEqual(
                        column.getBoard().getIdBoard(), column.getPosition(), requestDTO.position());
                incrementColumns.forEach(c -> c.setPosition(c.getPosition() + 1));
            }
            column.setPosition(requestDTO.position());
            return toDTO(column);
        }
        return toDTO(column);
    }

    public void delete(Integer id) {
        boardColumnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Column not found"));
        boardColumnRepository.deleteById(id);
    }

    private BoardColumnResponseDTO toDTO(BoardColumn column) {
        return new BoardColumnResponseDTO(
                column.getIdColumn(),
                column.getTitle(),
                column.getColor(),
                column.getPosition(),
                column.getWipLimit(),
                column.getBoard().getIdBoard()
        );
    }
}