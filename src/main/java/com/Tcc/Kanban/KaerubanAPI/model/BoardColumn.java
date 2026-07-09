package com.Tcc.Kanban.KaerubanAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "`column`")
public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_column", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_board", nullable = false, columnDefinition = "BINARY(16)")
    private Board board;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false, length = 7)
    private String color;

    private Integer wipLimit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardColumn other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}