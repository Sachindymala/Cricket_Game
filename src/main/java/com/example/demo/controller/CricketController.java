package com.example.demo.controller;

import com.example.demo.apiRequest.CreateMatchData;
import com.example.demo.apiRequest.PlayerDTO;
import com.example.demo.apiRequest.TeamDTO;
import com.example.demo.entity.Player;
import com.example.demo.entity.Team;
import com.example.demo.entity.Toss;
import com.example.demo.repo.PlayerRepository;
import com.example.demo.service.MatchService;
import com.example.demo.service.PlayerService;
import com.example.demo.service.TeamService;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
public class CricketController {

}
