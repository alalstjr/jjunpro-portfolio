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