package com.jjunpro.koreanair.account.domin

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "JP_ACCOUNT")
internal data class Account(
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private val id: Long? = null,
		
		@Column(name = "ACCOUNT_USERNAME")
		private val username: String? = null,

		@Column(name = "ACCOUNT_LOGINID")
		public val userId: String? = null,
		
		@Column(name = "ACCOUNT_PASSWORD")
		public val password: String? = null,
		
		@Column(name = "ACCOUNT_ROLE")
		@Enumerated(value = EnumType.STRING)
		public val userRole: UserRole? = null,
		
		@Column(name = "ACCOUNT_SOCIAL_ID")
		private val socialId: Long? = null,
		
		@Column(name = "ACCOUNT_SOCIAL_PROFILEPIC")
		private val profileHref: String? = null
) {
		
}

/*
 *	internal : Kotlin에서 한모듈로 관리해주는  코드에서만 해당 Class 에 접근 할 수 있다.
 *	data class : data를 붙임으로써 lombock 에서의 @Data 처럼 getter, setter, toString 을 자동으로 구현할 수 있게 해줍니다.
 *	data class는 괄호 안에 생성자와 필드 선언을 동시에 해줍니다.
 *
 *	username: String? = null 에서 {?} 는 Kotlin언어로써 해당 필드에 null 값이 들어올 수 있다고 명시적으로 선언해 주는것
 *	
 */