package com.backend.project.repository;

import com.backend.project.domain.University;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long>, UniversityRepositoryDSL
{
    Page<University> findAll(Pageable pageable);
}
