package com.example.demo.controller;

import com.example.demo.apiRequest.TeamDTO;
import com.example.demo.entity.Player;
import com.example.demo.entity.Team;
import com.example.demo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/team")
    public Team crateTeam(@RequestBody TeamDTO team){
        return teamService.createTeam(team);
    }

    @GetMapping("/team")
    public Optional<Team> getTeam(@RequestParam Long id){
        return teamService.getTeam(id);
    }

    @GetMapping("/allTeam")
    public List<Team> getAllTeams(){
        return teamService.getAllTeams();
    }

    @DeleteMapping("/team")
    public String deleteTeam(@RequestParam Long id){
        return teamService.deleteTeam(id);
    }
}
