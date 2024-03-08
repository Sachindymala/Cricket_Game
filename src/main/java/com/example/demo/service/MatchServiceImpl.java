package com.example.demo.service;

import com.example.demo.apiRequest.*;
import com.example.demo.entity.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Service
public class MatchServiceImpl  implements MatchService {

    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private MatchStatsRepo matchStatsRepo;
    @Autowired
    private TeamRepository teamRepo;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private Playing11Repo playing11Repo;
    @Autowired
    private Playing11Repo playing11Repository;

    @Override
    public MatchCreationResponse createMatch(CreateMatchData matchData){
        MatchDtls match = new MatchDtls();
        MatchCreationResponse matchResponse = new MatchCreationResponse();
        match.setMatchName(matchData.getMatchName());
        match.setVenue(matchData.getVenue());
        match.setTeam1ID(matchData.getTeam1ID());
        match.setTeam2ID(matchData.getTeam2ID());
        match.setDate(new Date());
        match.setNumberOfOvers(matchData.getNumberOfOvers());

        MatchDtls matchDtls=  matchRepo.save(match);
        List<Long> playerIDs = matchData.getPlayingPlayersId();
        Map<Long, Integer> indTeamCount = new HashMap<>();
        indTeamCount.put(matchDtls.getTeam1ID(),0);
        indTeamCount.put(matchDtls.getTeam2ID(),0);
        String playersStoreResult = storePlaying11(playerIDs,matchDtls,indTeamCount);
        if(playersStoreResult.equalsIgnoreCase("Done")){
            matchResponse.setMatchDtls(matchDtls);
            matchResponse.setDescription("MatchCreatedSuccessfully");
            return matchResponse;
        }
        matchRepo.delete(matchDtls);
        matchResponse.setDescription(playersStoreResult);
        return matchResponse;
    }
    private String  storePlaying11(List<Long> teamPlayerIDs, MatchDtls match1,Map<Long,Integer> indCount) {
        if(teamPlayerIDs !=null || teamPlayerIDs.size()>0){

            List<Player> players = playerRepository.findAllById(teamPlayerIDs);
            if(teamPlayerIDs.size() != players.size()){
                return "Some Players Not Found";
            }
            List<Playing11> playing11s = new ArrayList<>();
            for(Player player : players){
                Playing11 playing11 = new Playing11();
                Long playerTeamId = player.getTeam().getTeamId();
                if(indCount.containsKey(playerTeamId)){
                    indCount.put(player.getTeam().getTeamId(),indCount.get(playerTeamId)+1);
                }else{
                    return "Player does not belong to either team "+player.getPlayerId();
                }
                playing11.setTeamId(playerTeamId);
                playing11.setPlayerId(player.getPlayerId());
                playing11.setMatchId(match1.getMatchId());
                playing11s.add(playing11);
            }
            if(indCount.get(match1.getTeam1ID())!=indCount.get(match1.getTeam2ID())){
                return "Both the team does not contain equal number of players.";
            }
            playing11Repo.saveAll(playing11s);
            return "Done";
        }
        return "no players passed.";
    }

    @Override
    public TossResult startToss(Long matchId) {
        Optional<MatchDtls> matchOptional = matchRepo.findById(matchId);
        //0 can be mapped as errorcode for invalid error code.
        TossResult tossResult = new TossResult();
        Long winnerID;
        if(matchOptional.isPresent()){
            MatchDtls match = matchOptional.get();
            Random rand = new Random();
            int rand_int = rand.nextInt(1000);
            //if random int is odd or eve
            if(rand_int%2==0)
                winnerID = match.getTeam1ID();
            else
                winnerID = match.getTeam2ID();

            match.setTossWinner(winnerID);
            matchRepo.save(match);
            tossResult.setTeam1Id(match.getTeam1ID());
            tossResult.setTeam2Id(match.getTeam2ID());
            tossResult.setDescription("Toss Successful");
            tossResult.setTossWinnerId(winnerID);
        }else{
            tossResult.setDescription("Pls pass valid Match id");
        }
        return tossResult;
    }

