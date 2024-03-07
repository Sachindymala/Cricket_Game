package com.example.demo.entity;


import lombok.Data;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Team {

    @Id
    @Column(name = "TeamId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long teamId;

    @Column(name = "TeamName")
     private String  teamName;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Player> players;
}







/*public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public String getViceCaptain() {
        return viceCaptain;
    }

    public void setViceCaptain(String viceCaptain) {
        this.viceCaptain = viceCaptain;
    }

    public Team(int teamId, String teamName, String captain, String viceCaptain) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.captain = captain;
        this.viceCaptain = viceCaptain;
    }

    public Team() {
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", captain='" + captain + '\'' +
                ", viceCaptain='" + viceCaptain + '\'' +
                '}';
    }*/