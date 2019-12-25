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
        transform: translateY(0.9375rem);
        transition: all 0.2s;
    }
    &.Waring-anim-enter.Waring-anim-enter-active {
        transform: translateY(0);
        opacity: 1;
    }
    &.Waring-anim-leave {
        transform: translateY(0);
        opacity: 1;
        transition: all 0.2s;
    }
    &.Waring-anim-leave.Waring-anim-leave-active {
        transform: translateY(0.9375rem);
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
export const FlexCol2  = css`
    display: flex;
    flex-direction: column;
    flex-basis: 50%;
    max-width: 50%;
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
    margin: 3.75rem auto 3.75rem;
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
    border: 0.0625rem solid #ddd;
    border-radius: 0.4rem;
    width: 100%;
`;
export const SmallBtn  = styled.button`
    height: 1.875rem;
    background-color: ${props => props.theme.themeColor};
    color: #fff;
    padding: 0 1.25rem;
    border-radius: 0.1875rem;

    &:hover {
        background-color: ${props => props.theme.themeColorHover};
    }
`;

/*******************
    Form Style
********************/
export const FormCss = css`
    display: block;
    width: 100%;
    padding: .375rem .1rem;
    font-size: .875rem;
    line-height: 1.5;
    color: #5c6873;
    background-color: #fff;
    background-clip: padding-box;
    box-shadow: none;
`;
export const FormCssBasic = css`
    border: 0.0625rem solid #e4e7ea;
    border-radius: .25rem;
`;
export const Form = styled.form`
    padding 1.25rem 0;
`;
export const FormGroup = styled.div`
    position: relative;
    margin-bottom: 1.25rem;
`;
export const Formlabel = styled.label`
    ${CookieFontR}
    
    display: inline-block;
    font-size: 1rem;
    margin-bottom: 0.3125rem;
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
    border-bottom: 0.0625rem solid #e4e7ea;
    background-color: transparent;

    font-family: none;
`;
export const InputWarning = styled.div`
    color: #e60023;
    font-size: 0.75rem;
    position: absolute;
    bottom: -1.125rem;

    ${props => props.active && css`
        animation: ${ShakeWeakly} 0.3s linear;
    `}
`;
export const Textarea = styled.textarea`
    ${FormCss};
    ${FormCssBasic};
    height: calc(9rem + 0.125rem);
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
    border-radius: 0.1875rem;
    font-size: 1rem;
    padding: 0.5rem 0;
    margin-top: 0.9375rem;
`;
export const NotPost = styled.div`
    ${CookieFontR}

    width: 100%;
    text-align: center;
    justify-content: center;
    padding: 6.25rem 0;
    font-size: 1.625rem;
    color: #A99798;

    @media only screen and (max-width: ${MOBILE_S}px) {
        font-size: 1.125rem;
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
    padding: 0.625rem;
    color: #fff;
    font-weight: 600;
    font-size: 1rem;
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
        box-shadow: 0 0 0 0.3125rem rgba(0,0,0,0.03);
        border-radius: 0.3125rem;
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
        padding: 0 0.625rem;
    }
`;
export const ModalBox = styled.div`
    ${FlexInit}
    ${FlexCenter}

    max-width: 37.5rem;
    border-radius: 0.3125rem;
    height: 80vh; 
    padding: 0.625rem;
`;
export const ModalCloseBtn = styled.button`
    position: absolute;
    right: -2rem;
    top: 0;
    width: 2rem;
    height: 2rem;
    background-color: #f2f2f2;
    opacity: 1;
    border-radius: 0 0.3125rem 0.3125rem 0;
    
    &:before,
    &:after {
        position: absolute;
        left: 0.9375rem;
        top: 0.375rem;
        content: ' ';
        height: 1.25rem;
        width: 0.125rem;
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
        top: -2rem;
        border-radius: 0.3125rem 0.3125rem 0 0;
    }
`;
export const Title = styled.h3`
    font-size: 1.75rem;
    margin-bottom: 1.563rem;
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
    border-radius: 0.25rem;
`;
export const LodingBox = styled.div`
    ${FlexInit}
    ${FlexCenter}

    height: 5rem;
    width: 15rem;
    text-align: center;

    > svg {
        margin: 0 auto;
    }
`;
export const FileLoadText = styled.div`
    color: #fff;
    font-weight: bold;
    font-size: 16px;
    margin-top: 10px;
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
            width: 18.75rem;
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
            width: 31.25rem;
            margin: auto auto 0;

            @media only screen and (max-width: ${MOBILE_S}px) {    
                margin: 0.625rem auto 0;
                width: 100%;
            }
        `
        : 
        `
            width: 18.75rem;
            margin: 0 auto;

            @media only screen and (max-width: ${MOBILE_S}px) {
                width: 100%;
                position: absolute;
            }
        `
    }
`;
export const MainMap = styled.div`
    width: calc(100% - 18.75rem);
    border-left: 0.0625rem solid #e3e3e3;
    margin-left: 18.75rem;
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
        padding: 0.625rem 0.3125rem;
        display: block;

        > button {
            background-color: ${props => props.theme.themeColor};
            width: 100%;
            color: #fff;
            font-size: 1rem;
            padding: 0.3125rem;
            border-radius: 0.3125rem;
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
    background-image: url("${props => props.image}");

    width: 2.5rem;
    height: 2.5rem;
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
    padding: 0 0.625rem;
`;