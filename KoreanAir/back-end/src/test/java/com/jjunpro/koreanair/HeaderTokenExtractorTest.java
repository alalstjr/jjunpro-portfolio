package com.jjunpro.koreanair;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jjunpro.koreanair.account.security.jwt.HeaderTokenExtractor;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HeaderTokenExtractorTest {
	
	private HeaderTokenExtractor extractor = new HeaderTokenExtractor();
	private String header;
	
	@Before
	public void setUp() 
	{
		this.header = "Bearer asdasdasdasd.asdasdasdasd";
	}
	
	@Test
	public void TEST_JWT_EXTRACT() 
	{
		assertThat(extractor.extract(this.header), is("asdasdasdasd.asdasdasdasd"));
	}
}
