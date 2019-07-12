package com.jjunpro.koreanair.board.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

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
	@Type(type = "text")
	@NotBlank(message = "내용은 비워 둘 수 없습니다.")
	private String content;
	
	@Column(name = "bo_category", length = 255)
	@NotBlank(message = "카테고리를 비워 둘 수 없습니다.")
	private String category;
	
	@Column(name = "bo_readcount", length = 255)
	private int readcount;
	
	@CreationTimestamp
	@Column(name = "bo_reg_date", nullable = false, updatable = false)
	private LocalDateTime regDate;
	
	@Column(name = "bo_ip", length = 255)
	private String ip;
	
	@Column(name = "bo_files")
	private int files;
	
	@Column(name = "bo_thumb")
	private String thumb;
}
