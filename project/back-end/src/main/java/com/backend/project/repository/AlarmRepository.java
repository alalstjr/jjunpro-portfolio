package com.backend.project.repository;

import com.backend.project.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long>, AlarmRepositoryDSL
{
    Optional<Alarm> findById(Long id);
    Optional<Alarm> findByUserId(Long userId);
}
