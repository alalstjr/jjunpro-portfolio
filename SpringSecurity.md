# 스프링 시큐리티 노트

# Spring Security(스프링 시큐리티) 란 무엇인가?

스프링 시큐리티 레퍼런스에서는 자바 EE 기반의 엔터프라이즈 소프트웨어 애플리케이션을 위한 포괄적인 보안 서비스들을 제공하고 
<b>오픈 플랫폼</b>이면서 자신만의 인증 매커니즘을 간단하게 만들 수 있습니다.

스프링 시큐리티를 이해하기 위해서는 스프링 시큐리티가 <b>애플리케이션 보안을 구성하는 두가지 영역</b>에 대해서 알아야 합니다. 

바로 <b>인증(Authentication)과 권한(Authorization)</b> 이라는 것입니다.

- 인증 : 애플리케이션의 작업을 수행할 수 있는 주체(사용자)라고 주장할 수 있는 것
- 권한 : 권한은 인증된 주체가 애플리케이션의 동작을 수행할 수 있도록 허락되있는지를 결정하는 것

권한 승인이 필요한 부분으로 접근하려면 인증 과정을 통해 주체가 증명 되어야만 한다는 것입니다.

# Spring Security 동작 방식

- 1. 클라이언트가 Resource에 URL을 통해 요청을 보낸다.
- 2. <b>DelegatingFilterProxy</b>는 요청을 Intercept! 가로채서 Spring Security빈으로 보낸다.
- 3. Spring Security빈은 인증 및 권한을 확인한다.
- 4. 권한이 잘 부여되어 있다면 리소스에 접근을 허용하고 그렇지 않다면 거부한다.

img 2019-07-17-1.png

## DelegatingFilterProxy VS DispatcherServlet

프링을 사용해본 분이라면, DelegatingFilterProxy가 DispatcherServlet과 동작방식이 매우 비슷한 것을 알 수 있다. 
둘다  
<a href="https://ko.wikipedia.org/wiki/%ED%8D%BC%EC%82%AC%EB%93%9C_%ED%8C%A8%ED%84%B4">Facade</a>
로서, <b>사용자의 요청을 가장먼저 받아서 요청을 처리할 곳</b>으로 전가한다.
그렇다면 사용자의 요청을 누가 먼저처리할까? 누가 우선순위가 더 높을까?

우선 순위 <b>DelegatingFilterProxy >>> DispatcherServlet</b> <br/>
Filter 가 먼저 동작하고 DispatcherServlet 이 다음으로 동작한다. 
인증되지 않은 사용자는 Filter에서 먼저 걸려저셔 <b>Facade Controller에게 조차 전달되지 않는다.</b>

## DelegatingFilterProxy 등록하기 ==> Request Intercept & Filter

스프링의 다른 기능들처럼, 빈만 등록하면 절반은 끝난다. 
빈을 ApplicationContext에 등록하기만 하면 스프링이 자동으로 생성해주고 요청을 가로채서 DelegatingFilterProxy로 전달해준다. 
그 다음에는 사용하기만 하면 된다. 

## Java Configuration

스프링 시큐리티 레퍼런스에서는 자바 기반의 설정으로 설명하고 있습니다. 그 이유는 무엇일까요?

스프링 프레임워크 3.1에서부터 어노테이션을 통한 자바 설정을 지원하기 때문에 스프링 시큐리티 3.2부터는 XML로 설정하지 않고도 간단하게 설정할 수 있도록 지원하기 때문입니다.

원래 XML 기반의 설정에서는 web.xml에 org.springframework.web.filter.DelegatingFilterProxy라는 springSecurityFilterChain을 등록하는 것으로 시작합니다만, 자바 기반의 설정에서는 WebSecurityConfigurerAdapter를 상속받은 클래스에 @EnableWebSecurity 어노테이션을 명시하는 것만으로도 springSecurityFilterChain가 자동으로 포함되어집니다.

@EnableWebSecurity 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { 

}

# 클래스 작성 순서

