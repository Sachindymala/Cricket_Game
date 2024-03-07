package com.example.demo.repo;

import com.example.demo.entity.MatchStats;
import com.example.demo.entity.Playing11;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchStatsRepo extends JpaRepository<MatchStats, Long> {

    List<Playing11> findByTeamIdAndMatchId(Long teamId, Long matchId);

}
