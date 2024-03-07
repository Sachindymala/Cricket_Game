package com.example.demo.apiRequest;

import lombok.Data;

import javax.persistence.Column;

@Data
public class PlayerDTO {

    private String playerName;
    private String playerType;
    private Long teamId;
}
