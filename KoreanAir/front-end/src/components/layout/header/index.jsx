import React, { Fragment } from "react";
import { injectIntl } from "react-intl";

import Utilnav from "./utilnav";

const Header = ({ intl }) => (
  <Fragment>
    <Utilnav intl={intl} />
  </Fragment>
);

export default injectIntl(Header);
