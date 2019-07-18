package com.jjunpro.koreanair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jjunpro.koreanair.account.domin.Account;
import com.jjunpro.koreanair.account.security.AccountContext;
import com.jjunpro.koreanair.account.security.jwt.JwtFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
// 기본값은 webEnvironment = SpringBootTest.WebEnvironment.MOCK 이기때문에 생략가능

@AutoConfigureMockMvc
public class JwtFactoryTest {

	private static final Logger log = LoggerFactory.getLogger(JwtFactoryTest.class);
	
	private AccountContext context;
	
	@Autowired
	private JwtFactory factory;
	
	@Before
	public void setUp() {
		Account account = new Account();
		log.error("userid: {}, password: {}, role {}", account.getUserId(), account.getPassword(), account.getUserRole());
		this.context = AccountContext.fromAccountModel(account);
	}
	
	@Test
	public void TEST_JWT_GENERATE() {
		log.error(factory.generateToken(this.context));
	}
}
