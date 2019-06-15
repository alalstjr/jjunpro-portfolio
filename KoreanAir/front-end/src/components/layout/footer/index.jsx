import React from "react";
import { injectIntl } from "react-intl";

import FirstSection from "./firstSection";
import SecondSection from "./secondSection";
import ThirdSection from "./thirdSection";

import { Container } from "../../../style/globalStyles";
import { FooterBox, FooterLeft } from "./styleFooter";

const Footer = ({ intl }) => (
  <FooterBox>
    <Container>
        <FooterLeft>
            <FirstSection intl={intl} />
            <SecondSection intl={intl} />
        </FooterLeft>
        <ThirdSection intl={intl} />
    </Container>
  </FooterBox>
);

export default injectIntl(Footer);