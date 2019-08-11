package com.jjunpro.koreanair.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jjunpro.koreanair.domain.BoardTask;

public interface BoardTaskService {

	public BoardTask saveOrUpdateBoardTask(BoardTask boardTask, String ip);
	
	public Iterable<BoardTask> findAll(Sort sort);
	
	public BoardTask findById(Long id);
	
	public Iterable<BoardTask> findByCate(String bo_category);
	
	public void delete(Long id);
	
	public Page<BoardTask> findAll(Pageable pageable);
	
	public Page<BoardTask> findByCate(String bo_category, Pageable pageable);
}
