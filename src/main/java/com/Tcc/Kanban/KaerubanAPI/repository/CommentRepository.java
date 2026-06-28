package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByCard_IdCardOrderByCreatedAtDesc(Integer cardId);
}