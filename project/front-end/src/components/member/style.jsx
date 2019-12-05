import styled, { css } from "styled-components"
import { ClearFix, InitTransition } from "../../style/globalStyles"

const PC_S = 1200;
const TABLET = 991;
const MOBILE_B = 768;
const MOBILE_M = 640;
const MOBILE_S = 480;

/*******************
    Login Style
********************/
export const LoginWrap = styled.div`
    max-width: 300px;
    margin: 0 auto;
    padding: 1.3125rem 2.3125rem;
    margin-bottom: 20px;

    @media only screen and (max-width: ${MOBILE_S}px) {
        margin-bottom: 0px;
    }
`;
export const LoginLogo = styled.h1`
    ${InitTransition}
    text-align: center;
    font-weight: 600;
    padding: 10px 0 20px;
    color: ${props => props.theme.themeColorHover};

    ${
        props => props.initSearch ?
        "font-size: 45px;"
        :
        "font-size: 25px;"
    }
`;
export const LoginBtnCss = css`
    display: block;
    margin-bottom: 5px;
    height: 40px;
    line-height: 40px;
    box-sizing: border-box;
    font-size: 14px;
    font-weight: bold;
    text-align: center;
    width: 100%;
`;
export const LoginBtn = styled.button`
    ${LoginBtnCss}
    border: 1px solid #e3e3e3;
    color: #737373;
`;
export const SingUpBtn = styled.button`
    ${LoginBtnCss}
    background-color: ${props => props.theme.themeColorHover};
    color: #fff;
    margin-bottom: 0;

    > a {
        display: block;
        color: #fff;
        font-size: 14px;
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
    max-width: 450px;
    border-radius: 3px;
    background-color: white;
    box-shadow: 0px 3px 6px rgba(0,0,0,0.16);
    z-index: 10;
    padding: 0px 20px;

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
`;
export const TitleWrap = styled.div`
    ${ClearFix}
    margin-top: 15px;
`;
export const TitleLogo = styled.h1`
    color: ${props => props.theme.themeColorHover};
    float: left;
    font-size: 28px;
    font-weight: bold;
`;
export const Title = styled.h3`
    font-size: 18px;
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
    background-color: #fff;
    border-radius: 3px;
    margin: 60px auto 0;
    max-width: 935px;
    overflow: hidden;
    width: 100%;
    display: flex;
`;
export const MyPageLeft = styled.div`
    display: inherit;
    flex-direction: column;
    width: 230px;
`;
export const MyPageRight = styled.div`
    display: inherit;
    flex-direction: column;
    width: calc(100% - 230px);

    background-color: ${props => props.theme.backgroundColor};
`;
export const MyPageWhite = styled.div`
    margin-left: 15px;
    background-color: #fff;
`;
export const MyPageList = styled.li`
    cursor: pointer;
    font-size: 16px;
    padding: 16px 16px 16px 30px;
    border-left: 2px solid transparent;
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
        border-left-color: ${props => props.theme.themeColor};
        color: ${props => props.theme.themeColor};
        font-weight: 600;
    }

    &:hover {
        background-color: #fafafa;
        border-left-color: #dbdbdb;
    }
`;
export const GroupBox = styled.div`
    margin-bottom: 16px;
    display: flex;
`;
export const ProfileLabel = styled.div`
    flex-basis: 28%;
    display: flex;
    flex-direction: column;

    text-align: right;
    padding-right: 30px
    font-size: 16px;
    font-weight: 400;
    margin: auto;
`;
export const ProfileInput = styled.div`
    flex-basis: 72%;
    display: flex;
    flex-direction: column;

    padding: 0 30px;
    padding-right: 60px;
    position: relative;

    > #urlListInput {
        padding-right: 60px;
    }
`;
export const ProfileWrap = styled.div`
    margin-top: 30px;
`;
export const UrlList = styled.ul`
    padding: 0 15px;
    border: 1px solid #e2e2e2;
    margin-top: 10px;
`;
export const Url = styled.li`
    position: relative;
    padding: 5px 0;
`;
export const CloseBtn = styled.button.attrs({
    type: "button"
})`
    position: absolute;
    right: 0;
    top: 7px;
    width: 17px;
    height: 17px;

    &:before,
    &:after {
        position: absolute;
        left: 8px;
        top: 1px;
        content: ' ';
        height: 15px;
        width: 1px;
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
    width: 50px;
    height: 25px;
    border-radius: 3px;
    background-color: ${props => props.theme.themeColor};
    color: #fff;
    position: absolute;
    right: 63px;
    top: 2px;

    &:hover {
        background-color: ${props => props.theme.themeColorHover};
    }
`;
export const SaveBtn = styled.button.attrs({
    type: "submit"
})`
    background: ${props => props.theme.themeColor};
    color: #fff;
    padding: 7px;
    font-size: 14px;
    font-weight: 600;
    border-radius: 3px;

    &:hover {
        background-color: ${props => props.theme.themeColorHover};
    }
`;
export const ProfileId = styled.div`
    font-size: 18px;
    font-weight: 400;
    margin-bottom: 5px;
`;

/*******************
    ActivityRecord Style
********************/
export const ActivityRecordWrap = styled.ul`
    height: 80vh;
    background-color: ${props => props.theme.backgroundColor};
`;