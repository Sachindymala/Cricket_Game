package com.example.demo.service;

import com.example.demo.apiRequest.PlayerDTO;
import com.example.demo.entity.Player;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


public interface PlayerService {

    public Player createPlayer(PlayerDTO player);

    public Player showPlayer(Long id);

    public List<Player> getAllPlayers();

    public String deletePlayer(Long id);


    List<Player> saveAllPlayers(List<PlayerDTO> playerDTOList);

    List<Long> getPlayerIds(Long teamId);
}
