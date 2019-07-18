package com.jjunpro.koreanair.account.domin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

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
	private String username = "asd";

	@Column(name = "ACCOUNT_LOGINID")
	public String userId = "asd";
	
	@Column(name = "ACCOUNT_PASSWORD")
	public String password = "asd";
	
	@Column(name = "ACCOUNT_ROLE")
	@Enumerated(value = EnumType.STRING)
	public UserRole userRole = UserRole.USER;
	
	@Column(name = "ACCOUNT_SOCIAL_ID")
	private Long socialId;
	
	@Column(name = "ACCOUNT_SOCIAL_PROFILEPIC")
	private String profileHref;
}
