import styled, { createGlobalStyle, css } from 'styled-components';
import reset from './reset';
import { ShakeWeakly } from "./keyFrames";
import gyeonggiOTF from '../details/fonts/gyeonggi/Title_Medium.otf';
import gyeonggiWOFF from '../details/fonts/gyeonggi/Title_Medium.woff';
import CookieRunBold from '../details/fonts/cookieRun/CookieRunOTF-Bold.woff';
import CookieRunRegular from '../details/fonts/cookieRun/CookieRunOTF-Regular.woff';
import NanumSquareL from '../details/fonts/nanum/NanumSquareL.woff';
import NanumSquareR from '../details/fonts/nanum/NanumSquareR.woff';
import NanumSquareB from '../details/fonts/nanum/NanumSquareB.woff';

const PC_S = 1200;
const TABLET = 991;
const MOBILE_B = 768;
const MOBILE_M = 640;
const MOBILE_S = 480;

/*******************
    Global Style
********************/
export const GlobalStyle = createGlobalStyle`
    ${reset};

    @import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR:300,400&display=swap&subset=korean');

    @font-face {
        font-family: 'gyeonggi-ligth';
        font-style: normal;
        font-weight: 400;
        src: url('${gyeonggiOTF}');
        src: url('${gyeonggiWOFF}') format('woff2');
        font-weight: normal;
        font-style: normal;
    }

    @font-face { 
        font-family: 'CookieRunOTF-Bold'; 
        src: url('${CookieRunBold}') format('woff'); 
        font-weight: bold; 
        font-style: normal; 
    }
    @font-face { 
        font-family: 'CookieRunOTF-Regular'; 
        src: url('${CookieRunRegular}') format('woff'); 
        font-weight: 400; 
        font-style: normal; 
    }

    @font-face { 
        font-family: 'NanumSquare'; 
        src: url('${NanumSquareL}') format('woff'); 
        font-weight: 300; 
        font-style: normal;
    }
    @font-face { 
        font-family: 'NanumSquare'; 
        src: url('${NanumSquareR}') format('woff'); 
        font-weight: 400; 
        font-style: normal; 
    }
    @font-face { 
        font-family: 'NanumSquare'; 
        src: url('${NanumSquareB}') format('woff'); 
        font-weight: bold; 
        font-style: normal; 
    }

    html {
        background-color: ${props => props.theme.backgroundColor};
        color: ${props => props.theme.fontColor};
    }
    body, * { 
        font-family:'Noto Sans KR', sans-serif;
        font-weight: 300; 
        font-size: ${props => props.theme.fontSizeSm};
    }
    :before, :after, * {
        box-sizing: border-box;
    }
`;
export const CookieFontB = css`
    font-family: 'CookieRunOTF-Bold';
`;
export const CookieFontR = css`
    font-family: 'CookieRunOTF-Regular';
`;
export const ClearFix = css`
    &:after {
        display: block;
        content: '';
        clear: both;
    }
`;

/****************************************
    Animation Style
****************************************/
export const ModalActive = css`
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
export const WaringActive = css`
    &.Waring-anim-enter {
        opacity: 0.00;
        transform: translateY(15px);
        transition: all 0.2s;
    }
    &.Waring-anim-enter.Waring-anim-enter-active {
        transform: translateY(0px);
        opacity: 1;
    }
    &.Waring-anim-leave {
        transform: translateY(0px);
        opacity: 1;
        transition: all 0.2s;
    }
    &.Waring-anim-leave.Waring-anim-leave-active {
        transform: translateY(15px);
        opacity: 0.00;
    }
`;

/*******************
    Text Style
********************/
export const Ellipsis = css`
    display: block;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    overflow: hidden;
`;

/*******************
    Flex Style
********************/
export const FlexInit  = css`
    display: flex;
    flex-direction: column;

    position: relative;
`;
export const FlexCenter = css`
    margin: auto 0;
`;
export const FlexCol3  = css`
    display: flex;
    flex-direction: column;
    flex-basis: 33.333%;
    max-width: 33.333%;
    margin: 0.5%;

    @media only screen and (max-width: ${MOBILE_M}px) {
        display: block;
        margin: 0;
        flex-basis: 100%;
        max-width: 100%;
    }
`;
export const FlexCol4  = css`
    display: flex;
    flex-direction: column;
    flex-basis: 25%;
    max-width: 25%;
`;
export const Container = styled.div`
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
`;
export const FlexBottom = styled.div`
    display: flex;
    flex-direction: column;
    margin: auto 0 0;
