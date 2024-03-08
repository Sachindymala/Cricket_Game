package com.example.demo.apiRequest;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TeamDTO {
    @NotNull
    private String name;
}
