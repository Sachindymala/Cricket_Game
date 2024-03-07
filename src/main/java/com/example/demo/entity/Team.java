package com.example.demo.entity;


import lombok.Data;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Team {

    @Id
    @Column(name = "TeamId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long teamId;

    @Column(name = "TeamName")
     private String  teamName;


}
