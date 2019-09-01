package com.jjunpro.koreanair.project;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jjunpro.koreanair.project.security.jwts.HeaderTokenExtractor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeaderTokenExtractorTest {

	private HeaderTokenExtractor extractor = new HeaderTokenExtractor();
	private String header;
	
	@Before
	public void setUp() {
		this.header = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJqanVucHJvIiwiVVNFUk5BTUUiOiJqanVucHJvIiwiVVNFUl9ST0xFIjoiVVNFUiIsIkVYUCI6MTU2ODAzMDcxOH0.el6zNRMKsHA6veXqQ3mGwA2eOMQbx07OL6vuYxmgpmk";
	}
	
	@Test
	public void TEST() throws JsonProcessingException, IOException {
		assertThat(extractor.extract(this.header), is("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJqanVucHJvIiwiVVNFUk5BTUUiOiJqanVucHJvIiwiVVNFUl9ST0xFIjoiVVNFUiIsIkVYUCI6MTU2ODAzMDcxOH0.el6zNRMKsHA6veXqQ3mGwA2eOMQbx07OL6vuYxmgpmk"));
	}

}
