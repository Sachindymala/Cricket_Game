package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="Player")
public class Player {

    @Id
    @Column(name = "PlayerID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    @Column(name = "PlayerName")
    private String playerName;

    @Column(name = "PlayerType")
    private String playerType;

    @ManyToOne
    private Team team;

}


