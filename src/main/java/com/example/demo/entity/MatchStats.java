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
    private Long matchStatsId;
    private Long matchID;
    private int overNum;
    private  int ballNum;
    private  int runs;
    private int wicket;
    private Long batsmenPlayerId;
    private Long bowlerPlayerId;
    private Long teamId;


}
