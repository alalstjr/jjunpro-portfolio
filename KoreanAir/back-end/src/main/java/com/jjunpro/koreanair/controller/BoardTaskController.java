package com.jjunpro.koreanair.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjunpro.koreanair.board.BoardTask;
import com.jjunpro.koreanair.service.BoardTaskService;

@RestController
@RequestMapping("/api/board")
@CrossOrigin
public class BoardTaskController {
	
	@Autowired
	private BoardTaskService boardTaskService;
	
	@PostMapping("/insert")
	public ResponseEntity<?> addPTToBoard(
			@Valid @RequestBody BoardTask boardTask, 
			BindingResult result, 
			HttpServletRequest request
	) {
		
		// IP 정보
		String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }
        
		if(result.hasErrors()) {
			Map<String, String> errorMap = new HashMap<String, String>();
			
			for(FieldError error : result.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			
			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		} 
		
		BoardTask newPT = boardTaskService.saveOrUpdateBoardTask(boardTask, ip);
		
		return new ResponseEntity<BoardTask>(newPT, HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public Iterable<BoardTask> getAllPTs() {
		return boardTaskService.findAll();
	}
}
