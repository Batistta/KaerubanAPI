package com.Tcc.Kanban.KaerubanAPI.repository;

import com.Tcc.Kanban.KaerubanAPI.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    List<Card> findByColumn_IdColumnOrderByPosition(Integer columnId);
    Long countByColumn_IdColumn(Integer idColumn);

    //position > oldPosition and position <= newPosition
    List<Card> findByColumn_IdColumnAndPositionGreaterThanAndPositionLessThanEqual(
            Integer columnId, Integer oldPosition, Integer newPosition);
    // position >= newPosition and position < oldPosition
    List<Card> findByColumn_IdColumnAndPositionLessThanAndPositionGreaterThanEqual(
            Integer columnId, Integer oldPosition, Integer newPosition);
    List<Card> findByColumn_IdColumnAndPositionGreaterThan(Integer columnId, Integer position);
    List<Card> findByColumn_IdColumnAndPositionGreaterThanEqual(Integer columnId, Integer position);
}