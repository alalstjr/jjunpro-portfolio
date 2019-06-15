import React, { Fragment } from "react";
import { Link } from "react-router-dom";
import { FooterList, FooterListTitle, FooterListUl } from "../styleFooter";
import { FlexContainer } from "../../../../style/globalStyles";

{/* {intl.formatMessage({ id: "HOME.FIRST.subtitle" })} */}

const FirstSection = ({ intl }) => (
    <FlexContainer>
        <FooterList>
            <FooterListTitle>
                약관 및 규정
            </FooterListTitle>
            <FooterListUl>
                <li>
                    <Link to="">개인정보 처리방침</Link>
                </li>
            </FooterListUl>
        </FooterList>
        <FooterList>
            <FooterListTitle>
                기타 안내
            </FooterListTitle>
            <FooterListUl>
                <li>
                    <Link to="">개인정보 처리방침</Link>
                </li>
            </FooterListUl>
        </FooterList>
        <FooterList>
            <FooterListTitle>
                고객지원
            </FooterListTitle>
            <FooterListUl>
                <li>
                    <Link to="">개인정보 처리방침</Link>
                </li>
            </FooterListUl>
        </FooterList>
        <FooterList>
            <FooterListTitle>
                회사소개
            </FooterListTitle>
            <FooterListUl>
                <li>
                    <Link to="">개인정보 처리방침</Link>
                </li>
            </FooterListUl>
        </FooterList>
    </FlexContainer> 
);

export default FirstSection;