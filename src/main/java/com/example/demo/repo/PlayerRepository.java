package com.example.demo.repo;

import com.example.demo.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {

    @Query("SELECT p.playerId  FROM Player p WHERE team_team_id = :teamId")
    List<Long> findPlayerIdByTeamId(@Param("teamId") Long teamId);
}
