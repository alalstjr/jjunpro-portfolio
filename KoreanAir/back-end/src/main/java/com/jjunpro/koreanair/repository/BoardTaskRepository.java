package com.jjunpro.koreanair.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jjunpro.koreanair.board.BoardTask;

@Repository
public interface BoardTaskRepository extends CrudRepository<BoardTask, Long>{
	BoardTask getByNum(Long num);
}
