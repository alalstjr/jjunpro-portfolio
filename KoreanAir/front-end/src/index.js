import React from 'react'
import ReactDOM from 'react-dom'
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";

import App from './App'

import { Provider } from "react-redux"
import ConnectedIntlProvider from "./connectedIntlProvider"
import store from "./store"

// GlobalStyle
import { GlobalStyle } from "./style/globalStyles"
import { ThemeProvider } from "styled-components"
import theme from "./style/theme"

import LnitialComponent from "./initialComponent"

import { AdminComponent } from './admin';

ReactDOM.render(
    <Provider store={store}>
        <ConnectedIntlProvider>
            <ThemeProvider theme={theme}>
                <React.Fragment>
                    <GlobalStyle />
                    <LnitialComponent />

                    <Router>
                        <Route path="/" exact component={App} />
                        <Route exact path="/admin" component={AdminComponent} />
                    </Router>

                </React.Fragment> 
            </ThemeProvider>
        </ConnectedIntlProvider>
    </Provider>,
    document.getElementById('root')
);
