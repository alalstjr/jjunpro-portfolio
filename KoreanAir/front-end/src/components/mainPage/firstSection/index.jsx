import React from "react";

const FirstSection = ({ intl }) => (
  <section>
    {intl.formatMessage({ id: "HOME.FIRST.subtitle" })}
  </section>
);

export default FirstSection;
