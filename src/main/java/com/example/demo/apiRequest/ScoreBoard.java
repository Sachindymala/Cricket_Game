package com.example.demo.apiRequest;

import lombok.Data;

@Data
public class ScoreBoard {

    private Long team1ID;
    private Long team2ID;

    private int team1Score;
    private int Team2Score;

    private String winner;
    private String discription;

}
