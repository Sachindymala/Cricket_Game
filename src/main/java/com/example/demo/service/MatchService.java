package com.example.demo.service;

import com.example.demo.apiRequest.*;
import com.example.demo.entity.MatchDtls;

import java.util.List;

public interface MatchService {

    public MatchCreationResponse createMatch(CreateMatchData createMatchData);
    public TossResult startToss(Long matchId);

    public Long simulateMatch(Long matchId);

    public ScoreBoard getScoreBoard(Long matchId);

    public List<OverStats> getOverStats(Long teamId, Long matchId);
}
