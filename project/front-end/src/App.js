import React from "react"
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom"

import MainPage from "./components/mainPage"
import Board from "./components/board"
import BoardWriteInsert from "./components/board/write/writeInsert"
import BoardWriteUpdate from "./components/board/write/writeUpdate"
import BoardView from "./components/board/view"

import PugjjigView from "./components/pugjjig/view/PugjjigView"
import PugjjigViewList from "./components/myPage/myList/PugjjigViewList"
import PugjjigLikeList from "./components/myPage/myList/PugjjigLikeList"

const App = () => {
    return (
      <Router>
        <Switch>
          {/* 메인페이지 */}
          <Route exact path="/" component={MainPage} />
          {/* 게시판 */}
          <Route exact path="/board" component={Board} />
          <Route exact path="/board/write" component={BoardWriteInsert} />
          <Route exact path="/board/:page_num" component={Board} />
          <Route exact path="/board/write/:bo_num" component={BoardWriteUpdate} />
          <Route exact path="/board/view/:bo_num" component={BoardView} />
          {/* Pugjjig Review */}
          <Route exact path="/pugjjig/:id" component={PugjjigView} />
          {/* Pugjjig Reviews */}
          <Route exact path="/pugjjigs" component={PugjjigViewList} />
          <Route exact path="/pugjjigs/:userId" component={PugjjigViewList} />
          {/* Pugjjig Like */}
          <Route exact path="/pugjjigLikes" component={PugjjigLikeList} />
          <Route exact path="/pugjjigLikes/:userId" component={PugjjigLikeList} />

          <Redirect from="*" to="/" />
        </Switch>
      </Router>
      );
}

export default App;
