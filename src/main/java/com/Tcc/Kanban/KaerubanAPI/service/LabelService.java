package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.label.LabelRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.label.LabelResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.model.Board;
import com.Tcc.Kanban.KaerubanAPI.model.Label;
import com.Tcc.Kanban.KaerubanAPI.repository.BoardRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.LabelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final LabelRepository labelRepository;
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

    public LabelResponseDTO findById(UUID id) {
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

    public LabelResponseDTO findByBoardId(UUID boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        Label label = labelRepository.findByBoard_Id(board.getId())
                .orElseThrow(() -> new EntityNotFoundException("Label not found"));
        return toDTO(label);
    }

    public LabelResponseDTO updateById(UUID id, LabelRequestDTO requestDTO) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Label not found"));
        label.setName(requestDTO.name());
        label.setColor(requestDTO.color());
        return toDTO(labelRepository.save(label));
    }

    public void deleteById(UUID id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Label not found"));
        labelRepository.delete(label);
    }


    public LabelResponseDTO toDTO(Label label) {
        return new LabelResponseDTO(
                label.getBoard().getId(),
                label.getId(),
                label.getName(),
                label.getColor());

    }
}