`;

/*******************
    일반 페이지 상단 넓이 공통
********************/
export const CommonSpace = css`
    margin: 60px auto 60px;
`;

/*******************
    Botton Style
********************/
export const Btn = css`
    display: inline-block;
    padding: 0.375rem 0.75rem;
    margin-bottom: 0;
    font-size: 0.875rem;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    touch-action: manipulation;
    cursor: pointer;
    user-select: none;
    background-image: none;
    border: 0.0625rem solid transparent;
    border-radius: 0.25rem;
    color: #fff;
    
    &:hover {
        opacity: 0.7;
    }
    a {
        color: #fff;
    }
`;
export const EditBtn = styled.button`
    ${Btn};
    background-color: #ec971f;
`;
export const RemoveBtn = styled.button`
    ${Btn};
    background-color: #c9302c;
`;
export const WriteBtn = styled.button`
    ${Btn};
    background-color: #286090;
`;
export const DataNone  = styled.div`
    text-align: center;
    padding: 2.5rem 0;
    border: 1px solid #ddd;
    border-radius: 0.4rem;
    width: 100%;
`;

/*******************
    Form Style
********************/
export const FormCss = css`
    display: block;
    width: 100%;
    padding: .375rem .75rem;
    font-size: .875rem;
    line-height: 1.5;
    color: #5c6873;
    background-color: #fff;
    background-clip: padding-box;
`;
export const FormCssBasic = css`
    border: 1px solid #e4e7ea;
    border-radius: .25rem;
`;
export const Form = styled.form`
    padding 1.25rem 0;
`;
export const FormGroup = styled.div`
    position: relative;
    margin-bottom: 20px;
`;
export const Formlabel = styled.label`
    ${CookieFontR}
    
    display: inline-block;
    font-size: 1rem;
    margin-bottom: 5px;
`;
export const Input = styled.input.attrs({
    // required: true
})`
    ${FormCss};
    ${FormCssBasic};

    font-family: none;
`;
export const InputClean = styled.input.attrs({
    // required: true
})`
    ${FormCss};
    border: none;
    border-bottom: 1px solid #e4e7ea;

    font-family: none;
`;
export const InputWarning = styled.div`
    color: #e60023;
    font-size: 12px;
    position: absolute;
    bottom: -18px;

    ${props => props.active && css`
        animation: ${ShakeWeakly} 0.3s linear;
    `}
`;
export const Textarea = styled.textarea`
    ${FormCss};
    ${FormCssBasic};
    height: calc(9rem + 2px);
`;
export const SelectBox = styled.select`
    ${FormCss};
    ${FormCssBasic};
`;
export const SubmitBtn = styled.button`
    background-color: ${props => props.theme.themeColor};
    width: 100%;
    text-align: center;
    color: #fff;
    border-radius: 3px;
    font-size: 16px;
    padding: 8px 0;
    margin-top: 15px;
`;
export const NotPost = styled.div`
    ${CookieFontR}

    width: 100%;
    text-align: center;
    justify-content: center;
    padding: 100px 0;
    font-size: 26px;
    color: #A99798;

    @media only screen and (max-width: ${MOBILE_S}px) {
        font-size: 18px;
    }
`;
export const HiddenBtn = styled.button`
    outline: none;
`;

/*******************
    Waring Style
********************/
export const WaringCss = css`
    ${WaringActive}

    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    text-align: center;
    padding: 10px;
    color: #fff;
    font-weight: 600;
    font-size: 16px;
    z-index: 10;
`;
export const WaringWrap = styled.div`
    ${WaringCss}
    background-color: ${props => props.theme.themeColor};
`;
export const SuccessWrap = styled.div`
    ${WaringCss}
    background-color: #3897f0;
`;

/****************************************
    Active&Hover Style
****************************************/
export const ActiveList = css`
    transition: 0.2s cubic-bezier(0.91, 0.35, 0.21, 0.72);
    
    a.active,
    .active,
    &:hover {
        box-shadow: 0 0 0 5px rgba(0,0,0,0.03);
        border-radius: 5px;
    }
`;
export const WrapActive = styled.div`
    ${ActiveList};
`;

export const H20 = styled.div`
    margin-bottom: 1.25rem;
`;

export const LeftBox = styled.div`
    float: left;
`;
export const RightBox = styled.div`
    float: right;
`;
export const ListUl = styled.ul`
    ${ClearFix};
    li {
        padding: 0 0.625rem;
        float: left;
    }
    li:last-child {
        padding-right: 0;
    }
