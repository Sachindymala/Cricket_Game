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
    public boolean crateTeam(@RequestBody TeamDTO team){
        return teamService.createTeam(team);
    }

}
