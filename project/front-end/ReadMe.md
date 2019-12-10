목차
- [1. CORS Response Headers 오류 설정](#CORS-Response-Headers-오류-설정)
- [2. React Router 4 일반페이지 와 관리자페이지 나누기](#React-Router-4-일반페이지-와-관리자페이지-나누기)
- [3. React-Admin 정보](#React-Admin-정보)
    - [1. authProvider 유저 ROLE권한 체크](#authProvider-유저-ROLE권한-체크)
- [4. axios 정리](#axios-정리)
    - [1. axios jwt token 기본설정](#axios-jwt-token-기본설정)
- [5. 새로 전달받은 값 체크후 Re 랜더링 방법](#새로-전달받은-값-체크후-Re-랜더링-방법)


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

- https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/headers.html#headers-static - [Spring Security Static Headers 등록 설정 방법]

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

- https://stackoverflow.com/questions/49473727/how-to-handle-multiple-routers-in-react -[How to handle multiple routers in react 여러개의 Router 관리하기]

# React-Admin 정보

- https://marmelab.com/react-admin/index.html - [React-Admin-튜토리얼]
- https://github.com/marmelab/react-admin/tree/master/docs - [React-Admin-Git-DOCS]

## authProvider 유저 ROLE권한 체크

> src/admin/authProvider/index.jsx

~~~
export const adminAccountCheck = async () => {
    axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
    return await axios.get("http://localhost:8080/api/account/admin")
        .then(function(){
            return Promise.resolve();
        }).catch(function() {
            return Promise.reject({ redirectTo: '/' });
        });    
}
~~~

headers 로그인한 유저의 Token 값을 전송하여 해당 유저의 ROLE 값을 실시간으로 확인합니다.
만약에 ROLE값이 임의로 변경되면 서버에서 체크하여 강제 로그아웃 시킵니다.

- https://github.com/marmelab/react-admin/blob/master/docs/Authentication.md - [관리자페이지-로그인-인증-promise]

# axios 정리

- https://github.com/axios/axios/issues/1422 - [axios.Catch-error-체크전송]
- https://github.com/axios/axios/issues/193 - [axios-에러-체크-후-reject-request]

## axios jwt token 기본설정

~~~
axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
~~~

- http://jeonghwan-kim.github.io/2018/03/26/vue-authentication.html - [axios-defaults-headers-설정]

# 새로 전달받은 값 체크후 Re 랜더링 방법

map, filter 기능을 활요하여 배열의 변경된 값을 체크하여 값을 수정합니다.

~~~
class 클래스 {
    ...
    handleLikeUpdate = (preData, postData) => {
        let props;
        preData.map(preData => {
            // id 가 일치하면 변경되는 값만 찾아서 변경합니다.
            if(preData.key*1 === postData.uniId) {
                props = preData.props;
                props.university.uniLike = postData.likeCount;
                props.university.uniLikeState = postData.likeState;
            }
        });
    }
    ...
}
~~~

위 예시로 게시글의 좋아요 버튼을 클릭시 해당 게시글{id} 만 좋아요 카운팅과 좋아요상태를 실시간으로 변경해 주기 위해서
map 메소드를 활용한 handle 함수 메소드입니다.

매개변수로 {preData} 변경 이전의 배열값을 받습니다.
{postData} 서버로부터 받은 변경된 데이터의 오브젝트 입니다. 변경되는 값만 지니고 있습니다.

~~~
preData 데이터 >
(2) [{…}, {…}]
    0: {$$typeof: Symbol(react.element), key: "4", ref: null, props: {…}, type: ƒ, …}
    1: {$$typeof: Symbol(react.element), key: "2", ref: null, props: {…}, type: ƒ, …}

postData 데이터 >
{uniId: 4, likeCount: 0, likeState: false}
~~~

postData.uniId 값으로 변경된 값을 찾은 후 prop에 직접 변경합니다.

불변성을 지킨 다른 예시
~~~
preData.map(preData => preData.key*1 === postData.uniId
    ? ({ 
        ...preData.props.university,
        uniLike: postData.likeCount,
        uniLikeState: postData.likeState
    }) // id 가 일치하면 새 객체를 만들고, 기존의 내용을 집어넣고 원하는 값 덮어쓰기
    : preData // 바꿀 필요 없는것들은 그냥 기존 값 사용
);
~~~

- https://velopert.com/3638 - [배열-다루기-제거와-수정]

# 리누스 docker 간단 설치

https://www.leafcats.com/153