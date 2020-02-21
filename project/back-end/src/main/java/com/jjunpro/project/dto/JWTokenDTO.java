package com.jjunpro.project.dto;

import lombok.Data;

@Data
public class JWTokenDTO {

    private Long id;

    private String jwtoken;

    private String username;

    private String nickname;

    public JWTokenDTO(
            Long id,
            String jwtoken,
            String username,
            String nickname
    ) {
        this.id = id;
        this.jwtoken = jwtoken;
        this.username = username;
        this.nickname = nickname;
    }
}
