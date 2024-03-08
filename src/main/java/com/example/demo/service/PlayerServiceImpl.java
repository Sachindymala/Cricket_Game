package com.example.demo.service;

import com.example.demo.apiRequest.PlayerDTO;
import com.example.demo.entity.Player;
import com.example.demo.entity.Team;
import com.example.demo.repo.PlayerRepository;
import com.example.demo.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.LinkLoopException;
import javax.validation.Valid;
import java.util.*;

@Service
public class PlayerServiceImpl implements  PlayerService{

    @Autowired
    private PlayerRepository playerRepo;
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Player createPlayer(PlayerDTO playerDTO) {
      Player player = new Player();
      player.setPlayerType(playerDTO.getPlayerType());
      player.setPlayerName(playerDTO.getPlayerName());

        if (playerDTO.getTeamId() != null) {
            Optional<Team> teamOptional = teamRepository.findById(playerDTO.getTeamId());
            teamOptional.ifPresent(player::setTeam);
        }
        return playerRepo.save(player);
    }

    @Override
    public Player showPlayer(Long id) {
         Optional<Player> playerOptional = playerRepo.findById(id);
         Player player = new Player();
         if (playerOptional.isPresent()){
             player = playerOptional.get();

         }
        return player;
    }

    @Override
    public List<Player> getAllPlayers(){
        return playerRepo.findAll();
    }

    @Override
    public String deletePlayer(Long id){
        Optional<Player> playerOptional = playerRepo.findById(id);
        if(playerOptional.isPresent()){
            playerRepo.delete(playerOptional.get());
            return "Player Deleted Successfully.";
        }
        return "Player does not exist.";
    }

    @Override
    public List<Player> saveAllPlayers(List<PlayerDTO> playerDTOList) {
        List<Player> playerList = new ArrayList<>();
        Map<Long,Team> teamCache = new HashMap<>();
        for(PlayerDTO playerDTO : playerDTOList) {
            Player player = new Player();
            player.setPlayerType(playerDTO.getPlayerType());
            player.setPlayerName(playerDTO.getPlayerName());

            if (playerDTO.getTeamId() != null) {
                if(!teamCache.containsKey(playerDTO.getTeamId())){
                    Optional<Team> teamOptional = teamRepository.findById(playerDTO.getTeamId());
                    if (teamOptional.isPresent()) {
                        teamCache.put(playerDTO.getTeamId(),teamOptional.get());
                    }else{
                        continue;
                    }
                }
                player.setTeam(teamCache.get(playerDTO.getTeamId()));
                playerList.add(player);
            }
        }
        return playerRepo.saveAll(playerList);
    }

    @Override
    public List<Long> getPlayerIds(Long teamId) {
        return playerRepo.findPlayerIdByTeamId(teamId);
    }
}
