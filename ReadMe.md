# 포트폴리오 리스트

## KoreanAir 

- 제작 일시 2019년 06월 15일 
- Spring-Boot 와 ReactJS 를 활용한 웹 

## npm 설치

slick slide 
> npm install react-slick --save-d

react dropzone file input
> npm install react-dropzone --save-d
https://upmostly.com/tutorials/react-dropzone-file-uploads-react/

react dropfile
> npm install filepond react-filepond --save-d
https://github.com/pqina/filepond-docs/blob/master/content/patterns/API/server.md
https://itnext.io/uploading-files-with-react-and-filepond-f8a798308557

## JPA 설명

https://jojoldu.tistory.com/251
http://arahansa.github.io/docs_spring/jpa.html

### JPA 간단하 설명

https://jobc.tistory.com/120
https://liveupdate.tistory.com/248 // 메서드 설명
https://has3ong.tistory.com/284
https://weejw.tistory.com/83

jpa 연관 매핑
https://medium.com/@SlackBeck/%EC%A4%91%EC%B2%A9%EB%90%9C-fk-foreign-key-%EB%A5%BC-jpa%EB%A1%9C-%EC%97%B0%EA%B4%80-%EA%B4%80%EA%B3%84-%EB%A7%A4%ED%95%91-%ED%95%98%EA%B8%B0-216ba5f2b8ed

### JPA LIMIT 메서드

https://www.logicbig.com/tutorials/spring-framework/spring-data/limiting-query-results.html
https://hakurei.tistory.com/108

### @Column

칼럼의 이름을 이용하여 지정된 필드나 속성을 데이블의 칼럼에 매핑한다.
생략되면 속성과 같은 이름의 칼럼으로 매핑된다.

> @Column(name = "bo_num", length = 10)

private Long num; 

일경우 bo_num 컬럼으로 매핑

https://www.slideshare.net/zipkyh/ksug2015-jpa2-jpa

JPA 필드 와 컬럼 매핑 레퍼런스 – 머루의개발블로그
http://wonwoo.ml/index.php/post/717

Spring Data JPA를 활용한 DAO를 바꿔보자.
http://www.chidoo.me/index.php/2016/05/08/spring-data-jpa-for-short-memories/

loombok 설치
https://countryxide.tistory.com/16

date 시간 날짜 관련 어노테이션
https://medium.com/@SlackBeck/java-8-date-time-%EC%99%80-jpa-%EA%B7%B8%EB%A6%AC%EA%B3%A0-%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-7a02eea23d29

## 정렬관련 
https://cnpnote.tistory.com/entry/SPRING-Spring-%EB%8D%B0%EC%9D%B4%ED%84%B0%EC%97%90%EC%84%9C-OrderBy%EB%A5%BC-findAll%EA%B3%BC-%ED%95%A8%EA%BB%98-%EC%82%AC%EC%9A%A9%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95

https://jistol.github.io/java/2017/02/11/jpa-sort/

# FILE UPLOAD 관련 정리

## PATH, PATHS 클래스 정리

JAVA : https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html
https://java.ihoney.pe.kr/342

## File 클래스 정리

JAVA : https://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html
reactjs-kr.firebaseapp.com/docs/typechecking-with-proptypes.html

## JAP FILE UPLOAD 
https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/

# FilePond 

## 서버로 부터 받아온 response 값 전달받는 방법
server = {
    {
        url: 'http://localhost:8080/uploadMultipleFiles',
        process: { 
            onload: (response) => console.log(response)
        }
    }
}
server.process.onload 활용한다.
https://github.com/pqina/vue-filepond/issues/51

## 참고만
http://www.libqa.com/wiki/730

## spring boot 코드
https://github.com/walbatrossw/boot-qna

## react 줄바꿈 배열처리
https://velopert.com/1896

## react HOC
https://www.vobour.com/%EB%A6%AC%EC%95%A1%ED%8A%B8-react-%EC%9D%B4%ED%95%B4-4-higher-order-component
https://reactjs-kr.firebaseapp.com/docs/higher-order-components.html
https://merong.city/p/react-hoc

## react event
https://reactjs.org/docs/events.html

## react file dropzone file 삭제
https://github.com/react-dropzone/react-dropzone/issues/805

# Spring Security
https://postitforhooney.tistory.com/entry/SpringSecurity-%EC%B4%88%EB%B3%B4%EC%9E%90%EA%B0%80-%EC%9D%B4%ED%95%B4%ED%95%98%EB%8A%94-Spring-Security-%ED%8D%BC%EC%98%B4

PreAuthenticatedAuthenticationToken 인증 시작단계에서 만들어 주고 만들어진 Token을 provider에 전송하는 방식의 인증

PreAuthorizationToken -> FormLoginAuthenticationProvider 순으로 진행

## 진행 방향

Provider 의 authenticate 메서드를 호출해서 인증을 시도합니다. 그러면 어떠한 결과 값이 제공됩니다.
성공하면 인증 객체가 실패하면 NoSuchElementException 을 반환하도록 설계 되어있습니다.


## JPA JWT DOMAIN
https://stackoverflow.com/questions/1281952/jpa-fastest-way-to-ignore-a-field-during-persistence

## test 설명
https://javaengine.tistory.com/331

## static 함수 설명
https://ifcontinue.tistory.com/2

## 스프링 시큐리티 설명
https://coding-start.tistory.com/153
http://springmvc.egloos.com/504862
https://ryudung.tistory.com/20

## 스프링을 통한 의존성 주입 - @Autowired 를 통한 속성 주입
https://expert0226.tistory.com/195

## java 캐스팅
https://mommoo.tistory.com/40

## ObjectMapper 란
https://soulduse.tistory.com/22

## 생성자가 private으로 선언된 경우/의미
https://story.stevenmin.info/107

https://medium.com/@okihouse16/%EB%B3%84%EB%B3%84-%EC%82%BD%EC%A7%88%ED%95%98%EB%8B%A4%EA%B0%80-%EC%97%AC%EA%B8%B0%EA%B9%8C%EC%A7%80-%EC%99%94%EB%84%A4%EC%9A%94-%E3%85%8E%E3%85%8E-%ED%8F%AC%EC%8A%A4%ED%8C%85-%EB%B3%B4%EA%B3%A0-%EC%89%BD%EA%B2%8C-%ED%95%B4%EA%B2%B0%ED%95%A0-%EA%B1%B8-%EA%B1%B0%EC%9D%98-2%EC%9D%BC%EA%B0%84-%EB%B6%99%EB%93%A4%EA%B3%A0-%EC%9E%88%EC%97%88%EC%8A%B5%EB%8B%88%EB%8B%A4-94ff6977a76b

## 발급받은 JWT Token 확인

filter -  JwtAuthenticationFilter 클레스 생성

## Spring bean 이란
https://endorphin0710.tistory.com/93

## 로그인 성공후 부가 작업
https://to-dy.tistory.com/94 [todyDev]

## spring json 변환
https://engaspect.tistory.com/27

## Spring Security Custom AuthenticationProvider
출처: https://micropilot.tistory.com/2884 []

## JAVA 제네릭(Generic) 문법 정리
https://preamtree.tistory.com/138

## JAVA 스트림 stream
http://iloveulhj.github.io/posts/java/java-stream-api.html

## 스트림 주의사항 stream
https://okky.kr/article/329818