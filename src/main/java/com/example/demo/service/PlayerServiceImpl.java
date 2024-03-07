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
    private PlayerRepository playerRepo;
    @Autowired
    private TeamRepository teamRepository;

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


}
