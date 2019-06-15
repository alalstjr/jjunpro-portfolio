import React from "react";

const SecondSection = ({ intl }) => (
  <section>
    {intl.formatMessage({ id: "HOME.FIRST.subtitle" })}
  </section>
);

export default SecondSection;
