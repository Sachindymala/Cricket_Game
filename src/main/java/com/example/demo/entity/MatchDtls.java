package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class MatchDtls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    private String matchName;
    private String venue;
    private Date date;
    private Long team1ID;
    private Long team2ID;
    private int numberOfOvers;
    private Long tossWinner;
    private Long winner;


//    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
//    private List<Playing11> playing11s;

//    @ManyToMany
//    private List<Player> players;
}
