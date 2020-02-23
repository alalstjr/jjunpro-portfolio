package com.jjunpro.project.repository;

import com.jjunpro.project.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryDSL {

}