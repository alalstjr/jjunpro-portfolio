package com.jjunpro.koreanair.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jjunpro.koreanair.board.dto.BoardTask;

// REST API 입니다.
@Repository
public interface BoardTaskRepository extends CrudRepository<BoardTask, Long>{
	BoardTask getByNum(Long num);

	Iterable<BoardTask> findAllByOrderByRegDateDesc();

	Iterable<BoardTask> findAll(Sort sort);
	
	Iterable<BoardTask> findByCategory(String bo_category);
	
	/*
	 * BoardTask 페이징 
	 */
	Page<BoardTask> findAll(Pageable pageable);
}

/*
 * CrudRepository<BoardTask, Long> 에서 
 * BoardTask 는 {entity} 
 * Long 은 {id} 를 의미합니다.
 */