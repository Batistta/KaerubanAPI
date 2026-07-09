package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    List<Card> findByColumn_IdOrderByPosition(UUID columnId);
    Long countByColumn_Id(UUID idColumn);
    List<Card> findByColumn_IdAndPositionGreaterThanAndPositionLessThanEqual(
            UUID columnId, Integer oldPosition, Integer newPosition);    //position > oldPosition and position <= newPosition
    List<Card> findByColumn_IdAndPositionLessThanAndPositionGreaterThanEqual(
            UUID columnId, Integer oldPosition, Integer newPosition);    // position >= newPosition and position < oldPosition
    List<Card> findByColumn_IdAndPositionGreaterThan(UUID columnId, Integer position);
    List<Card> findByColumn_IdAndPositionGreaterThanEqual(UUID columnId, Integer position);
}