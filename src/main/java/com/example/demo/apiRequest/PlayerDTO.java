package com.example.demo.apiRequest;

import com.example.demo.entity.PlayerType;

import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data

public class PlayerDTO {

    @NotNull
    private String playerName;
    @NotNull
    private PlayerType playerType;
    @NotNull
    private Long teamId;
}

