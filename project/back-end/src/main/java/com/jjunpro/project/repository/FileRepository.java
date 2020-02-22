package com.jjunpro.project.repository;

import com.jjunpro.project.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}