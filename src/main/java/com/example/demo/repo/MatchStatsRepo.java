package com.example.demo.repo;

import com.example.demo.entity.MatchStats;
import com.example.demo.entity.Playing11;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchStatsRepo extends JpaRepository<MatchStats, Long> {


    @Query("SELECT ms.overNum,SUM(ms.runs),SUM(ms.wicket) FROM MatchStats ms WHERE ms.teamId = :teamId AND ms.matchID = :matchID GROUP BY ms.overNum")
    List<Object[]> findStatsByTeamId(@Param("teamId") Long teamId,@Param("matchID") Long matchID);

    @Query("SELECT SUM(ms.runs)  FROM MatchStats ms WHERE ms.teamId = :teamId AND ms.matchID = :matchID")
    Integer findScoreByTeamId( @Param("teamId") Long teamId,@Param("matchID") Long matchID);


}
