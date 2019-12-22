import styled, { css } from "styled-components"
import { ClearFix, InitTransition, FlexInit, CookieFontB, CookieFontR } from "../../style/globalStyles"

const PC_S = 1200;
const TABLET = 991;
const MOBILE_B = 768;
const MOBILE_M = 640;
const MOBILE_S = 480;

/*******************
    List Style
********************/
export const ListWrap = styled.div`
    ${InitTransition}
    overflow-y: scroll;
    height: 100vh;
    position: fixed;
    top: 0;
    background-color: #fff;
    ::-webkit-scrollbar {
        display:none;
    }

    ${
        props => props.initSearch === true ?
        `
            left: 50%;
            padding-top: 31rem;
            width: 31.25rem;
            margin-left: -15.63rem;

            @media only screen and (max-width: ${MOBILE_S}px) {    
                width: 100%;
                padding-top: 25.5rem;
                margin-left: 0;
            }
        `
        :
        `
            left: 0;
            padding-top: 23.13rem;
            width: 18.75rem;

            &:after {
                display: block;
                position: absolute;
                text-align: center;
                width: 100%;
                content: "잠시만 기다려주세요.";
                position: absolute;
                top: 2rem;
                margin-top: 1.25rem;
                background: #fff;
                padding: 1.25rem 0;
                z-index: 2;
            }

            @media only screen and (max-width: ${MOBILE_S}px) {    
                width: 100%;
                padding-top: 23rem;
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
export const PagingBox = styled.div`
    position: relative;
`;
export const Pagination = styled.div`
    text-align: center;
    padding: 1.25rem 0;
    background: #fff;
    position: relative;
    z-index: 3;

    > button {
        width: 1.25rem;
        height: 1.25rem;
        margin: 0 0.1875rem;
    }

    > button#on {
        background-color: ${props => props.theme.themeColor};
        color: #fff;
    }

    > button:hover {
        background-color: ${props => props.theme.themeColorHover};
        color: #fff;
    }
`;

/*******************
    Item Style
********************/
export const Item = styled.button.attrs({
    type: "button"
})`
    ${ClearFix};
    padding: 0 0.9375rem;
    width: 100%;
    height: 3.125rem;
    line-height: 3.125rem;
    box-sizing: border-box;
    border-bottom: 0.0625rem solid #e3e3e3;
    cursor: pointer;
    display: block;
    z-index: 3;
    position: relative;
    background: #fff;
    &:hover {
        background-color: #fafafa;
    }
`;
export const ItemUniName = styled.span`
    float: left;
    color: #4c4c4c;
    font-size: 0.875rem;
    font-weight: 400;

    @media only screen and (max-width: ${MOBILE_S}px) {    
        font-size: 1rem;
        font-weight: 300;
    }
`;
export const ItemUniCount = styled.span`
    float: right;
    color: #a6a6a6;
    font-size: 0.75rem;
    font-weight: 400;

    @media only screen and (max-width: ${MOBILE_S}px) {    
        font-size: 0.875rem;
        font-weight: 300;
    }
`;

/*******************
    Search Style
********************/
export const SearchWrap = styled.div`
    padding: 0 0.3125rem;
    position: relative;
    z-index: 1;

    @media only screen and (max-width: ${MOBILE_S}px) {    
        padding: 0;
    }
`;
export const SearchTitle = styled.h2`
    ${CookieFontR}
    position: relative;
    padding: 0.5rem 0;
    line-height: 1.25rem;
    color: #292929;
    font-size: 1rem;

    @media only screen and (max-width: ${MOBILE_S}px) {    
        font-size: 1.125rem;
        margin-bottom: 0.3125rem;
    }
`;
export const SearchInputWrap = styled.div`
    position: relative;
`;
export const SearchInputText = styled.input.attrs(props => ({
    type: "text"
}))`
    padding: 0 2.188rem 0 0.625rem;
    width: 100%;
    height: 2.5rem;
    line-height: 2.5rem;
    box-sizing: border-box;
    border: 0.0625rem solid #e3e3e3;
    color: #4c4c4c;
    font-size: 0.875rem;
    background: #f2f2f2;
    border-radius: 0.1875rem;
`;
export const SearchInputBtn = styled.button.attrs({
    type: "button"
})`
    background: url(${require("../../static/images/icon/search.png")}) no-repeat center center;
    background-size: 1.25rem 1.313rem;
    text-indent: -9999px;
    width: 2.188rem;
    height: 2.5rem;
    position: absolute;
    right: 0;
    top: 0;
`;
export const RefreshBtn = styled.button.attrs({
    type: "button"
})`
    background-color: ${props => props.theme.themeColor};
    width: 100%;
    height: 2.5rem;
    line-height: 2.5rem;
    font-size: 0.875rem;
    border-radius: 0.1875rem;
    color: #fff;
    transition: 0.2s all ease;

    &:hover {
        background-color: ${props => props.theme.themeColorHover};
    }
`;

export const SwitchWrap = styled.div`
    position: relative;
    border: 0.0625rem solid #ddd;
    border-radius: 0.25rem;
    overflow: hidden;
    margin-bottom: 0.625rem;

    ${
        props => props.storeState === 3 ?
        `> button:nth-child(3) {
            color: #fff
        }`
        :
        props => props.storeState === 2 ?
        `> button:nth-child(2) {
            color: #fff
        }`
        :
        `> button:nth-child(1) {
            color: #fff
        }`
    }

    &:before {
        display: block;
        content: "";
        position: absolute;
        width: 33.333%;
        height: 2.625rem;
        background-color: ${props => props.theme.themeColor};
        top: 0;
        transition: 0.2s all;
        border-radius: 0.25rem;

        left: ${
            props => props.storeState !== false ?
            "33.333%"
            :
            "0"
        };

        left: ${
            props => props.storeState === 3 ?
            `66.666%`
            :
            props => props.storeState === 2 ?
            `33.333%`
            :
            `0`
        }
    }
`;
export const SwitchBtn = styled.button.attrs({
    type: "button"
})`
    padding: 0.25rem 0;
    position: relative;
    z-index: 1;
    width: 33.333%;
    height: 2.625rem;

    > svg {
        transition: all 0.2s ease-in-out;
        position: absolute;
        left: 50%;
        top: 50%;
        margin-top: -1rem;
        margin-left: -0.6875rem;
    }
    
    &:hover > div {
        visibility: visible;
        opacity: 1;
    }

    ${
        props => props.initSearch === true ?
        `&:hover > svg {
            left: 1.4rem;
            
            @media only screen and (max-width: ${MOBILE_S}px) {    
                opacity: 0;
            }
        }`
        :
        "&:hover > svg {visibility: hidden;opacity: 0;}"
    }
`;
export const SearchNotice = styled.div`
    text-align: center;
    padding: 1.25rem 0;
    background: #fff;
    z-index: 3;
    position: absolute;
    top: 35px;
    text-align: center;
    width: 100%;

    > button {
        display: none;
    }

    @media only screen and (max-width: ${MOBILE_S}px) {    
        font-size: 1.125rem;
    }
`;
export const SearchSet = styled.div`
    border-bottom: 0.0625rem solid #e3e3e3;
    padding: 0 0.3125rem;
    background-color: #fff;
    position: relative;
    z-index: 3;
`;

export const SearchSetWrapCSS = css`
    display: flex;
    flex-wrap: wrap;
    padding: 0.3125rem 0;

    > div:first-child {
        ${
            props => props.initSearch ? 
            null
            :
            `display: none`
        }

        max-width: 5rem;
        text-align: left;
    }

    > div {
        display: flex;
        flex-direction: column;

        font-size: 0.875rem;
        font-weight: 500;
        text-align: center;
        margin: auto 0;
    }
    
    > div input {
        display: none;
    }
    > div input + label {
        text-align: center;
        padding: 0.4375rem 0;
        cursor: pointer;
        font-size: 0.875rem;
        font-weight: 500;
        border-radius: 0.1875rem;
    }
    > div input:checked + label {
        background-color: ${props => props.theme.themeColor};
        color: #fff;
    }
`;
export const SearchSetTimeWrap = styled.div`
    ${SearchSetWrapCSS}

    > div {
        ${
            props => props.initSearch ? 
            `
                flex-basis: 27%;
                @media only screen and (max-width: ${MOBILE_S}px) {    
                    flex-basis: 25%;
                }
            `
            :
            `flex-basis: 33.333%;`
        }
    }
`;
export const SearchSetFoodWrap = styled.div`
    ${SearchSetWrapCSS}

    > div {
        ${
            props => props.initSearch ? 
            `
                flex-basis: 16.5%;
                @media only screen and (max-width: ${MOBILE_S}px) {    
                    
                }
            `
            :
            `flex-basis: 20%;`
        }
    }
`;

export const NoticeText = styled.div`
    ${CookieFontR}

    transition: all 0.2s ease-in-out;
    visibility: hidden;
    opacity: 0;
`;

/*******************
    UserBox Style
********************/
export const UserBox = styled.div`
    ${InitTransition}
    background-color: #fff;
    position: relative;
    z-index: 1;
    ${
        props => props.initSearch ? 
        `
            padding-top: 5.625rem;
            @media only screen and (max-width: ${MOBILE_S}px) {    
                padding-top: 0;
            }
        `
        :
        `padding-top: 0;`
    }

    @media only screen and (max-width: ${MOBILE_S}px) {    
        padding-left: 0.625rem;
        padding-right: 0.625rem;
    }
`;