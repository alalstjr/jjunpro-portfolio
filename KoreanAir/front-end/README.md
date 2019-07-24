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