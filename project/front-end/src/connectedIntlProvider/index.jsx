import React from "react";
import { connect } from "react-redux";
import { IntlProvider } from "react-intl";

import { changeLocale } from "./action";

const getCurrentLocale = () => {
  let defaultLang = navigator.language || navigator.userLanguage || navigator.languages[0];

  defaultLang = defaultLang.split("-")[0];
  if (defaultLang !== "ko") {
    defaultLang = "en";
  }
  return defaultLang;
};

class ConnectedIntlprovider extends React.Component {
  componentWillMount() {
    const { dispatch } = this.props;

    dispatch(changeLocale(getCurrentLocale()));
  }

  render() {
    const { children, locale, message } = this.props;
    return (
      <IntlProvider locale={locale} messages={message}>
        {React.Children.only(children)}
      </IntlProvider>
    );
  }
}

const mapStateToProps = state => {
  return { locale: state.locale.locale, message: state.locale.message };
};

export default connect(mapStateToProps)(ConnectedIntlprovider);
