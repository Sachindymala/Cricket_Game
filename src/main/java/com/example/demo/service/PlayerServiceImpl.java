package com.example.demo.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.apiRequest.PlayerDTO;
import com.example.demo.entity.Player;
import com.example.demo.entity.Team;
import com.example.demo.repo.PlayerRepository;
import com.example.demo.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements  PlayerService{

    @Autowired
    PlayerRepository playerRepo;
    @Autowired
    TeamRepository teamRepository;

    @Override
    public boolean createPlayer(PlayerDTO playerDTO) {
      Player player = new Player();
      player.setPlayerType(playerDTO.getPlayerType());
      player.setPlayerName(playerDTO.getPlayerName());

        if (playerDTO.getTeamId() != null) {
            Optional<Team> teamOptional = teamRepository.findById(playerDTO.getTeamId());
            teamOptional.ifPresent(player::setTeam);
        }
        playerRepo.save(player);
        return true;
    }

    @Override
    public Optional<Player> showPlayer(Long id) {
        return playerRepo.findById(id);
    }

    @Override
    public List<Player> getAllPlayers(){
        return playerRepo.findAll();
    }

    @Override
    public boolean deletePlayer(Long id){
        Optional<Player> playerOptional = playerRepo.findById(id);
        if(playerOptional.isPresent()){
            playerRepo.delete(playerOptional.get());
            return true;
        }
        return false;
    }

}
