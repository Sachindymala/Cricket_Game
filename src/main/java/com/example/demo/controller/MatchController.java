package com.example.demo.controller;

import com.example.demo.apiRequest.CreateMatchData;
import com.example.demo.apiRequest.ScoreBoard;
import com.example.demo.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    MatchService matchService;

    @PostMapping("/createMatch")
    public long createMatch(@RequestBody CreateMatchData matchData) {
        return matchService.createMatch(matchData);
    }

    @GetMapping("/toss")
    public long getTossMatch(@RequestParam Long matchId) {
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

}
