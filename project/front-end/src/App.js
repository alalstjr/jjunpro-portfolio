import React from "react"
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom"

import Main from "./components/mainPage/MainProvider"

import PugjjigView from "./components/pugjjig/view/PugjjigView"

import Board from "./components/board"
import BoardWriteInsert from "./components/board/write/writeInsert"
import BoardWriteUpdate from "./components/board/write/writeUpdate"
import BoardView from "./components/board/view"
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

          {/* 게시판 */}
          <Route exact path="/board" component={Board} />
          <Route exact path="/board/write" component={BoardWriteInsert} />
          <Route exact path="/board/:page_num" component={Board} />
          <Route exact path="/board/write/:bo_num" component={BoardWriteUpdate} />
          <Route exact path="/board/view/:bo_num" component={BoardView} />

          <Redirect from="*" to="/" />
        </Switch>
      </Router>
      );
}

export default App;
