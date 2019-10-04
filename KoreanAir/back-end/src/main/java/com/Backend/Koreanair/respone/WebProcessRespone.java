package com.backend.koreanair.respone;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebProcessRespone {

    /**
     *	Web API Error Respone
     *  String Type 웹 오류 응답
     */
    public ResponseEntity webErrorRespone(String errorType, String errorText) {
        Map<String, String> errorMap = new HashMap<String, String>();
        errorMap.put(errorType, errorText);

        return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
