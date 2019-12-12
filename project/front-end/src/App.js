import React from "react"
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom"

import Main from "./components/mainPage/MainProvider"

import PugjjigView from "./components/pugjjig/view/PugjjigView"

import MyPage from "./components/member/myPage/MyPageProvider"
import PugjjigSearch from "./components/search/pugjjig/PugjjigSearchProvider"

const App = () => {
    return (
      <Router>
        <Switch>
          {/* 메인페이지 */}
          <Route exact path="/" component={Main} />
          {/* Pugjjig Review */}
          <Route exact path="/pugjjig/:id" component={PugjjigView} />
          {/* Pugjjig Search */}
          <Route exact path="/pugjjig/uniSearch/:id" component={PugjjigSearch} />
          <Route exact path="/pugjjig/stoSearch/:id" component={PugjjigSearch} />
          <Route exact path="/pugjjig/tagSearch/:id" component={PugjjigSearch} />
          <Route exact path="/pugjjig/userSearch/:id" component={PugjjigSearch} />
          {/* User MyPage */}
          <Route exact path="/mypage" component={MyPage} />

          <Redirect from="*" to="/" />
        </Switch>
      </Router>
      );
}

export default App;
