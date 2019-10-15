import React from "react";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";

import MainPage from "./components/mainPage";
import Board from "./components/board";
import BoardWriteInsert from "./components/board/write/writeInsert";
import BoardWriteUpdate from "./components/board/write/writeUpdate";
import BoardView from "./components/board/view";

const App = () => {
    return (
      <Router>
        <Switch>
          {/* 메인페이지 */}
          <Route exact path="/" component={MainPage} />
          {/* 게시판 */}
          <Route exact path="/pugjjig" component={Board} />
          <Route exact path="/pugjjig/write" component={BoardWriteInsert} />
          <Route exact path="/pugjjig/:page_num" component={Board} />
          <Route exact path="/pugjjig/write/:bo_num" component={BoardWriteUpdate} />
          <Route exact path="/pugjjig/view/:bo_num" component={BoardView} />
          {/* <Redirect from="*" to="/" /> */}
        </Switch>
      </Router>
      );
}

export default App;
