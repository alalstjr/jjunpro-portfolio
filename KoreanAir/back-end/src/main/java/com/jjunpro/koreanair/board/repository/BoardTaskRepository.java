package com.jjunpro.koreanair.board.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jjunpro.koreanair.board.dto.BoardTask;

@Repository
public interface BoardTaskRepository extends CrudRepository<BoardTask, Long>{
	BoardTask getByNum(Long num);

	Iterable<BoardTask> findAllByOrderByRegDateDesc();

	Iterable<BoardTask> findAll(Sort sort);
	
	Iterable<BoardTask> findByCategory(String bo_category);
	
}
