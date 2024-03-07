package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MatchStatsTbl")
@Data
public class MatchStats {

    @Id
    @GeneratedValue
    private Long matchStatsID;
    private Long matchID;
    private int overNum;
    private  int ballNum;
    private  int score;
    private int wicket;
    private Long  atBat;
    private Long atBall;
    private Long teamId;

}