    @Override
    public Long simulateMatch(Long matchID) {
        Optional<MatchDtls> matchOptional = matchRepo.findById(matchID);
        if (matchOptional.isPresent()) {
            MatchDtls match = matchOptional.get();

            Long tossWinner = match.getTossWinner();
            if (tossWinner == null) {
                tossWinner = startToss(matchID).getTossWinnerId();
            }
            Long[] teams = new Long[2];
            teams[0] = tossWinner;
            if (Objects.equals(teams[0], match.getTeam1ID())) {
                teams[1] = match.getTeam2ID();
            } else {
                teams[1] = match.getTeam1ID();
            }

            int[] teamScore = new int[2];
            List<Playing11> team1 = playing11Repository.findByTeamIdAndMatchId(teams[0], matchID);
            List<Playing11> team2 = playing11Repository.findByTeamIdAndMatchId(teams[1], matchID);

            List<Playing11> team1SecondInn = new ArrayList<>(team2);
            List<Playing11> team2SecondInn = new ArrayList<>(team1);

            teamScore[0] =
                    startInnings(match, team1, team2, matchID,teams[0]);
            teamScore[1] =
                    startInnings(match, team1SecondInn, team2SecondInn, matchID,teams[1]);
            Long victorID ;
            if (teamScore[0] > teamScore[1]) {
                victorID = teams[0];
            }else {
                victorID = teams[1];
            }
            match.setWinner(victorID);
            matchRepo.save(match);
           return victorID;

        }
        return 0L;
    }

    @Override
    public ScoreBoard getScoreBoard(Long matchId) {
        ScoreBoard scoreBoard = new ScoreBoard();

        Optional<MatchDtls> optionalMatchDtls = matchRepo.findById(matchId);
        if(optionalMatchDtls.isPresent()){
            MatchDtls matchDtls = optionalMatchDtls.get();
            scoreBoard.setTeam1ID(matchDtls.getTeam1ID());
            scoreBoard.setTeam1Score(matchStatsRepo.findScoreByTeamId( scoreBoard.getTeam1ID(),matchId));
            scoreBoard.setTeam2ID(matchDtls.getTeam2ID());
            scoreBoard.setTeam2Score(matchStatsRepo.findScoreByTeamId( scoreBoard.getTeam2ID(),matchId));
            Optional<Team> optionalTeam = teamRepo.findById(matchDtls.getWinner());
            optionalTeam.ifPresent(team -> scoreBoard.setWinner(team.getTeamName()));
        }else{
            scoreBoard.setDiscription("Pls pass valid Match id");
        }
        return scoreBoard;
    }

    @Override
    public List<OverStats> getOverStats(Long teamId, Long matchId) {
        List<OverStats> overStatsList = new ArrayList<>();
        List<Object[]> tempStats = matchStatsRepo.findStatsByTeamId(teamId,matchId);

        for (Object[] row : tempStats) {
            OverStats overStats = new OverStats();

            overStats.setOverNum((Integer) row[0]);
            overStats.setScore(((Number) row[1]).intValue());
            overStats.setWicket(((Number) row[2]).intValue());

            overStatsList.add(overStats);
        }
        return overStatsList;

    }


    int startInnings(MatchDtls match, List<Playing11> batTeam, List<Playing11> ballTeam, Long matchId,Long atBatTeam) {
       int score = 0;
       int wicket = 0;

       Long atBat = batTeam.remove(batTeam.size()-1).getPlayerId();
       Long atNonBat = batTeam.remove(batTeam.size()-1).getPlayerId();
       int numberOfOvers = match.getNumberOfOvers();
       for(int i=1;i<=numberOfOvers;i++){
           Long atBall = ballTeam.get((i-1)%ballTeam.size()).getPlayerId();
           for(int j=1;j<=6;j++){
               int run = getRandom();
               storeOutCome(atBat,atBall,matchId,i,j,run,atBatTeam);
               if(run==7){
                   wicket++;
                   if(batTeam.isEmpty() || wicket>=10){
                       return score;
                   }
                   atBat = batTeam.remove(batTeam.size()-1).getPlayerId();
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


    private void storeOutCome(Long atBat, Long atBall, Long matchId, int i, int j, int run,long teamId) {
        MatchStats matchStats = new MatchStats();
        matchStats.setMatchID(matchId);
        matchStats.setBowlerPlayerId(atBall);
        matchStats.setBatsmenPlayerId(atBat);
        matchStats.setOverNum(i);
        matchStats.setBallNum(j);
        matchStats.setTeamId(teamId);
        if(run==7){
            matchStats.setRuns(0);
            matchStats.setWicket(1);
        }else{
            matchStats.setRuns(run);
        }
        matchStatsRepo.save(matchStats);
    }

    private int getRandom() {
        Random rand = new Random();
        return rand.nextInt(8);
    }
}
