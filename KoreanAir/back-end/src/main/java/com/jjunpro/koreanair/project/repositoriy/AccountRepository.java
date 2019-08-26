package com.jjunpro.koreanair.project.repositoriy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jjunpro.koreanair.project.domain.AccountEntity;
import com.jjunpro.koreanair.project.dto.AccountSaveDTO;

public interface AccountRepository extends JpaRepository<AccountEntity, Long>{
	Optional<AccountEntity> findByUserId(String dto);
}
