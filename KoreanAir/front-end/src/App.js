import React from "react";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import { injectIntl } from "react-intl";

import Header from "./components/layout/header";
import Footer from "./components/layout/footer";
import MainPage from "./components/mainPage";
import Board from "./components/board";
import BoardWriteInsert from "./components/board/write/writeInsert";
import BoardWriteUpdate from "./components/board/write/writeUpdate";
import BoardView from "./components/board/view";

import { AdminComponent } from './admin';

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

export default injectIntl(App);
