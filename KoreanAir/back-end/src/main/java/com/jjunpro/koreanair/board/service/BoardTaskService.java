package com.jjunpro.koreanair.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jjunpro.koreanair.board.dto.BoardTask;
import com.jjunpro.koreanair.board.repository.BoardTaskRepository;

@Service
public class BoardTaskService {
	@Autowired
	private BoardTaskRepository boardTaskRepository;
	
	public BoardTask saveOrUpdateBoardTask(BoardTask boardTask, String ip) {
	
		// 클라이언트 ip를 추가합니다.
		if(boardTask.getIp() == null || boardTask.getIp() == "") 
		{
			boardTask.setIp(ip);
		}
		
		return boardTaskRepository.save(boardTask);
	}
	
	public Iterable<BoardTask> findAll(Sort sort) {
		return boardTaskRepository.findAll(sort);
	}
	
	/*
	 * 게시판의 원하는 ID 값을 찾아 하나만 반환합니다. 
	 */
	public BoardTask findById(Long id) {
		return boardTaskRepository.getByNum(id);
	}
	
	/*
	 * 게시판의 해당 카테고리 목록만 반환합니다. 
	 */
	public Iterable<BoardTask> findByCate(String bo_category) {
		return boardTaskRepository.findByCategory(bo_category);
	}
	
	/*
	 * 게시판의 게시글을 삭제 합니다. 
	 */
	public void delete(Long id) {
		BoardTask boardTask = findById(id);
		boardTaskRepository.delete(boardTask);
	}
}
