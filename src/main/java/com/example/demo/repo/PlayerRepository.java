package com.example.demo.repo;

import com.example.demo.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlayerRepository extends JpaRepository<Player,Long> {
}
