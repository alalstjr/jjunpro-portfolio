package com.jjunpro.project.repository;

import com.jjunpro.project.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long>, AlarmRepositoryDSL {
}
