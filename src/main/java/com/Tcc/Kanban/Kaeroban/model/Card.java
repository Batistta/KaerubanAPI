package com.Tcc.Kanban.Kaeroban.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCard;

    @ManyToOne
    @JoinColumn(name = "fk_column", nullable = false)
    private BoardColumn column;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Integer position;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDate dueDate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "card_label",
            joinColumns = @JoinColumn(name = "fk_card"),
            inverseJoinColumns = @JoinColumn(name = "fk_label")
    )
    private List<Label> labels = new ArrayList<>();

}