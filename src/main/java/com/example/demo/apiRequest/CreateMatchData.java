package com.example.demo.apiRequest;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateMatchData {

    private String matchName;
    private String Venue;
    private Long team1ID;
    private List<Long> team1Playing;
    private Long team2ID;
    private List<Long> team2Playing;
    private int NumberOfOvers;

}
