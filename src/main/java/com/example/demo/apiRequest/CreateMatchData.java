package com.example.demo.apiRequest;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class CreateMatchData {

    @NotNull
    private String matchName;
    private String Venue;
    private Long team1ID;
    private List<Long> playingPlayersId;
    private Long team2ID;
    private int numberOfOvers;

}
