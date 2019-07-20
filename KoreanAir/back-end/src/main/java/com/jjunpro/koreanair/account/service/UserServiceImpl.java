package com.jjunpro.koreanair.account.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjunpro.koreanair.account.domin.Account;
import com.jjunpro.koreanair.account.repository.AccountRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;  
	
	@Bean
	PasswordEncoder getEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		return null;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Account> findById(String userId) {
		return accountRepository.findByUserId(userId);
	}

	@Override
	public Account saveOrUpdateMemberTask(Account user) 
	{
		String rawPassword = user.getPassword();
		String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);
		user.setPassword(encodedPassword);
		return accountRepository.save(user);	
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PasswordEncoder passwordEncoder() {
		 return this.passwordEncoder;
	}

}
