package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.card.CardSaveDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.card.CardResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.card.CardUpdateDTO;
import com.Tcc.Kanban.KaerubanAPI.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
@CrossOrigin
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponseDTO> save(@Valid @RequestBody CardSaveDTO requestDTO) {
        return new ResponseEntity<>(cardService.save(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/column/{columnId}")
    public ResponseEntity<List<CardResponseDTO>> getByColumn(@PathVariable UUID columnId) {
        return ResponseEntity.ok(cardService.findByColumn(columnId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(cardService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponseDTO> update(@PathVariable UUID id, @RequestBody CardUpdateDTO requestDTO) {
        return ResponseEntity.ok(cardService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idCard}/labels/{idLabel}")
    public ResponseEntity<CardResponseDTO> addLabel(@PathVariable UUID idCard, @PathVariable UUID idLabel) {
        return ResponseEntity.ok(cardService.addLabel(idCard, idLabel));
    }

    @DeleteMapping("/{idCard}/labels/{idLabel}")
    public ResponseEntity<CardResponseDTO> removeLabel(@PathVariable UUID idCard, @PathVariable UUID idLabel) {
        return ResponseEntity.ok(cardService.removeLabel(idCard, idLabel));
    }


}