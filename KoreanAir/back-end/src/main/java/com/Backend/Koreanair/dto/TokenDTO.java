package com.backend.koreanair.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;

    public TokenDTO(String token, String userId, String username) {
        super();
        this.token = token;
        this.userId = userId;
        this.username = username;
    }
}