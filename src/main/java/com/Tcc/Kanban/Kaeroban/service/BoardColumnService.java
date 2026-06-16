package com.Tcc.Kanban.Kaeroban.service;

import com.Tcc.Kanban.Kaeroban.dto.BoardColumnRequestDTO;
import com.Tcc.Kanban.Kaeroban.dto.BoardColumnResponseDTO;
import com.Tcc.Kanban.Kaeroban.model.Board;
import com.Tcc.Kanban.Kaeroban.model.BoardColumn;
import com.Tcc.Kanban.Kaeroban.repository.BoardColumnRepository;
import com.Tcc.Kanban.Kaeroban.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public BoardColumnResponseDTO update(Integer id, BoardColumnRequestDTO requestDTO) {
        BoardColumn column = boardColumnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Column not found"));
        column.setTitle(requestDTO.title());
        column.setColor(requestDTO.color());
        column.setPosition(requestDTO.position());
        column.setWipLimit(requestDTO.wipLimit());
        return toDTO(boardColumnRepository.save(column));
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