import React, { Fragment } from "react";
import { Link } from "react-router-dom";
import { FooterContainer, FooterList, FooterListTitle, FooterListUl } from "../style";

{/* {intl.formatMessage({ id: "HOME.FIRST.subtitle" })} */}

const FirstSection = ({ intl }) => (
    <FooterContainer>
        <FooterList>
            <FooterListTitle>
                약관 및 규정
            </FooterListTitle>
            <FooterListUl>
                <li>
                    <Link to="/">개인정보 처리방침</Link>
                    <Link to="/">회원약관</Link>
                    <Link to="/">운송약관</Link>
                    <Link to="/">소비자 안전 관련 정보 공개</Link>
                    <Link to="/">운임 및 서비스 요금표</Link>
                </li>
            </FooterListUl>
        </FooterList>
        <FooterList>
            <FooterListTitle>
                기타 안내
            </FooterListTitle>
            <FooterListUl>
                <li>
                    <Link to="">고객 안내 서비스</Link>
                    <Link to="">항공교통이용자 서비스</Link>
                    <Link to="">항공교통이용자 피해구제</Link>
                    <Link to="">관련 사이트</Link>
                    <Link to="">사이트맵</Link>
                </li>
            </FooterListUl>
        </FooterList>
        <FooterList>
            <FooterListTitle>
                고객지원
            </FooterListTitle>
            <FooterListUl>
                <li>
                    <Link to="">지점 연락처</Link>
                    <Link to="">고객의 말씀</Link>
                    <Link to="">FAQ</Link>
                    <Link to="">e 서식함</Link>
                    <Link to="">채팅 서비스</Link>
                </li>
            </FooterListUl>
        </FooterList>
        <FooterList>
            <FooterListTitle>
                회사소개
            </FooterListTitle>
            <FooterListUl>
                <li>
                    <Link to="">대한항공에 대하여</Link>
                    <Link to="">경영 재무 정보</Link>
                    <Link to="">녹색경영</Link>
                    <Link to="">사회공헌활동/초등학생견학</Link>
                    <Link to="">공지사항/뉴스</Link>
                </li>
            </FooterListUl>
        </FooterList>
    </FooterContainer> 
);

export default FirstSection;