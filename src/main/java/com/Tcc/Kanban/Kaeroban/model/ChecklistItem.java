package com.Tcc.Kanban.Kaeroban.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ChecklistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;

    @ManyToOne
    @JoinColumn(name = "fk_checklist", nullable = false)
    private Checklist checklist;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private Boolean isChecked;

    @Column(nullable = false)
    private Integer position;
}