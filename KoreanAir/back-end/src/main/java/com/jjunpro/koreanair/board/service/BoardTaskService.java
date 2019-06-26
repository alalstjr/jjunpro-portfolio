package com.jjunpro.koreanair.board.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jjunpro.koreanair.board.dto.BoardTask;
import com.jjunpro.koreanair.board.repository.BoardTaskRepository;

@Service
public class BoardTaskService {
	@Autowired
	private BoardTaskRepository boardTaskRepository;
	
	public BoardTask saveOrUpdateBoardTask(BoardTask boardTask, String ip) {
	
		if(boardTask.getIp() == null || boardTask.getIp() == "") 
		{	// 클라이언트 ip
			boardTask.setIp(ip);
		}
		
		return boardTaskRepository.save(boardTask);
	}
	
	public Iterable<BoardTask> findAll() {
		return boardTaskRepository.findAll();
	}
	
	/*
	 * 게시판의 원하는 ID 값을 찾아 하나만 반환합니다. 
	 */
	public BoardTask findById(Long id) {
		return boardTaskRepository.getByNum(id);
	}
}
