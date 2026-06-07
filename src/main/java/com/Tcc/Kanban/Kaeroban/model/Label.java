package com.Tcc.Kanban.Kaeroban.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLabel;

    @ManyToOne
    @JoinColumn(name = "fk_board", nullable = false)
    private Board board;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 7)
    private String color;
}