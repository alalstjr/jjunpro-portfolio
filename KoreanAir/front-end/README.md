목차
[1. CORS Response Headers 오류 설정](#CORS-Response-Headers-오류-설정)
[2. React Router 4 일반페이지 와 관리자페이지 나누기](#React-Router-4-일반페이지-와-관리자페이지-나누기)

# React hooc localstore
https://www.youtube.com/watch?v=kQKs7o-X0zc
https://www.robinwieruch.de/local-storage-react/

# React Local State vs Redux State(Store) 언제, 어떤 것을 사용해야 하나?
https://kimch3617.tistory.com/entry/React-Local-State-vs-Redux-StateStore-%EC%96%B8%EC%A0%9C-%EC%99%9C-%EC%82%AC%EC%9A%A9%ED%95%B4%EC%95%BC-%ED%95%98%EB%82%98

# React Lifecycle
https://jaeyeophan.github.io/2018/01/01/React-4-Component-Life-Cycle/

# When to use “join,” “register,” or “sign up”?
https://ux.stackexchange.com/questions/11936/when-to-use-join-register-or-sign-up

# 유저 로그인 유지 react
https://backend-intro.vlpt.us/6/06.html

# react styled keyframe v4 변경사항
https://www.styled-components.com/docs/faqs#what-do-i-need-to-do-to-migrate-to-v4
https://github.com/styled-components/styled-components/issues/2102

# react setState 가 비동기로 처리되는 이유
https://medium.com/@saturnuss/setstate-%EB%8A%94-await%EC%99%80-%EC%82%AC%EC%9A%A9%EC%9D%B4-%EA%B0%80%EB%8A%A5%ED%95%A0%EA%B9%8C-7b02581f6df4

# react state 객체 or 배열 선언시 setState 설정
https://stackoverflow.com/questions/43638938/updating-an-object-with-setstate-in-react

this.setState({
    warning: {
        ...this.state.warning,
        userId
    }
});
...this.state.warning,
식으로 선언해줘야 기존의 다른 초기값들도 존재하게 됩니다. 없다면 userId값만 존재하게 됩니다.

# axios jwt token 기본설정
http://jeonghwan-kim.github.io/2018/03/26/vue-authentication.html
axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

# React-Admin 정보

## authProvider
관리자페이지 로그인 인증 promise - https://github.com/marmelab/react-admin/blob/master/docs/Authentication.md

### axios 정보
axios.Catch error 체크전송 - https://github.com/axios/axios/issues/1422
axios 에러 체크 후 reject request - https://github.com/axios/axios/issues/193

# CORS Response Headers 오류 설정

> src/admin/index.jsx

~~~
...
const dataProvider = jsonServerProvider('http://localhost:8080/api', httpClient);
export const AdminComponent = () => (
    <Admin dashboard={Dashboard} authProvider={authProvider} dataProvider={dataProvider}>
        <Resource name="account" list={UserList} />
    </Admin>
);
...
~~~

http://localhost:8080/api/account 로 spring 서버로 유저 리스트 정보를 얻기위해 요청하면 아래와 같은 오류가 발생했습니다.

- 원본
`The X-Total-Count header is missing in the HTTP Response. The jsonServer Data Provider expects responses for lists of resources to contain this header with the total number of results to build the pagination. If you are using CORS, did you declare X-Total-Count in the Access-Control-Expose-Headers header?`

- 번역
`HTTP 응답에 X-Total-Count 헤더가 누락됨 jsonServer 데이터 공급자는 리소스 목록에 대한 응답에 이 머리글을 포함하며, 이 머리글을 작성하기 위한 총 결과 수가 포함될 것으로 예상한다. CORS를 사용하는 경우 Access-Control-Expose-Headers 헤더에 X-Total-Count를 선언하셨습니까?`

번역한 글만보면 서버로부터 받은 Response Headers 목록에 X-Total-Count 값이 존재하지 않아서 발생하는 오류라는것을 체크했습니다.
이제 Client 에서 Spring Server 로 요청이 와서 응답해줄 때 Spring Security 에서 headers 값을 직접 설정해서 Client 로 Response 해주도록 합니다.

https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/headers.html#headers-static - [Spring Security Static Headers 등록 설정 방법]

> java config/WebSecurityConfig.java

~~~
protected void configure(HttpSecurity http) throws Exception {
    ...
    http
		.headers()
		.addHeaderWriter(new StaticHeadersWriter("X-Total-Count", "10"));
    ...
}
~~~

# React Router 4 일반페이지 와 관리자페이지 나누기

일반 페이지 와 관리자 페이지 를 나누어서 보여줘야할 필요가 있었습니다.

> src/App.js

- 이전 코드
~~~
const App = () => {
    return (
      <Router>
        <Header/>
        <Switch>
          {/* 관리자페이지 */}
          <Route exact path="/admin" component={AdminComponent} />
          {/* 메인페이지 */}
          <Route exact path="/" component={MainPage} />
          {/* 게시판 */}
          <Route exact path="/boardEvent" component={Board} />
          <Route exact path="/boardEvent/write" component={BoardWriteInsert} />
          <Route exact path="/boardEvent/:page_num" component={Board} />
          <Route exact path="/boardEvent/write/:bo_num" component={BoardWriteUpdate} />
          <Route exact path="/boardEvent/view/:bo_num" component={BoardView} />
          <Redirect from="*" to="/" />
        </Switch>
        <Footer/>
      </Router>
      );
}
~~~

- 변경 코드
~~~
const App = () => {
    return (
      <Router>
        <Header/>
        <Switch>
          - {/* 관리자페이지 */}
          - <Route exact path="/admin" component={AdminComponent} />
          {/* 메인페이지 */}
          <Route exact path="/" component={MainPage} />
          {/* 게시판 */}
          <Route exact path="/boardEvent" component={Board} />
          <Route exact path="/boardEvent/write" component={BoardWriteInsert} />
          <Route exact path="/boardEvent/:page_num" component={Board} />
          <Route exact path="/boardEvent/write/:bo_num" component={BoardWriteUpdate} />
          <Route exact path="/boardEvent/view/:bo_num" component={BoardView} />
          <Redirect from="*" to="/" />
        </Switch>
        <Footer/>
      </Router>
      );
}
~~~

> src/index.jsx
~~~
ReactDOM.render(
    <Provider store={store}>
        <ConnectedIntlProvider>
            <ThemeProvider theme={theme}>
                <React.Fragment>
                    <GlobalStyle />
                    <LnitialComponent />

                    <Router>
                        {/* 일반페이지 */}
                        <Route path="/" exact component={App} />
                        + {/* 관리자페이지 */}
                        + <Route exact path="/admin" component={AdminComponent} />
                    </Router>

                </React.Fragment> 
            </ThemeProvider>
        </ConnectedIntlProvider>
    </Provider>,
    document.getElementById('root')
);
~~~

이런식으로 App 밖에 Route 를 따로 일반페이지, 관리자페이지 선언하여 path 를 통해 구별하여 나오도록 했습니다.

https://stackoverflow.com/questions/49473727/how-to-handle-multiple-routers-in-react -[How to handle multiple routers in react 여러개의 Router 관리하기]