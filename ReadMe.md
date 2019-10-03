--------------------
# Spring JPA 연습 프로젝트 제작
--------------------


# 목차

- [1. JPA 초기 셋팅](#JPA-초기-셋팅)
- [2. React Admin](#React-Admin)

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