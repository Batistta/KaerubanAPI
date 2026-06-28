package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.CardRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.CardResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
@CrossOrigin
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponseDTO> save(@RequestBody CardRequestDTO requestDTO) {
        return new ResponseEntity<>(cardService.save(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/column/{columnId}")
    public ResponseEntity<List<CardResponseDTO>> getByColumn(@PathVariable Integer columnId) {
        return ResponseEntity.ok(cardService.findByColumn(columnId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(cardService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponseDTO> update(@PathVariable Integer id, @RequestBody CardRequestDTO requestDTO) {
        return ResponseEntity.ok(cardService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idCard}/labels/{idLabel}")
    public ResponseEntity<CardResponseDTO> addMember(@PathVariable Integer idCard, @PathVariable Integer idLabel) {
        return ResponseEntity.ok(cardService.addLabel(idCard, idLabel));
    }

    @DeleteMapping("/{idCard}/labels/{idLabel}")
    public ResponseEntity<CardResponseDTO> removeMember(@PathVariable Integer idCard, @PathVariable Integer idLabel) {
        return ResponseEntity.ok(cardService.removeLabel(idCard, idLabel));
    }


}