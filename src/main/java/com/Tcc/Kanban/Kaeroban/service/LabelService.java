package com.Tcc.Kanban.Kaeroban.service;

import com.Tcc.Kanban.Kaeroban.dto.LabelRequestDTO;
import com.Tcc.Kanban.Kaeroban.dto.LabelResponseDTO;
import com.Tcc.Kanban.Kaeroban.model.Board;
import com.Tcc.Kanban.Kaeroban.model.Label;
import com.Tcc.Kanban.Kaeroban.repository.BoardColumnRepository;
import com.Tcc.Kanban.Kaeroban.repository.BoardRepository;
import com.Tcc.Kanban.Kaeroban.repository.CardRepository;
import com.Tcc.Kanban.Kaeroban.repository.LabelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final LabelRepository labelRepository;
    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;

    public LabelResponseDTO save(LabelRequestDTO requestDTO) {
        Board board = boardRepository.findById(requestDTO.idBoard())
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        Label label = new Label();
        label.setBoard(board);
        label.setName(requestDTO.name());
        label.setColor(requestDTO.color());
        return toDTO(labelRepository.save(label));
    }

    public LabelResponseDTO findById(Integer id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Label not found"));
        return toDTO(label);
    }

    public List<LabelResponseDTO> findByNameContaining(String name) {
        List<Label> labels =  labelRepository.findByNameContaining(name);
        List<LabelResponseDTO> dtos = new ArrayList<>();
        labels.forEach(label -> dtos.add(toDTO(label)));
        return dtos;
    }

    public LabelResponseDTO findByBoardId(Integer boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        Label label = labelRepository.findByBoard_idBoard(board.getIdBoard())
                .orElseThrow(() -> new EntityNotFoundException("Label not found"));
        return toDTO(label);
    }

    public LabelResponseDTO updateById(Integer id, LabelRequestDTO requestDTO) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Label not found"));
        label.setName(requestDTO.name());
        label.setColor(requestDTO.color());
        return toDTO(labelRepository.save(label));
    }

    public void deleteById(Integer id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Label not found"));
        labelRepository.delete(label);
    }


    public LabelResponseDTO toDTO(Label label) {
        return new LabelResponseDTO(
                label.getBoard().getIdBoard(),
                label.getIdLabel(),
                label.getName(),
                label.getColor());

    }
}
