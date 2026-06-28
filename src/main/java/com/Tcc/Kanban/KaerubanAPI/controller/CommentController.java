package com.Tcc.Kanban.KaerubanAPI.controller;

import com.Tcc.Kanban.KaerubanAPI.dto.CommentRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.CommentResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDTO> save(@RequestBody CommentRequestDTO requestDTO) {
        return new ResponseEntity<>(commentService.save(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<CommentResponseDTO>> getByCard(@PathVariable Integer cardId) {
        return ResponseEntity.ok(commentService.findByCard(cardId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> update(@PathVariable Integer id, @RequestBody CommentRequestDTO requestDTO) {
        return ResponseEntity.ok(commentService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
