package com.Tcc.Kanban.Kaeroban.repository;

import com.Tcc.Kanban.Kaeroban.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    List<Card> findByColumn_IdColumnOrderByPosition(Integer columnId);
    int countByColumn_IdColumn(Integer idColumn);

}