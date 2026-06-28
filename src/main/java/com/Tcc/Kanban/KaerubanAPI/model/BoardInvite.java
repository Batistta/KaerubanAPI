package com.Tcc.Kanban.KaerubanAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class BoardInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer idInvite;

    @ManyToOne
    @JoinColumn(name = "fk_board", nullable = false)
    private Board board;

    @Column(nullable = false, length = 8)
    private String code;

    private LocalDate expiresAt;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
