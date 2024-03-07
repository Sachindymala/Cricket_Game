package com.example.demo.service;

import com.example.demo.apiRequest.CreateMatchData;
import com.example.demo.apiRequest.ScoreBoard;

public interface MatchService {

    public Long createMatch(CreateMatchData createMatchData);
    public Long startToss(Long matchId);

    public Long simulateMatch(Long matchId);

    public ScoreBoard getScoreBoard(Long matchId);

}
