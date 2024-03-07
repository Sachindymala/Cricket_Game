package com.example.demo.service;

import com.example.demo.apiRequest.CreateMatchData;
import com.example.demo.apiRequest.ScoreBoard;
import com.example.demo.entity.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchServiceImpl  implements MatchService {

    @Autowired
    MatchRepo matchRepo;
    @Autowired
    MatchStatsRepo matchStatsRepo;
    @Autowired
    TeamRepository teamRepo;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    Playing11Repo playing11Repo;
    @Autowired
    Playing11Repo playing11Repository;

    @Override
    public Long createMatch(CreateMatchData matchData){
        Long teamAId = matchData.getTeam1ID();
        Long teamBId = matchData.getTeam1ID();
        MatchDtls match = new MatchDtls();
        match.setMatchName(matchData.getMatchName());
        match.setVenue(matchData.getVenue());
        match.setTeam1ID(matchData.getTeam1ID());
        match.setTeam2ID(matchData.getTeam2ID());
        match.setDate(new Date());
        match.setNumberOfovers(matchData.getNumberOfOvers());

        MatchDtls match1 =  matchRepo.save(match);
        Long matchId = match1.getMatchId();

        List<Long> teamAPlayerIDs = matchData.getTeam1Playing();
        List<Long> teamBPlayerIDs = matchData.getTeam2Playing();

        storePlaying11(teamAPlayerIDs,match1);
        storePlaying11(teamBPlayerIDs,match1);

        return matchId;
    }
    private void storePlaying11(List<Long> teamPlayerIDs, MatchDtls match1) {
        if(teamPlayerIDs !=null || teamPlayerIDs.size()>0){
            List<Player> players = playerRepository.findAllById(teamPlayerIDs);
            List<Playing11> playing11s = new ArrayList<>();
            for(Player player : players){
                Playing11 playing11 = new Playing11();
                playing11.setPlayerID(player.getPlayerId());
                playing11.setTeamId(player.getTeam().getTeamId());
                playing11.setMatchId(match1.getMatchId());
                playing11s.add(playing11);
            }
            playing11Repo.saveAll(playing11s);
        }
    }

    @Override
    public Long startToss(Long matchId) {
        Optional<MatchDtls> matchOptional = matchRepo.findById(matchId);
        //0 can be mapped as errorcode for invalid error code.
        Long winnerID = 0l;
        if(matchOptional.isPresent()){
            MatchDtls match = matchOptional.get();
            Random rand = new Random();
            int rand_int = rand.nextInt(1000);
            //if random int is odd or eve
            if(rand_int%2==0)
                winnerID = match.getTeam1ID();
            else
                winnerID = match.getTeam2ID();

            match.setToss(winnerID);
            matchRepo.save(match);
        }
        return winnerID;
    }

    @Override
    public Long simulateMatch(Long matchID) {
        Optional<MatchDtls> matchOptional = matchRepo.findById(matchID);
        if (matchOptional.isPresent()) {
            MatchDtls match = matchOptional.get();

            Long tossWinner = match.getToss();
            if (tossWinner == null) {
                tossWinner = startToss(matchID);
            }
            Long[] teams = new Long[2];
            teams[0] = tossWinner;
            if (teams[0] == match.getTeam1ID()) {
                teams[1] = match.getTeam2ID();
            } else {
                teams[1] = match.getTeam1ID();
            }

            int[] teamScore = new int[2];
            List<Playing11> team1 = playing11Repository.findByTeamIdAndMatchId(teams[0], matchID);
            List<Playing11> team2 = playing11Repository.findByTeamIdAndMatchId(teams[1], matchID);

            teamScore[0] =
                    startInnings(match, team1, team2, matchID);
            teamScore[1] =
                    startInnings(match, team2, team1, matchID);
            Long victorID ;
            if (teamScore[0] > teamScore[1]) {
                victorID = teams[0];
            }else {
                victorID = teams[1];
            }
            match.setVictor(victorID);
            matchRepo.save(match);
           return victorID;

        }
        return  0l;
    }

    @Override
    public ScoreBoard getScoreBoard(Long matchId) {

        return null;
    }


    int startInnings(MatchDtls match, List<Playing11> batTeam, List<Playing11> ballTeam, Long matchId) {
       int score = 0;
       int wicket = 0;

       Long atBat = batTeam.remove(batTeam.size()-1).getId();
       Long atNonBat = batTeam.remove(batTeam.size()-1).getId();
       int numberOfOvers = match.getNumberOfovers();
       for(int i=1;i<=numberOfOvers;i++){
           Long atBall = ballTeam.get((i-1)%ballTeam.size()).getId();
           for(int j=1;j<=6;j++){
               int run = getRandom();
               storeOutCome(atBat,atBall,matchId,i,j,run);
               if(run==7){
                   atBat = batTeam.remove(batTeam.size()-1).getId();
                   wicket++;
                   if(batTeam.size()<=1 || wicket>=10){
                       return score;
                   }
               }else if(run%2!=0){
                   Long t = atNonBat;
                   atNonBat = atBat;
                   atBat = t;
                   score += run;
               }else{
                   score += run;
               }
           }
           Long t = atNonBat;
           atNonBat = atBat;
           atBat = t;
       }
       return score;

   }


    private void storeOutCome(Long atBat, Long atBall, Long matchId, int i, int j, int run) {
        MatchStats matchStats = new MatchStats();
        matchStats.setMatchID(matchId);
        matchStats.setAtBall(atBall);
        matchStats.setAtBat(atBat);
        matchStats.setOverNum(i);
        matchStats.setBallNum(j);
        if(run==7){
            matchStats.setScore(0);
            matchStats.setWicket(1);
        }else{
            matchStats.setScore(run);
        }
        matchStatsRepo.save(matchStats);
    }

    private int getRandom() {
        Random rand = new Random();
        return rand.nextInt(8);
    }
}
