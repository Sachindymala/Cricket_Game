package com.example.demo.repo;

import com.example.demo.entity.MatchDtls;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MatchRepo extends JpaRepository<MatchDtls,Long> {

}
