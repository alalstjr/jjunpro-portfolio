package com.backend.project.respone;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebProcessRespone {

    /**
     *	Web API Error Maps Respone
     *  String Type 웹 오류 응답
     *  오류를 한곳에 모아서 한번에 응답합니다.
     */
    public ResponseEntity webErrorRespone(Map<String, String> errorMap) {
        return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.ACCEPTED);
    }

    /**
     *	Web API Error Respone
     *  String Type 웹 오류 응답
     *  각각의 오류를 한번 응답합니다.
     */
    public ResponseEntity webErrorRespone(String errorType, String errorText) {
        Map<String, String> errorMap = new HashMap<String, String>();
        errorMap.put(errorType, errorText);
        return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.ACCEPTED);
    }

    /**
     *	Web API Error Respone
     *  BindingResult Type 웹 오류 응답
     */
    public ResponseEntity webErrorRespone(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<String, String>();

        for(FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.ACCEPTED);
    }
}
