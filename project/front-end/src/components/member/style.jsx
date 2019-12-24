import styled, { css } from "styled-components"
import { ClearFix, InitTransition, CookieFontB, CommonSpace } from "../../style/globalStyles"

const PC_S = 1200;
const TABLET = 991;
const MOBILE_B = 768;
const MOBILE_M = 640;
const MOBILE_S = 480;

/*******************
    Login Style
********************/
export const LoginWrap = styled.div`
    max-width: 18.75rem;
    margin: 0 auto;
    padding: 1.1rem 2.3125rem;
    margin-bottom: 0.625rem;
    background-color: #fff;

    @media only screen and (max-width: ${MOBILE_S}px) {
        margin-bottom: 0;
    }
`;
export const LoginLogo = styled.h1`
    ${InitTransition}
    ${CookieFontB}
    text-align: center;
    font-weight: 600;
    padding: 0.625rem 0 1.25rem;
    color: ${props => props.theme.themeColorHover};

    ${
        props => props.initSearch ?
        "font-size: 2.813rem;"
        :
        "font-size: 1.563rem;"
    }
`;
export const LoginBtnCss = css`
    display: block;
    margin-bottom: 0.3125rem;
    height: 2.5rem;
    line-height: 2.5rem;
    box-sizing: border-box;
    font-size: 0.875rem;
    font-weight: bold;
    text-align: center;
    width: 100%;
`;
export const LoginBtn = styled.button`
    ${LoginBtnCss}
    border: 0.0625rem solid #e3e3e3;
    color: #737373;
`;
export const SignUpBtn = styled.button`
    ${LoginBtnCss}
    background-color: ${props => props.theme.themeColorHover};
    color: #fff;
    margin-bottom: 0.3125rem;

    > a {
        display: block;
        color: #fff;
        font-size: 0.875rem;
        font-weight: bold;
    }
`;

/*******************
    Login Modal Style
********************/
export const ModalOverlay = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    background-color: rgba(0,0,0,0.6);
    z-index: 9;

    &.Modal-anim-enter {
        opacity: 0.00;
        transition: all 0.2s;
    }
    &.Modal-anim-enter.Modal-anim-enter-active {
        opacity: 1;
    }
    &.Modal-anim-leave {
        opacity: 1;
        transition: all 0.2s;
    }
    &.Modal-anim-leave.Modal-anim-leave-active {
        opacity: 0.00;
    }
`;
export const Modal = styled.div`
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 100%;
    max-width: 28.13rem;
    border-radius: 0.1875rem;
    background-color: white;
    box-shadow: 0 0.1875rem 0.375rem rgba(0,0,0,0.16);
    z-index: 10;
    padding: 0 1.25rem;

    &.Modal-anim-enter {
        opacity: 0.00;
        transform: translate(-50%, -50%) scale(0.7);
        transition: all 0.2s;
    }
     &.Modal-anim-enter.Modal-anim-enter-active {
        opacity: 1;
        transform: translate(-50%, -50%) scale(1);
    }
    &.Modal-anim-leave {
        opacity: 1;
        transform: translate(-50%, -50%) scale(1);
        transition: all 0.2s;
    }  
    &.Modal-anim-leave.Modal-anim-leave-active {
        opacity: 0.00;
        transform: translate(-50%, -50%) scale(0.7);
    }

    @media only screen and (max-width: ${MOBILE_S}px) {
        max-width: 95%;
    }
`;
export const TitleWrap = styled.div`
    ${ClearFix}
    position: absolute;
    right: 0.9375rem;
    top: 0.75rem;
`;
export const TitleLogo = styled.h1`
    color: ${props => props.theme.themeColorHover};
    float: left;
    font-size: 1.75rem;
    font-weight: bold;
`;
export const Title = styled.h3`
    font-size: 1.125rem;
    float: right;
    font-weight: bold;
    color: #3E4348;
`;
export const Content = styled.div`
    
`;

/*******************
    MyPage Style
********************/
export const MyPageWrap = styled.div`
    ${CommonSpace}

    background-color: #fff;
    border-radius: 0.1875rem;
    max-width: 58.44rem;
    overflow: hidden;
    width: 100%;
    display: flex;

    @media only screen and (max-width: ${MOBILE_M}px) {
        width: 100%;
        display: block;
        padding: 0 0.9375rem;
        background-color: transparent;
        margin: 0.625rem auto 0;
    }
`;
export const MyPageLeft = styled.div`
    ${ClearFix}
    display: inherit;
    flex-direction: column;
    width: 14.38rem;

    @media only screen and (max-width: ${MOBILE_B}px) {
        width: 10rem;
    }
    @media only screen and (max-width: ${MOBILE_M}px) {
        width: 100%;
        padding: 0.9375rem;
    }
    @media only screen and (max-width: ${MOBILE_S}px) {
        padding: 0;
    }
