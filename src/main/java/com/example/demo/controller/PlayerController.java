package com.example.demo.controller;

import com.example.demo.apiRequest.PlayerDTO;
import com.example.demo.entity.Player;
import com.example.demo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;


    @PostMapping("/player")
    public boolean createPlayer(@RequestBody PlayerDTO playerDTO){
        return playerService.createPlayer(playerDTO);
    }

    @GetMapping("/player")
    public Optional<Player> getPlayers(@RequestParam Long id){
        return playerService.showPlayer(id);
    }

    @GetMapping("/allPlayers")
    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @DeleteMapping("/player")
    public boolean deletePlayer(@RequestParam Long id){
        return playerService.deletePlayer(id);
    }

}
