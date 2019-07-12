package com.jjunpro.koreanair.account.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.jjunpro.koreanair.account.domin.Account;

public interface AccountRepository extends CrudRepository<Account, Long>{
	
	Optional<Account> findByUserId(String userId);
	
	Optional<Account> findBySocialId(Long socialId);
	
}