`;
export const MyPageRight = styled.div`
    display: inherit;
    flex-direction: column;
    width: calc(100% - 14.38rem);

    background-color: ${props => props.theme.backgroundColor};

    @media only screen and (max-width: ${MOBILE_B}px) {
        width: calc(100% - 10rem);
    }
    @media only screen and (max-width: ${MOBILE_M}px) {
        width: 100%;
        padding: 0.9375rem;
    }
    @media only screen and (max-width: ${MOBILE_S}px) {
        padding: 0.9375rem 0;
    }
`;
export const MyPageWhite = styled.div`
    margin-left: 0.9375rem;
    background-color: #fff;

    @media only screen and (max-width: ${MOBILE_M}px) {
        margin: 0;
    }
`;
export const MyPageList = styled.li`
    cursor: pointer;
    font-size: 1rem;
    padding: 1rem 1rem 1rem 1.875rem;
    border-left: 0.125rem solid transparent;
    transition: 0.4s all ease;

    ${
        props => props.active === "likeRecord" ?
        `:nth-child(4)`
        :
        props => props.active === "repository" ?
        `:nth-child(3)`
        :
        props => props.active === "password" ?
        `:nth-child(2)`
        :
        `:nth-child(1)`
    } { 
        border-color: ${props => props.theme.themeColor};
        color: ${props => props.theme.themeColor};
        font-weight: 600;
    }

    &:hover {
        background-color: #fafafa;
        border-left-color: #dbdbdb;
    }

    @media only screen and (max-width: ${MOBILE_M}px) {
        width: 25%;
        float: left;
        background-color: #fff;
        padding: 0.625rem;
        text-align: center;
        font-size: 0.875rem;

        border-left: none;
        border-bottom: 0.125rem solid transparent;
    }
    @media only screen and (max-width: ${MOBILE_S}px) {
        font-size: 0.75rem;
        padding: 0.625rem 0;
    }
`;
export const GroupBox = styled.div`
    display: flex;
    margin-bottom: 1rem;

    @media only screen and (max-width: ${MOBILE_B}px) {
        display: block;
    }
`;
export const ProfileLabel = styled.div`
    flex-basis: 28%;
    display: flex;
    flex-direction: column;

    text-align: right;
    padding-right: 1.875rem
    font-size: 1rem;
    font-weight: 400;
    margin: auto;

    @media only screen and (max-width: ${MOBILE_B}px) {
        text-align: left;
        padding: 0.625rem 0.9375rem;
    }
`;
export const ProfileInput = styled.div`
    flex-basis: 72%;
    display: flex;
    flex-direction: column;

    padding: 0 1.875rem;
    padding-right: 3.75rem;
    position: relative;

    > #urlListInput {
        padding-right: 3.75rem;
    }

    @media only screen and (max-width: ${MOBILE_B}px) {
        padding: 0 0.9375rem;
        padding-right: 0.9375rem
    }
`;
export const ProfileWrap = styled.div`
    margin-top: 1.875rem;

    @media only screen and (max-width: ${MOBILE_B}px) {
        margin-top: 0;
    }
`;
export const UrlList = styled.ul`
    padding: 0 0.9375rem;
    border: 0.0625rem solid #e2e2e2;
    margin-top: 0.625rem;
`;
export const Url = styled.li`
    position: relative;
    padding: 0.3125rem 0;
`;
export const CloseBtn = styled.button.attrs({
    type: "button"
})`
    position: absolute;
    right: 0;
    top: 0.4375rem;
    width: 1.063rem;
    height: 1.063rem;

    &:before,
    &:after {
        position: absolute;
        left: 0.5rem;
        top: 0.0625rem;
        content: ' ';
        height: 0.9375rem;
        width: 0.0625rem;
        background-color: #333;
    }
    &:before {
        transform: rotate(45deg);
    }
    &:after {
        transform: rotate(-45deg);
    }
`;
export const UrlSaveBtn = styled.button.attrs({
    type: "button"
})`
    width: 3.125rem;
    height: 1.563rem;
    border-radius: 0.1875rem;
    background-color: ${props => props.theme.themeColor};
    color: #fff;
    position: absolute;
    right: 3.938rem;
    top: 0.125rem;

    &:hover {
        background-color: ${props => props.theme.themeColorHover};
    }

    @media only screen and (max-width: ${MOBILE_B}px) {
        right: 0.9375rem;
    }
`;
export const SaveBtn = styled.button.attrs({
    type: "submit"
})`
    background: ${props => props.theme.themeColor};
    color: #fff;
    padding: 0.4375rem;
    font-size: 0.875rem;
    font-weight: 600;
    border-radius: 0.1875rem;

    &:hover {
        background-color: ${props => props.theme.themeColorHover};
    }
`;
export const ProfileId = styled.div`
    font-size: 1.125rem;
    font-weight: 400;
    margin-bottom: 0.3125rem;
`;

/*******************
    ActivityRecord Style
********************/
export const ActivityRecordWrap = styled.ul`
    height: 80vh;
    background-color: ${props => props.theme.backgroundColor};
`;