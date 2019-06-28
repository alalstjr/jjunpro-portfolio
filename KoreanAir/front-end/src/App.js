import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import { injectIntl } from "react-intl";

import Header from "./components/layout/header";
import Footer from "./components/layout/footer";
import MainPage from "./components/mainPage";
import Board from "./components/board";
import BoardWrite from "./components/board/write";
import BoardView from "./components/board/view";

class App extends Component {
  render() {
  return (
    <Router>
      <Header/>
      <Switch>
        {/* 메인페이지 */}
        <Route exact path="/" component={MainPage} />
        {/* 게시판 */}
        <Route exact path="/boardEvent" component={Board} />
        <Route exact path="/boardEvent/write" component={BoardWrite} />
        <Route exact path="/boardEvent/view/:bo_num" component={BoardView} />
        <Redirect from="*" to="/" />
      </Switch>
      <Footer/>
    </Router>
    );
  }
}

export default injectIntl(App);
