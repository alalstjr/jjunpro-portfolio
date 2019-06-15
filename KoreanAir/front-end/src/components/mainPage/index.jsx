import React from "react";
import { injectIntl } from "react-intl";

import FirstSection from "./firstSection";

const HomePage = ({ intl }) => (
  <section>
    <FirstSection intl={intl} />
  </section>
);

export default injectIntl(HomePage);
