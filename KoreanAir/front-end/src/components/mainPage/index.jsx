import React, { Fragment } from "react";
import { injectIntl } from "react-intl";

import FirstSection from "./firstSection";
import SecondSection from "./secondSection";

const HomePage = ({ intl }) => (
  <Fragment>
    <FirstSection intl={intl} />
    <SecondSection intl={intl} />
  </Fragment>
);

export default injectIntl(HomePage);
