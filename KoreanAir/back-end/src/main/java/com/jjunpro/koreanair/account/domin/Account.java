package com.jjunpro.koreanair.account.domin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "JP_ACCOUNT")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bo_num")
	private Long id;
	
	@Column(name = "ACCOUNT_USERNAME")
	@NotBlank(message = "이름은 비워 둘 수 없습니다.")
	private String username;

	@Column(name = "ACCOUNT_LOGINID")
	@NotBlank(message = "아이디는 비워 둘 수 없습니다.")
	public String userId;
	
	@Column(name = "ACCOUNT_PASSWORD")
	@NotBlank(message = "비밀번호는 비워 둘 수 없습니다.")
	public String password;
	
	@Column(name = "ACCOUNT_ROLE")
	@Enumerated(value = EnumType.STRING)
	public UserRole userRole = UserRole.USER;
	
	@Column(name = "ACCOUNT_SOCIAL_ID")
	private Long socialId;
	
	@Column(name = "ACCOUNT_SOCIAL_PROFILEPIC")
	private String profileHref;
}
