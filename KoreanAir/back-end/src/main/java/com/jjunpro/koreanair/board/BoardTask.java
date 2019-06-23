package com.jjunpro.koreanair.board;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "jp_board")
public class BoardTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bo_num")
	private Long num;
	
	@Column(name = "bo_writer", length = 255)
	@NotBlank(message = "작성자는 비워 둘 수 없습니다.")
	private String writer;
	
	@Column(name = "bo_subject", length = 255)
	@NotBlank(message = "제목은 비워 둘 수 없습니다.")
	private String subject;
	
	@Column(name = "bo_password", length = 255)
	@NotBlank(message = "비밀번호는 비워 둘 수 없습니다.")
	private String password;
	
	@Column(name = "bo_content")
	@NotBlank(message = "내용은 비워 둘 수 없습니다.")
	private String content;
	
	@Column(name = "bo_category", length = 255)
	@NotBlank(message = "카테고리를 비워 둘 수 없습니다.")
	private String category;
	
	@Column(name = "bo_readcount", length = 255)
	private int readcount;
	
	@CreationTimestamp
	@Column(name = "bo_reg_date")
	private LocalDateTime reg_date;
	
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "bo_reg_date")
//	private Date reg_date;
	
	@Column(name = "bo_ip", length = 255)
	private String ip;
	
	@Column(name = "bo_file", length = 255)
	private String file;
}
