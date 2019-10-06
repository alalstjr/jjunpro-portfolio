import React, { Fragment } from "react"
import { connect } from 'react-redux'
import { injectIntl } from "react-intl"
import PropTypes from "prop-types"

import Utilnav from "./utilnav"

const Header = ({ intl, account }) => (
  <Fragment>
    <Utilnav 
      intl={intl} 
      account={account}
    />
  </Fragment>
);

// 유저 정보 props
Header.propTypes = {
  account : PropTypes.object.isRequired
}

export default injectIntl(connect(
  (state) => ({
    account: state.account.userInfo
  })
)(Header));
