package com.example.demo.service;

import com.example.demo.apiRequest.TeamDTO;
import com.example.demo.entity.Player;
import com.example.demo.entity.Team;
import com.example.demo.repo.PlayerRepository;
import com.example.demo.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements  TeamService{

    @Autowired
    private TeamRepository teamRepo;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public boolean createTeam(TeamDTO teamDTO) {
        Team team = new Team();
        team.setTeamName(teamDTO.getName());
        teamRepo.save(team);
        return true;
    }

    @Override
    public List<Team>  getAllTeams() {
        return teamRepo.findAll();
    }

    @Override
    public Optional<Team> getTeam(Long id){
        return teamRepo.findById(id);
    }

    @Override
    public boolean deleteTeam(Long id){
        Optional<Team> teamOptional = teamRepo.findById(id);
        if(teamOptional.isPresent()){
            teamRepo.delete(teamOptional.get());
            return true;
        }
        return false;
    }


}