## package 생성 

- dtos - (각 계층간의 데이터 교환을 위해 사용되는 개체 모음)
- filters - (req를 가로체서 사용자의 인증을 확인하는 클레스 모음)
- hendlers - (인증 후 결과를 처리해주는 핸들러 클래스 모음)
- jwt - (인증이 완료되면 JWT Token을 발행해주는 클래스 모음)
- providers (실제 인증을 하는 클래스  UserDetails 객체를 전달 받은 이후 실제 사용자의 입력정보와 UserDetails 객체를 가지고 인증을 시도한다.)
- tokens (인증 전 토큰확인과 인증 후 토큰 확인을 해주는 클래스 모음)
    - PreAuthorizationToken (인증 전 토큰 확인)
    - PostAuthorizationToken (인증 후 토큰 확인)

## 인증 진행 순서 (로그인)

- 1. SecurityConfig 
- 2. FormLoginFilter (attemptAuthentication 인증 시도)
- 3. FormLoginAuthenticationProvider
- 4. PreAuthorizationToken
- 5. AccountContext
- 6. PostAuthorizationToken
- 7. successfulAuthentication
- 8. FormLoginAuthenticationSuccessHandler
- 9. JwtFactory

순으로 진행되며 더 자세하게 작성하면은

### 1. SecurityConfig 

configure 메소드는 인증을 담당할 프로바이더 구현체를 설정, 필터 등록을 하는 메소드이다.
WebSecurityConfigurerAdapter 추상 클래스를 상속 받는다.

- 가장 먼저 인증이 필요한 서버에 사용자가 접속시 가장 처음 Filter를 연결해주는 역할

