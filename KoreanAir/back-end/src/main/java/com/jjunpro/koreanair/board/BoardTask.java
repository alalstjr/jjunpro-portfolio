package com.jjunpro.koreanair.board;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class BoardTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	
	@NotBlank(message = "작성자는 비워 둘 수 없습니다.")
	private String writer;
	
	@NotBlank(message = "제목은 비워 둘 수 없습니다.")
	private String subject;
	
	@NotBlank(message = "비밀번호는 비워 둘 수 없습니다.")
	private String password;
	
	@NotBlank(message = "내용은 비워 둘 수 없습니다.")
	private String content;
	
	private int readcount;
	private String reg_date;
	private String ip;
	
	public BoardTask() {
		super();
	}
	
	public final Long getNum() {
		return num;
	}
	public final void setNum(Long num) {
		this.num = num;
	}
	public final String getWriter() {
		return writer;
	}
	public final void setWriter(String writer) {
		this.writer = writer;
	}
	public final String getSubject() {
		return subject;
	}
	public final void setSubject(String subject) {
		this.subject = subject;
	}
	public final String getPassword() {
		return password;
	}
	public final void setPassword(String password) {
		this.password = password;
	}
	public final String getReg_date() {
		return reg_date;
	}
	public final void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public final int getReadcount() {
		return readcount;
	}
	public final void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public final String getContent() {
		return content;
	}
	public final void setContent(String content) {
		this.content = content;
	}
	public final String getIp() {
		return ip;
	}
	public final void setIp(String ip) {
		this.ip = ip;
	}
}
