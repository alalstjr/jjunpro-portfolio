package com.jjunpro.koreanair.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjunpro.koreanair.board.BoardTask;
import com.jjunpro.koreanair.repository.BoardTaskRepository;

@Service
public class BoardTaskService {
	@Autowired
	private BoardTaskRepository boardTaskRepository;
	
	public BoardTask saveOrUpdateBoardTask(BoardTask boardTask, String ip) {
	
//		if(boardTask.getReg_date() == null || boardTask.getReg_date() == "") 
//		{	// 데이터 생성시 현재시간
//			SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
//			Calendar time = Calendar.getInstance();
//			String format_time = format.format(time.getTime());
//			
//			format = null;
//			
//			boardTask.setReg_date(format_time);
//		}
		
		if(boardTask.getIp() == null || boardTask.getIp() == "") 
		{	// 클라이언트 ip
			boardTask.setIp(ip);
		}
		
		return boardTaskRepository.save(boardTask);
	}
	
	public Iterable<BoardTask> findAll() {
		return boardTaskRepository.findAll();
	}
}
