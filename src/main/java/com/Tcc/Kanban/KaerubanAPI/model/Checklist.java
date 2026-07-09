package com.Tcc.Kanban.KaerubanAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "checklist")
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_checklist", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 425)
    private String title;

    @Column(nullable = false)
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_card", nullable = false, columnDefinition = "BINARY(16)")
    private Card card;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Checklist other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
