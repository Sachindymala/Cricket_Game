package com.example.demo.controller;

import com.example.demo.apiRequest.*;
import com.example.demo.entity.MatchDtls;
import com.example.demo.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    MatchService matchService;

    @PostMapping()
    public MatchCreationResponse createMatch(@RequestBody CreateMatchData matchData) {
        return matchService.createMatch(matchData);
    }

    @GetMapping("/toss")
    public TossResult getTossMatch(@RequestParam Long matchId) {
        return matchService.startToss(matchId);
    }

    @GetMapping("/simulateMatch")
    public long simulateMatch(@RequestParam Long matchId) {
        return matchService.simulateMatch(matchId);
    }

    @GetMapping("/scoreBoard")
    public ScoreBoard getScoreBoard(@RequestParam Long matchId){
        return matchService.getScoreBoard(matchId);
    }

    @GetMapping("/oversStats")
    public List<OverStats> getOverStats(@RequestParam Long teamId, Long matchId){
        return matchService.getOverStats(teamId,matchId);
    }



}
