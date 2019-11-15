--------------------
# Spring JPA 연습 프로젝트 제작
--------------------


# 목차

- [1. JPA 초기 셋팅](#JPA-초기-셋팅)
- [2. React Admin](#React-Admin)

- [1. JPA Repository Type ERROR](#JPA-Repository-Type-ERROR)
- [2. 양방향 관계 예제](#양방향-관계-예제)
- [3. 빌더 패턴을 사용하는 이유를](#빌더-패턴을-사용하는-이유를)
- [4. 하이버네이트 ID 값 생성](#하이버네이트-ID-값-생성)

- [1. request로 받은 Authentication 인증정보값 사용하기](#request로-받은-인증정보값-사용하기)
- [2. request로 IP 받기](#request로-IP-받기)
- [3. 현재 로그인한 사용자 정보받기](#현재-로그인한-사용자-정보받기)

- [1. Java File Upload 이미지 crop 방법](#File-Upload-이미지-crop-방법)
- [2. Java Stream List Map Sorted Comparable](#Stream-List-Map-정렬-방법)

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

# React Admin

https://marmelab.com/react-admin/Tutorial.html - [React-Admin-관련-문서-링크]

## REST API 연동하기

https://marmelab.com/react-admin/Tutorial.html#connecting-to-a-real-api - [React-Admin-API-연동]

튜토리얼에서 제공해주는 예제 연동 코드를 활용하여 Spring Boot 서버와 연동하도록 하겠습니다.

> src/admin/provider/AccountProvider.jsx

~~~
// in src/dataProvider
import {
    GET_LIST,
    GET_ONE,
    GET_MANY,
    GET_MANY_REFERENCE,
    CREATE,
    UPDATE,
    DELETE,
    fetchUtils,
} from 'react-admin';
import { stringify } from 'query-string';

const API_URL = 'http://localhost:8080/api';

/**
 * @param {String} type One of the constants appearing at the top of this file, e.g. 'UPDATE'
 * @param {String} resource Name of the resource to fetch, e.g. 'posts'
 * @param {Object} params The Data Provider request params, depending on the type
 * @returns {Object} { url, options } The HTTP request parameters
 */
const convertDataProviderRequestToHTTP = (type, resource, params) => {
    switch (type) {
    case GET_LIST: {
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;

        // 1. 각각 넘어오는 파라미터를 활용하여 REST API 전송
        console.log(params);
        console.log(type);
        console.log(resource);

        // 2. query 는 직접 작성하여 URL 을 전송할 수 있는 FREE 한 변수 자신이 사용하는 REST API URL 에 맞도록 설정한다.
        const query = {
            size: perPage,
            sort: field+','+order,
            page: (page - 1)
            // range: JSON.stringify([(page - 1) * perPage, page * perPage - 1]),
            // filter: JSON.stringify(params.filter),
        };

        // 3. 마지막 완성된 REST API URL 정보를 확인합니다.
        console.log(stringify(query));

        // 4. 해당 값은 하단 export {url, options} 변수로 반환됩니다.
        return { url: `${API_URL}/${resource}?${stringify(query)}` };
    }
    case GET_ONE:
        return { url: `${API_URL}/${resource}/${params.id}` };
    case GET_MANY: {
        const query = {
            filter: JSON.stringify({ id: params.ids }),
        };
        return { url: `${API_URL}/${resource}?${stringify(query)}` };
    }
    case GET_MANY_REFERENCE: {
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;
        const query = {
            sort: JSON.stringify([field, order]),
            range: JSON.stringify([(page - 1) * perPage, (page * perPage) - 1]),
            filter: JSON.stringify({ ...params.filter, [params.target]: params.id }),
        };
        return { url: `${API_URL}/${resource}?${stringify(query)}` };
    }
    case UPDATE:
        return {
            url: `${API_URL}/${resource}/${params.id}`,
            options: { method: 'PUT', body: JSON.stringify(params.data) },
        };
    case CREATE:
        return {
            url: `${API_URL}/${resource}`,
            options: { method: 'POST', body: JSON.stringify(params.data) },
        };
    case DELETE:
        return {
            url: `${API_URL}/${resource}/${params.id}`,
            options: { method: 'DELETE' },
        };
    default:
        throw new Error(`Unsupported fetch action type ${type}`);
    }
};

/**
 * @param {Object} response HTTP response from fetch()
 * @param {String} type One of the constants appearing at the top of this file, e.g. 'UPDATE'
 * @param {String} resource Name of the resource to fetch, e.g. 'posts'
 * @param {Object} params The Data Provider request params, depending on the type
 * @returns {Object} Data Provider response
 */
const convertHTTPResponseToDataProvider = (response, type, resource, params) => {

    // 6. convertDataProviderRequestToHTTP 받은 값을 조합하여 사용자에게 최종 전달합니다.
    // response 무엇이 전달되는지 확인하면서 출력하면 됩니다.

    console.log(response);

    const { headers, json } = response;
    switch (type) {
    case GET_LIST:
        return {
            data: json.content.map(x => x),
            total: json.totalElements
        };
    case CREATE:
        return { data: { ...params.data, id: json.id } };
    default:
        return { data: json };
    }
};

/**
 * @param {string} type Request type, e.g GET_LIST
 * @param {string} resource Resource name, e.g. "posts"
 * @param {Object} payload Request parameters. Depends on the request type
 * @returns {Promise} the Promise for response
 */
export default (type, resource, params) => {
    const { fetchJson } = fetchUtils;
    const { url, options } = convertDataProviderRequestToHTTP(type, resource, params);

    // 5. REST API URL 로 전송된 response 값을 convertHTTPResponseToDataProvider 에서 받습니다.

    return fetchJson(url, options)
        .then(response => convertHTTPResponseToDataProvider(response, type, resource, params));
};
~~~

1. 각각 넘어오는 파라미터를 활용하여 REST API 전송 아래는 console 찍어본 넘어오는 값

~~~
{pagination: {…}, sort: {…}, filter: {…}}filter: {}pagination: {page: 1, perPage: 10}sort: {field: "id", order: "DESC"}__proto__: Object
AccountProvider.jsx:29 GET_LIST
AccountProvider.jsx:30 account
~~~

2. query 는 직접 작성하여 URL 을 전송할 수 있는 FREE 한 변수 자신이 사용하는 REST API URL 에 맞도록 설정한다.
3. 마지막 완성된 REST API URL 정보를 확인합니다.
4. 해당 값은 하단 export {url, options} 변수로 반환됩니다.
5. REST API URL 로 전송된 response 값을 convertHTTPResponseToDataProvider 에서 받습니다.
6. convertDataProviderRequestToHTTP 받은 값을 조합하여 사용자에게 최종 전달합니다.

> src/admin/AdminComponent.jsx

~~~
import React from 'react';

import { Admin, Resource } from 'react-admin';
import { UserList } from './user';
import { PostList, PostEdit, PostCreate } from './post';
import Dashboard from './dashboard';
import authProvider from './authProvider';
import dataProvider from './provider/AccountProvider';

export const AdminComponent = () => (
    <Admin dashboard={Dashboard} authProvider={authProvider} dataProvider={dataProvider}>
        <Resource name="posts" list={PostList} edit={PostEdit} create={PostCreate} />
        <Resource name="account" list={UserList} />
    </Admin>
);
~~~

dataProvider 추가합니다.

# JPA Repository Type ERROR

~~~
UniversitySaveDTO dto;

Optional<Account> account = accountRepository.findById(1L);
dto.setAccount(account);
~~~

이렇게 dto Optional<Account> 타입을 넣으려고 하면 아래와 같은 오류가 발생합니다.

~~~
inferred type 'S' for type parameter 'S' is not within its bound
~~~

그 이유는 UniversitySaveDTO dto 에서 받는 Account 타입은 Optional 포함하고 있지 않기 때문입니다.
해결 방법은 get() 메소드로 Optional 에서 빼와서 Account 타입으로 넣은 후 dto에 담는 것입니다.

~~~
UniversitySaveDTO dto;

Account account = accountRepository.findById(1L).get();
dto.setAccount(account);
~~~

https://4programmers.net/Forum/Java/312113-type_parameter_s_is_not_within_its_bound_i_jpa_repository - [type-parameter-S-is-not-within-its-bound-i-JPA-Repository]

# 양방향 관계 예제

> Account.java

~~~
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @Column(nullable = true)
    private Set<University> university = new HashSet<>();

    @Builder
    public Account(String userId) {
        this.userId = userId;
    }

    public void addUniversity(UniversitySaveDTO university) {
        this.getUniversity().add(university.toEntity());
        university.setAccount(this);
    }
}
~~~

addUniversity 메소드를 활용해서 양쪽의 데이터를 연결하도록 insert 설정을 해줍니다.

> University.java

~~~
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class University {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String uniSubject;

    @ManyToOne
    private Account account;

    @Builder
    public University(String uniSubject, Account account) {
        this.uniSubject = uniSubject;
        this.account = account;
    }
}

~~~

> UniversityController.java

~~~
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("")
    public ResponseEntity<University> saveOrUpdate(
            @Valid
            @RequestBody UniversitySaveDTO dto
    ) {

        Account account = accountRepository.findById(1L).get();

        account.addUniversity(dto);

        University university = dto.toEntity();

        accountRepository.save(account);
        universityRepository.save(university);

        return null;
    }
}
~~~

# 빌더 패턴을 사용하는 이유를

https://okky.kr/article/396206 - [빌더-패턴을-사용하는-이유를]

# request로 받은 인증정보값 사용하기

~~~
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<University> saveOrUpdate(
            @RequestBody UniversitySaveDTO dto,
            Authentication authentication
    ) {
        ...
    }
~~~

로그인한 유저 인증된 유저만 접근할 수 있는 공간입니다.
해당 공간에 접근한 유저의 정보를 활용하여 무언가를 하려고 합니다.
넘겨받은 Authentication 활용해서 말이죠.

하지만 단순하게 authentication 가지고 getter 해서 값을 가져오면 Oject로만 값을 반환하여 
쉽게 값을 사용할 수 없습니다. 이를 사용하기 위해서 필요한 인터페이스 `UserDetails` 입니다.

~~~
...
UserDetails userDetails = (UserDetails) authentication.getPrincipal();
userDetails.getUsername();
~~~

UserDetails 값에 캐스팅 담아서 사용하면 유저의 를 담아서 사용할 수 있습니다.

# request로 IP 받기

https://stackoverflow.com/questions/22877350/how-to-extract-ip-address-in-spring-mvc-controller-get-call - [사용자에게-IP받기]

# 현재 로그인한 사용자 정보받기

https://itstory.tk/entry/Spring-Security-%ED%98%84%EC%9E%AC-%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%95%9C-%EC%82%AC%EC%9A%A9%EC%9E%90-%EC%A0%95%EB%B3%B4-%EA%B0%80%EC%A0%B8%EC%98%A4%EA%B8%B0 - [SpringSecurity-현재-로그인한-사용자-정보-가져오기]

# 하이버네이트 ID 값 생성

https://medium.com/@SlackBeck/%ED%95%98%EC%9D%B4%EB%B2%84%EB%84%A4%EC%9D%B4%ED%8A%B8%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%9E%90%EB%8F%99-%ED%82%A4-%EC%83%9D%EC%84%B1-%EC%A0%84%EB%9E%B5%EC%9D%84-%EA%B2%B0%EC%A0%95%ED%95%98%EB%8A%94%EA%B0%80-75dfa89939d1 - [하이버네이트-ID값-생성-전략]

# File Upload 이미지 crop 방법

파일 crop 참고 링크
https://blog.netgloo.com/2015/03/03/spring-boot-crop-uploaded-image/

How to get an InputStream from a BufferedImage?
변환된 이미지를 파일로 바꾸는 방법
https://stackoverflow.com/questions/649186/how-to-get-an-inputstream-from-a-bufferedimage

이미지 파일 비율로 자르는 방법 - 이미지 비율이 아닌 전체적인 틀이 잘림
https://stackoverflow.com/questions/50562388/how-to-crop-image-in-java

고정 너비 및 높이로 Java 이미지 크기 조정 예제
https://memorynotfound.com/java-resize-image-fixed-width-height-example/

파일 높이 넓이 조정
https://stackoverflow.com/questions/10245220/java-image-resize-maintain-aspect-ratio

# Stream List Map 정렬 방법

간단한 코드 예제로 확인해 보면

~~~
Map<Store, List<University>> transform = queryFactory
        .from(qStore)
        .leftJoin(qStore.stoUniList, qUniversity)
        .where(
                qUniversity.publicStatus.eq(true)
                        .and(qUniversity.controlStatus.eq(false))
                        .and(qStore.stoId.eq(storeId))
        )
        .transform(groupBy(qStore).as(list(qUniversity)));
~~~

Store 객체의 University 객체를 Map에 담아서 반환합니다.
지금까지는 데이터가 무작위로 Map으로 반환됩니다.

하지만 결과는 qUniversity.uniLike 의 가장 많은 숫자 순서대로 결과를 나열하는 정렬 조건입니다.

~~~
List<StorePublic> results = transform.entrySet().stream()
        .map(
                s -> new StorePublic(
                        s.getKey().getId(),
                        s.getKey().getStoUniList().stream().map(
                            u -> new UniversityPublic(
                                    u.getId(),
                                    u.getUniSubject(),
                                    u.getUniContent(),
                                    u.getUniName(),
                                    u.getUniTag(),
                                    u.getUniStar(),
                                    u.getUniIp(),
                                    u.getModifiedDate(),
                                    u.getAccount().getId(),
                                    u.getAccount().getNickname(),
                                    u.getUniLike().size(),
                                    u.getUniLike().contains(account),
                                    u.getFiles()
                            )
                        ).collect(Collectors.toList())
                )
        )
        .collect(Collectors.toList());
~~~

위 코드는 전달받은 List를 StorePublic, UniversityPublic 객체로 담아서 
Map 형태를 Collectors.toList List 형태로 바꿔주는 stream 기능 메소드입니다.
지금의 코드는 무작위로 List에 담아서 반환합니다. 여기서 필요한것은 sorted() 를 활용하는 것입니다.

~~~
                        ... 생략
                            u.getUniLike().contains(account),
                    )
                ).sorted().collect(Collectors.toList())
        )
)
.collect(Collectors.toList());
~~~

sorted() 메소드만 추가한 상태로 실행한다면 아래와 같은 오류가 발생합니다.

~~~
class com.backend.project.projection.UniversityPublic cannot be cast to class java.lang.Comparable (com.backend.project.projection.UniversityPublic is in unnamed module of loader 'app'; java.lang.Comparable is in module java.base of loader 'bootstrap')
~~~

UniversityPublic 클래스에 Comparable 상속받고있지 않아 발생하는 오류입니다.

~~~
public class UniversityPublic implements Comparable<UniversityPublic> {

    ... 생략
    private Integer uniLike;

    @Override
    public int compareTo(UniversityPublic o) {
        return Integer.compare(this.uniLike, o.getUniLike());
    }
} 

`Comparable<UniversityPublic> 상속`받고 필수 메소드인 `compareTo를 오버라이드` 해줍니다.
compareTo는 정렬 기준을 직접 만들 수 있습니다.
위 코드는 uniLike 를 기준으로 정렬한다는 메소드입니다.

만약 객체를 `기본 비교(Comparable) 방법으로 정렬`하고 싶다면 아래와 같이 사용할 수 있다.

~~~
                        ... 생략
                            u.getUniLike().contains(account),
                    )
                ).sorted(Comparator.naturalOrder()).collect(Collectors.toList())
        )
)
.collect(Collectors.toList());
~~~

기본 비교(Comparable) `역순으로 정렬`하고 싶다면 아래와 같이 사용

~~~
                        ... 생략
                            u.getUniLike().contains(account),
                    )
                ).sorted(Comparator.reverseOrder()).collect(Collectors.toList())
        )
)
.collect(Collectors.toList());
~~~

https://cornswrold.tistory.com/298 - [JAVA-Stream-정렬(sorted())]

# 임시 정리 주소

https://dotoridev.tistory.com/2 - [QueryDsl 삽질기 1부 [ONE TO MANY 중복제거]]

https://jojoldu.tistory.com/394 - [If-문]

https://devbox.tistory.com/entry/DBMS-CASEWHENTHEN - [sql when 문]

https://effectivesquid.tistory.com/entry/JPQL-%EB%B9%8C%EB%8D%94-%ED%81%B4%EB%9E%98%EC%8A%A4-QueryDsl-%EC%82%AC%EC%9A%A9%EB%B2%95 - [JPQL-빌더-클래스-QueryDsl-사용법]

https://kic1.tistory.com/102 - [복잡한 쿼리]

https://joont92.github.io/jpa/QueryDSL/ - [QueryDSL 사용법]