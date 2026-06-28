package com.Tcc.Kanban.KaerubanAPI.service;

import com.Tcc.Kanban.KaerubanAPI.dto.CommentRequestDTO;
import com.Tcc.Kanban.KaerubanAPI.dto.CommentResponseDTO;
import com.Tcc.Kanban.KaerubanAPI.model.Card;
import com.Tcc.Kanban.KaerubanAPI.model.Comment;
import com.Tcc.Kanban.KaerubanAPI.model.User;
import com.Tcc.Kanban.KaerubanAPI.repository.CardRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.CommentRepository;
import com.Tcc.Kanban.KaerubanAPI.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public CommentResponseDTO save(CommentRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Card card = cardRepository.findById(requestDTO.cardId())
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        Comment comment = new Comment();
        comment.setContent(requestDTO.content());
        comment.setUser(user);
        comment.setCard(card);
        return toDTO(commentRepository.save(comment));
    }

    public List<CommentResponseDTO> findByCard(Integer cardId) {
        return commentRepository.findByCard_IdCardOrderByCreatedAtDesc(cardId)
                .stream().map(this::toDTO).toList();
    }

    public CommentResponseDTO findById(Integer id) {
        return toDTO(commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found")));
    }

    public CommentResponseDTO update(Integer id, CommentRequestDTO requestDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        comment.setContent(requestDTO.content());
        return toDTO(commentRepository.save(comment));
    }

    public void delete(Integer id) {
        commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        commentRepository.deleteById(id);
    }

    private CommentResponseDTO toDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getIdComents(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUser().getIdUser(),
                comment.getUser().getName(),
                comment.getCard().getIdCard()
        );
    }
}