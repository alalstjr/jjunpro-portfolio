package com.backend.koreanair.respone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *	JWT 인증 알고리즘에 따른 결과 안내문
 *	(사용자, 개발자) 에게 적절한 안내문을 출력해주는 Class
 */
@Component
public class JwtProcessRespone {

    private static final Logger log = LoggerFactory.getLogger(JwtProcessRespone.class);

    @Autowired
    private ObjectMapper objectMapper;

    /**
     *	JWT 상태 검사 Respone
     * @throws IOException
     * @throws JsonProcessingException
     */
    public void jwtStateCheckRespone() throws JsonProcessingException, IOException {

        HttpServletResponse res = (HttpServletResponse) RequestContextHolder.currentRequestAttributes();

        Map<String, Object> json = new HashMap<String, Object>();
        json.put("error", "올바른 JWT 정보가 아닙니다.");

        String e = "올바른 JWT 정보가 아닙니다.";

        log.error(e);

        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpStatus.ACCEPTED.value());
        res.getWriter().print(objectMapper.writeValueAsString(json));
    }
}
