package com.example.demo.service;

import com.example.demo.apiRequest.PlayerDTO;
import com.example.demo.entity.Player;

import java.util.List;
import java.util.Optional;


public interface PlayerService {

    public boolean createPlayer(PlayerDTO player);

    public Optional<Player> showPlayer(Long id);

    public List<Player> getAllPlayers();

    public boolean deletePlayer(Long id);

}
