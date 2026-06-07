package com.Tcc.Kanban.Kaeroban.repository;

import com.Tcc.Kanban.Kaeroban.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByCard_IdCardOrderByCreatedAtDesc(Integer cardId);
}