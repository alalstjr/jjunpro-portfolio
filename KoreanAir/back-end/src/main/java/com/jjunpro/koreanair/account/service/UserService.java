package com.jjunpro.koreanair.account.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jjunpro.koreanair.account.domin.Account;

public interface UserService extends UserDetailsService {
	Collection<GrantedAuthority> getAuthorities(String username);
	public Optional<Account> findById(String userId);
	public Account saveOrUpdateMemberTask(Account user);
	public void deleteUser(String username);
	public PasswordEncoder passwordEncoder();
}
