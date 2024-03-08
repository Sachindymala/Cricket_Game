package com.example.demo.apiRequest;

import com.example.demo.entity.MatchDtls;
import lombok.Data;

@Data
public class MatchCreationResponse {
    private MatchDtls matchDtls;
    private String description;
}
