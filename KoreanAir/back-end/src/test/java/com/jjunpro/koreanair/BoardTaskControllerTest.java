package com.jjunpro.koreanair;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
// 기본값은 webEnvironment = SpringBootTest.WebEnvironment.MOCK 이기때문에 생략가능

@AutoConfigureMockMvc
public class BoardTaskControllerTest {

	@Resource
	MockMvc mockMvc;
	
	@Resource
	DataSource dataSource;
	
	@Resource
	JdbcTemplate jdbcTemplate;
	
	@Test
	public void SampleControllerTest() {
		assertThat(mockMvc).isNotNull();
	}

}
