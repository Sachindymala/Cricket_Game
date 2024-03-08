package com.example.demo.service;

import com.example.demo.apiRequest.TeamDTO;
import com.example.demo.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {

    public Team createTeam(TeamDTO team);

    public List<Team> getAllTeams();

    public Optional<Team> getTeam(Long id);

    public String deleteTeam(Long id);


}
