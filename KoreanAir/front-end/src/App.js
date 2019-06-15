import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import { injectIntl } from "react-intl";

import Header from "./components/layout/header";
import Footer from "./components/layout/footer";
import MainPage from "./components/mainPage";

class App extends Component {
  render() {
  return (
    <Router>
      <Header/>
      <Switch>
        <Route exact path="/" component={MainPage} />
        <Redirect from="*" to="/" />
      </Switch>
      <Footer/>
    </Router>
    );
  }
}

export default injectIntl(App);