`;

/****************************************
    Modal Style
****************************************/
export const ModalWrap = styled.div`
    ${FlexInit}
    ${ModalActive}

    bottom: 0;
    left: 0;
    right: 0;
    top: 0;

    align-items: center;
    overflow-y: auto;
    position: fixed;
    z-index: 99999;
    background-color: rgba(0, 0, 0, 0.65);

    @media only screen and (max-width: ${MOBILE_S}px) {
        padding: 0 10px;
    }
`;
export const ModalBox = styled.div`
    ${FlexInit}
    ${FlexCenter}

    max-width: 600px;
    border-radius: 5px;
    height: 80vh; 
    padding: 10px;
`;
export const ModalCloseBtn = styled.button`
    position: absolute;
    right: -32px;
    top: 0;
    width: 32px;
    height: 32px;
    background-color: #f2f2f2;
    opacity: 1;
    border-radius: 0px 5px 5px 0;
    
    &:before,
    &:after {
        position: absolute;
        left: 15px;
        top: 6px;
        content: ' ';
        height: 20px;
        width: 2px;
        background-color: #333;
    }
    &:before {
        transform: rotate(45deg);
    }
    &:after {
        transform: rotate(-45deg);
    }

    @media only screen and (max-width: ${MOBILE_S}px) {
        right: 0;
        top: -32px;
        border-radius: 5px 5px 0 0;
    }
`;
export const Title = styled.h3`
    font-size: 28px;
    margin-bottom: 25px;
    font-weight: bold;
    color: #333;
    text-align: center
`;
export const Content = styled.div`
    
`;
export const Loding = styled.div`
    ${FlexInit}
    align-items: center;

    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0,0,0,0.5);
    border-radius: 4px;

    > svg {
        ${FlexInit}
        ${FlexCenter}

        width: 30px;
        height: 30px;
    }
`;

/* Main Style */
export const Main = styled.div`
    ${ClearFix};
    > div {
        float: left;
        height: 100%;
    }
    height: 100vh;
`;
export const InitTransition = css`
    transition: all 1s ease-in-out;
`;
export const MainList = styled.div`
    ${InitTransition}
    z-index: 3;
    position: relative;
    background: #fff;
    ${
        props => props.initSearch === true ?
        `
            width: 100%;
        `
        : 
        `
            width: 300px;
            @media only screen and (max-width: ${MOBILE_S}px) {
                width: 100%;
            }
        `
    }
    ${
        props => props.mobile === true ?
        `
            @media only screen and (max-width: ${MOBILE_S}px) {
                left: -100%;
            }
        `
        :
        `
            @media only screen and (max-width: ${MOBILE_S}px) {
                left: 0;
            }
        `
    }
`;
export const MainListContainer = styled.div`
    ${InitTransition}
    ${
        props => props.initSearch === true ?
        `
            width: 500px;
            margin: 90px auto 0;

            @media only screen and (max-width: ${MOBILE_S}px) {    
                margin: 10px auto 0;
                width: 100%;
            }
        `
        : 
        `
            width: 300px;
            margin: 0 auto;

            @media only screen and (max-width: ${MOBILE_S}px) {
                width: 100%;
                position: absolute;
            }
        `
    }
`;
export const MainMap = styled.div`
    width: calc(100% - 300px);
    border-left: 1px solid #e3e3e3;
    margin-left: 300px;
    position: absolute;
    z-index: 0;

    @media only screen and (max-width: ${MOBILE_S}px) {
        width: 100%;
        margin: 0;
    }
`;
export const MobileSearch = styled.span`
    @media only screen and (max-width: ${MOBILE_S}px) {
        position: fixed;
        bottom: 0;
        height: auto;
        width: 100%;
        text-align: center;
        padding: 10px 5px;
        display: block;

        > button {
            background-color: ${props => props.theme.themeColor};
            width: 100%;
            color: #fff;
            font-size: 16px;
            padding: 5px;
            border-radius: 5px;
        }
    }
`;

/*******************
    Image Style
********************/
export const ImgBox = styled.div`
    background-image: url(${props => props.bgImg});
    padding-bottom: ${props => props.bgSize};
    background-size: cover;
    background-position: center center;
`;
export const Image = styled.img`
    max-width: 100%;
    display: block;
    vertical-align: top;
    margin: 0.625rem auto;
`;

export const ProfileIamge = styled.div`
    background-image: url(${props => props.image});

    width: 40px;
    height: 40px;
    background-size: cover;
    border-radius: 50%;
    float: right;

    background-repeat: no-repeat;
    background-position: center center;
`;

/****************************************
    Mobile Style
****************************************/
export const MobilePadding = styled.div`
    padding: 0 10px;
`;