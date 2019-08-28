package com.jjunpro.koreanair.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import com.jjunpro.koreanair.project.enums.UserRole;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "ACCOUNT")
public class AccountEntity extends BaseTimeEntity {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String uuid;
	
	@Column(name = "ACCOUNT_ID", nullable = false, unique = true)
	@NotBlank(message = "아이디는 비워둘 수 없습니다.")
	private String userId;
	
	@Column(name = "ACCOUNT_PASSWORD", nullable = false)
	@NotBlank(message = "비밀번호는 비워둘 수 없습니다.")
	private String password;
	
	@Column(name = "ACCOUNT_NAME", nullable = false)
	@NotBlank(message = "이름은 비워둘 수 없습니다.")
	private String username;
	
	@Column(name = "ACCOUNT_ROLE", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRole userRole;
	
	@Builder
	public AccountEntity(String userId, String password, String username, UserRole userRole) {
		super();
		this.userId = userId;
		this.password = password;
		this.username = username;
		this.userRole = userRole;
	}
}
