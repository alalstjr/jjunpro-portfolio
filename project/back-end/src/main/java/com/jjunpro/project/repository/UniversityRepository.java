package com.jjunpro.project.repository;

import com.jjunpro.project.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long>, UniversityRepositoryDSL {

}
