package com.Tcc.Kanban.KaerubanAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "`column`")
public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idColumn;

    @ManyToOne
    @JoinColumn(name = "fk_board", nullable = false)
    private Board board;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false, length = 7)
    private String color;

    private Integer wipLimit;
}