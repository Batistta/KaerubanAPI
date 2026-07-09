package com.Tcc.Kanban.KaerubanAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "checklist_item")
public class ChecklistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_item", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "fk_checklist", nullable = false, columnDefinition = "BINARY(16)")
    private Checklist checklist;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private Boolean isChecked;

    @Column(nullable = false)
    private Integer position;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChecklistItem other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}