package com.example.demo.apiRequest;

import lombok.Data;

@Data
public class TossResult {
    private Long tossWinnerId;
    private Long team1Id;
    private Long team2Id;
    private String description;
}
