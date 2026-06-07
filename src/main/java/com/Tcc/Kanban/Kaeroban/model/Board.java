package com.Tcc.Kanban.Kaeroban.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer idBoard;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 7)
    private String color;

    @ManyToOne
    @JoinColumn(name = "fk_owner", nullable = false)
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "board_member",
            joinColumns = @JoinColumn(name = "fk_boards_id_board"),
            inverseJoinColumns = @JoinColumn(name = "fk_users_id_user")
    )
    private List<User> members;
}