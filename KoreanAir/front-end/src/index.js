import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';

import { Provider } from "react-redux";
import ConnectedIntlProvider from "./connectedIntlProvider";
import store from "./store"

// GlobalStyle
import { GlobalStyle } from "./style/globalStyles";
import { ThemeProvider } from "styled-components";
import theme from "./style/theme";

ReactDOM.render(
    <Provider store={store}>
        <ConnectedIntlProvider>
            <ThemeProvider theme={theme}>
                <React.Fragment>
                    <GlobalStyle />
                    <App />
                </React.Fragment> 
            </ThemeProvider>
        </ConnectedIntlProvider>
    </Provider>,
    document.getElementById('root')
);