{% highlight matlab %}
    private FormLoginFilter formLoginFilter() throws Exception 
    {
        FormLoginFilter filter = new FormLoginFilter("/formlogin", formLoginAuthenticationSuccessHandler, fromLoginFailuerHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        
        return filter;
    }
{% endhighlight %}

FormLoginFilter 필터에 (defaultUrl, 인증이 필요한 Url) 과 인증시 성공 or 실패 핸들러 를 보내줍니다.
그리고 해당 필터에 SpringSecurity에서 사용되는 인증객체를 Bean으로 등록해줍니다.
(쭌피셜 super 로 상속받은 부모클래스의 메소드 용자 아이디와 패스워드를 진짜 인증을 담당할 authenticationManagerBean 인증객체를 
해당 필터로 전송하여 인증을 실시합니다. 인증 요청 토큰을 처리하는 데 필요합니다.)

authenticationManagerBean 메소드의 경우에는 SpringSecurity에서 사용되는 인증객체를 Bean으로 등록할 때 사용합니다. 
@Bean을 붙여서 Bean으로 등록해줍니다.

{% highlight matlab %}
    http.addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
{% endhighlight %}

FormLoginFilter 메서드를 실행시킬 수 있도록 유저아이디 와 비밀번호인증 필터를 formLoginFilter와 같이 addFilterBefore 메서드를 통해서 등록해 줍니다.

### 2. FormLoginFilter

Filter 클래스가 상속받은 AbstractAuthenticationProcessingFilter 란

브라우저 기반 HTTP 기반 인증 요청 에서 사용되는 컴포넌트로 POST 폼 데이터를 포함하는 요청을 처리한다. 
인증 실패와 인증 성공 관련 이벤트를 관련 핸들러 메서드를 가지고 있습니다.
사용자 비밀번호를 다른 필터로 전달하기 위해서 Authentication 객체를 생성하고 일부 프로퍼티를 설정한다.
(해당 추상클래스 설명 구글번역)

AbstractAuthenticationProcessingFilter 클래스의 doFilter 메서드로 인해서 
가장 처음 인증 attemptAuthentication 메서드를 실행합니다.

{ 만약 attemptAuthentication 메서드에서 인증이 성공한다면 doFilter 메서드 에서

{% highlight matlab %}
    // Authentication success
    if (continueChainBeforeSuccessfulAuthentication) {
        chain.doFilter(request, response);
    }

    successfulAuthentication(request, response, chain, authResult);
{% endhighlight %}

successfulAuthentication 으로 메서드를 실행시키도록 해줍니다. (인증 실패도 동일)}

사용자입력 ID and Password 를 req 로 받은 값을 ObjectMapper 객체로 JSON 으로 변환하여 FormLoginDto형식으로 저장합니다.
(결과 FormLoginDto(userid=asd, password=asd) 식으로 변환됩니다.)

사용자입력값이 존재하는지 비교하기 위해서 DTO 를 인증 '전' Token 객체에 넣어 PreAuthorizationToken 을 생성합니다.

위 사용자의 값을 가지고 attemptAuthentication는 인증을 시도합니다.
인증 시도는 FormLoginAuthenticationProvider 에서 하게됩니다.

PreAuthorizationToken 해당 객체에 맞는 Provider를 
getAuthenticationManager 해당 메서드가 자동으로 찾아서 연결해 준다.

### 3. FormLoginAuthenticationProvider 

--------------------------------------
개발자가 인증절차의 대부분을 직접 구현하려면 AuthenticationProvider 인터페이스를 구현하여 authenticate() 메소드를 오버라이드하면 된다

authenticate()메소드의 파라미터로 화면에서 입력한 로그인 정보가 전달되며, DB로부터 이용자 정보를 가져오려면 authenticate() 메소드 내에서 UserDetailsService 등을 이용하면 된다. DB에서 가져온 이용자의 비밀번호가 암호화되어 있는 경우에는 우선 PasswordEncoder를 이용하여 화면에서 입력된 비밀번호를 암호화하여 비교해야 한다

Spring Security 를 이용하여 로그인 기능을 구현하면 스프링은 AuthenticationProvider 클래스의 Authentication authenticate(Authentication authentication )메소드를 이용하여 데이터베이스에 저장된 데이터와 이용자가 입력한 로그인 정보를 비교하게 된다. authenticate()메소드의 파라미터로 전달된 Authentication 객체는 이용자가 화면에서 입력한 로그인 정보가 저장되어 있다. 

authenticate() 메소드 안에서는 UserDetailsService가 사용되어 데이터베이스로부터 이용자의 이름을 이용하여 이용자 정보를 UserDetails 형의 객체로 가져온다. 

authenticate()메소드를 개발자가 오버라이드하여 인증로직을 작성할 때는 이용자 입력정보(Authentication)와 DB의 이용자 정보(UserDetails)를 비교하여 일치하면 Authentication 참조를 리턴하고 일치하지 않으면 AuthenticationException 예외를 던져야 한다
https://micropilot.tistory.com/2884
--------------------------------------

FormLoginFilter attemptAuthentication 메서드에서 받은 유저의 ID and Password 매개변수를
인증전 토큰 PreAuthorizationToken token 에 담습니다.

PreToken 의 getUsername 과 getUserPassword 를 accountRepository JPA로 DB 검색을 하여 조회되는 유저가 있는지 확인합니다.
조회 방법은 다음과 같습니다.

우선 인증을 요청한 사용자의 ID가 DB에 존재하는지 조회하여 존재하면 Account 객체 변수에 담습니다.

다음 isCorrectPassword 메서드를 사용하여 요청한 사용자의 Password 와 조회된 DB의 Password 가 같은지 확인합니다.

DB에 사용자가 존재하는 경우 

account
Account(id=1, username=asd, userId=asd, password=$2a$10$MWnTHwQx3QFjzkSpteFrk.fNJSI1hLC4PHNCqfJOjISYTCEzqJF.G, userRole=USER, socialId=null, profileHref=null)

PostAuthorizationToken 객체에 account 의 필요한 정보만 넣어 
객체로 생성하기 위해서 account-context 객체를 생성하여 넣습니다.

account-context 
com.jjunpro.koreanair.account.security.AccountContext@17a72: 
Username: asd; Password: [PROTECTED]; Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_USER

account-context 는 User 를 상속받고 있습니다.

AccountContext.fromAccountModel(account) 식으로 객체를 생성하여 사용합니다.

PostAuthorizationToken.getTokenFromAccountContext(); 으로 인증이 완료된 Post Token 을 생성 합니다.

### 5. AccountContext

FormLoginAuthenticationProvider 에서 인증이 완료되어 DB에서 가져온 account 를 객체로 생성하는 역활을 합니다.

기본 생성자를 AccountContext fromAccountModel 으로만 통해서 값을 빼올 수 있도록 하며
account, 
account.getUserId(), 
account.getPassword(), 
parseAuthorities(account.getUserRole())

PostAuthorizationToken 을 발급받을때 비교할 수 있도록 
유저 아이디 비밀번호 권환을 확인할 수 있도록 합니다.

account.getUserRole() 값은 여러개 들어갈 수 있도록 배열을 List로 치환 선언하여 반환할 수 있도록 합니다.

### 6. PostAuthorizationToken 

FormLoginAuthenticationProvider 에서 DB에 사용자가 조회되어 PostAuthorizationToken 값을 생성하여 return 하기위해 PostAuthorizationToken 클래스로 이동합니다.

PostAuthorizationToken 또한 UsernamePasswordAuthenticationToken 추상클래스를 상속 받고 있습니다.

생성이 완료되면 다시 FormLoginAuthenticationProvider 로 나와서 

ProviderManager 클래스의 Authentication authenticate 메서드에서 위에서 나온 결과값을 provider 에 담아서 
result = provider.authenticate(authentication); 이런식으로 결과를 반환합니다.

(쭌피셜 디버깅한 결과)이러저러한 클래스를 거치다가 
DefaultAuthenticationEventPublisher 클래스에서 

public void publishAuthenticationSuccess(Authentication authentication) {
    if (applicationEventPublisher != null) {
        applicationEventPublisher.publishEvent(new AuthenticationSuccessEvent(
                authentication));
    }
}

Authentication 의 결과값이 성공인지 실패인지 분별 후에 실패 or 성공으로 보내는것 같습니다.

### 7. successfulAuthentication

Provider 에서 인증이 끝난 후 다시 filter로 돌아와서 위 127번째 줄 doFilter 의 메서드로 인해서 인증이 성공 하였으니 successfulAuthentication(request, response, chain, authResult); 메서드로 이동하여 실행합니다.

### 8. FormLoginAuthenticationSuccessHandler

로그인 인증에 성공 하였으니 해당 유저에게 JWT Token을 발급해주는 메서드를 실행해야 합니다.

전달받은 PostAuthorizationToken 의 getPrincipal 값만 따로 AccountContext 객체 변수로
뺴와서 factory.generateToken(context); 로 JWT Token 생성을 합니다.

### 9. JwtFactory

Jwt Token 을 생성 합니다.

다시 FormLoginAuthenticationSuccessHandler 로 돌아와서 

processRespone(res, writeDto(tokenString));

TokenDto(token) 를 생성하여 반환합니다.

(쭌프로 디버깅 뇌피셜)그후 다른 filter가 존재하는지 FilterChainProxy 로 검색합니다.

## 인증 진행 순서 (Token 확인)

- 1. SecurityConfig 
- 2. SecurityConfig - JwtAuthenticationFilter
- 3. FilterSkipMatcher (로그인 요청에 의한 검증은 하지 않기위해서 필요한 클래스)
- 4. SecurityConfig - JwtAuthenticationFilter
- 5. JwtAuthenticationFilter - attemptAuthentication
- 6. HeaderTokenExtractor (사용자에게서 받은 Token값 'bearer ' 제거 후 토큰 확인을 해주기 위해서 피요한 클래스)
- 7. JwtAuthenticationProvider
- 8. JwtDecoder
- 9. JwtAuthenticationFilter- successfulAuthentication

# docs
https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/authentication/AbstractAuthenticationProcessingFilter.html
https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/