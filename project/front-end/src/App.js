import React, {Fragment} from "react";
import {BrowserRouter as Router, Route, Switch, Redirect} from "react-router-dom";

import Main from "./components/mainPage/MainProvider";
import PugjjigView from "./components/pugjjig/view/PugjjigView";
import MyPage from "./components/member/myPage/MyPageProvider";
import PugjjigSearch from "./components/search/pugjjig/PugjjigSearchProvider";
import Square from "./components/square/SquareProvider";
import AccountProvider from "./components/member/account/AccountProvider";
import AlarmProvider from "./components/Alarm/AlarmProvider";

const App = () => {
    return (
        < Fragment >
        < Router >
        < Switch >
        {/* 메인페이지 */}
        < Route
    exact
    path = "/"
    component = {Main}
    />
    {/* Pugjjig Review */
    }
<
    Route
    exact
    path = "/pugjjig/:id"
    component = {PugjjigView}
    />
    {/* Pugjjig Search */
    }
<
    Route
    exact
    path = "/pugjjig/uniSearch/:id"
    component = {PugjjigSearch}
    />
    < Route
    exact
    path = "/pugjjig/stoSearch/:id"
    component = {PugjjigSearch}
    />
    < Route
    exact
    path = "/pugjjig/tagSearch/:id"
    component = {PugjjigSearch}
    />
    < Route
    exact
    path = "/pugjjig/userSearch/:id"
    component = {PugjjigSearch}
    />
    {/* Pugjjig Best */
    }
<
    Route
    exact
    path = "/square"
    component = {Square}
    />
    {/* User MyPage */
    }
<
    Route
    exact
    path = "/mypage"
    component = {MyPage}
    />
    < Redirect
    from = "*"
    to = "/" / >
        < /Switch>
        < /Router>
    {/* 유저 로그인 회원가입 Component */
    }
<
    AccountProvider / >
    {/* 알림 Component */}
    < AlarmProvider / >
    < /Fragment>
)
    ;
}

export default App;
