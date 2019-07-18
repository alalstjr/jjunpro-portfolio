package com.jjunpro.koreanair;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jjunpro.koreanair.account.domin.Account;
import com.jjunpro.koreanair.account.repository.AccountRepository;
import com.jjunpro.koreanair.property.FileStorageProperties;


@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

	@Bean
	CommandLineRunner bootstrapTestAccount(
			AccountRepository accountRepository,
			PasswordEncoder passwordEncoder
			) 
	{
		return args -> {
			Account account = new Account();
			
			account.setPassword(passwordEncoder.encode("asd"));
			
			accountRepository.save(account);
		};
	}
	
}
