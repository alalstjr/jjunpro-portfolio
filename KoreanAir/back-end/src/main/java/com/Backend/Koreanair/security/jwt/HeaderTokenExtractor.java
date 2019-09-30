package com.backend.koreanair.security.jwt;

import com.backend.koreanair.respone.JwtProcessRespone;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HeaderTokenExtractor {

    @Autowired
    JwtProcessRespone jwtProcessRespone;

    /*
     * HEADER_PREFIX
     * header Authorization token 값의 표준이되는 변수
     */
    public final String HEADER_PREFIX = "Bearer ";

    public String extract(String header) throws JsonProcessingException, IOException {
        /*
         * - Token 값이 올바르지 않은경우 -
         * header 값이 비어있거나 또는 HEADER_PREFIX 값보다 짧은 경우
         * 이셉션을(예외)를 던져주어야 합니다.
         */
        if(header.equals("") || header.length() < HEADER_PREFIX.length()) {
            jwtProcessRespone.jwtStateCheckRespone();
        }

        /*
         * - Token 값이 존재하는 경우 -
         * (bearer ) 부분만 제거 후 token 값 반환
         */
        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
