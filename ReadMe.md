--------------------
# Spring JPA 연습 프로젝트 제작
--------------------


# 목차

- [1. JPA 초기 셋팅](#JPA-초기-셋팅)

# JPA, Security 초기 셋팅

> src/java/domain/Account.java

간단하게 유저 정보를 담을 수 있는 `릴레이션 Account Entity` 를 생성합니다.

> src/java/repository/AccountRepository.java

유저의 중요한 정보를 제외한 모든 값을 가져오기 위해서 `Projection` 을 활용하여 Password를 제외한 모든 값을 가져오도록 합니다.

AccountController 에서 API 정보가 정상적으로 가져오는지 확인하였으나 Security 기본 보안 설정때문에 접근할 수 가 없습니다. 기본 보안 설정을 해제하도록 하겠습니다.

> application.properties

방법은 두가지가 있지만 main Class를 복잡하게 하지않고 properties 한번에 설정을 관리할 수 있도록 하였습니다.

SecurityAutoConfiguration 사용을 해제하였습니다.

~~~
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration
~~~

그리고 Security config 커스텀 해주기 위해서 만들어 줍니다.

> src/java/config/WebSecurityConfig.java

~~~
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors().and();

        http
                .csrf()
                .disable();
    }
}
~~~

CSRF(Cross site request forgery)란 웹 사이트의 취약점을 이용하여 이용자가 의도하지 하지 않은 요청을 통한 공격을 의미합니다. http 통신의 Stateless 특성을 이용하여 쿠키 정보만 이용해서 사용자가 의도하지 않은 다양한 공격들을 시도할 수 있습니다. 해당 웹 사이트에 로그인한 상태로 https://xxxx.com/logout URL을 호출하게 유도하면 실제 사용자는 의도하지 않은 로그아웃을 요청하게 됩니다. 실제로 로그아웃뿐만 아니라 다른 웹 호출도 가능하게 되기 때문에 보안상 위험합니다.

api 확인을 위해서 외부의 접근을 풀어주었습니다. 
풀어주지 않으면 POST API 접근을 막아서 REST API 방식으로 유저의 생성을 할 수 가 없습니다.