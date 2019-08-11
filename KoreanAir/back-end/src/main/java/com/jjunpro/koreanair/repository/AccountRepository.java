package com.jjunpro.koreanair.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.jjunpro.koreanair.domain.Account;

public interface AccountRepository extends CrudRepository<Account, Long>{
	
	Optional<Account> findByUserId(String userId);
	
}
