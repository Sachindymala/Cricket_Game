package com.example.demo.controller;

import com.example.demo.apiRequest.OverStats;
import com.example.demo.apiRequest.PlayerDTO;
import com.example.demo.entity.Player;
import com.example.demo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;


    @PostMapping("/player")
    public Player createPlayer(@RequestBody @Valid PlayerDTO playerDTO){
        return playerService.createPlayer(playerDTO);
    }

    @GetMapping("/player")
    public Player getPlayers(@RequestParam Long id){
        return playerService.showPlayer(id);
    }
    @GetMapping("/allPlayers")
    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @DeleteMapping("/player")
    public String deletePlayer(@RequestParam Long id){
        return playerService.deletePlayer(id);
    }

    @PostMapping("/allPlayers")
    public List<Player> saveAllPlayer(@RequestBody List<PlayerDTO> playerDTOList){
        return playerService.saveAllPlayers(playerDTOList);
    }

    @GetMapping("/playerIds")
    public List<Long> getPlayerIds(@RequestParam Long teamId){
        return playerService.getPlayerIds(teamId);
    }

}
