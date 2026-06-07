package com.Tcc.Kanban.Kaeroban.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChecklists;

    @Column(nullable = false, length = 425)
    private String title;

    @Column(nullable = false)
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "fk_card", nullable = false)
    private Card card;
}
