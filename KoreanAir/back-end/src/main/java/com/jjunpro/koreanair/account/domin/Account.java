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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "JP_ACCOUNT")
public class Account extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "ACCOUNT_USERNAME", nullable = false, unique = true)
	@NotBlank(message = "이름은 비워 둘 수 없습니다.")
	private String username;

	@Column(name = "ACCOUNT_ID", nullable = false)
	@NotBlank(message = "아이디는 비워 둘 수 없습니다.")
	public String userId;
	
	@Column(name = "ACCOUNT_PASSWORD", nullable = false)
	@NotBlank(message = "비밀번호는 비워 둘 수 없습니다.")
	public String password;
	
	@Column(name = "ACCOUNT_ROLE", nullable = false)
	@Enumerated(value = EnumType.STRING)
	public UserRole userRole = UserRole.USER;
	
	@Builder
	public Account(String userId, String username, String password, UserRole userRole) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userRole = userRole;
	}
}
