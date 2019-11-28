import styled, { css } from "styled-components"
import { ClearFix, InitTransition, FlexInit } from "../../style/globalStyles"

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
            padding-top: 490px;
            width: 500px;
            margin-left: -250px;
        `
        :
        `
            left: 0;
            padding-top: 334px;
            width: 300px;
        `
    }
`;
export const PagingBox = styled.div`
    position: relative;
`;
export const Pagination = styled.div`
    text-align: center;
    padding: 20px 0;

    > button {
        width: 20px;
        height: 20px;
        margin: 0 3px;
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
    &:hover {
        background-color: #fafafa;
    }
`;
export const ItemUniName = styled.span`
    float: left;
    color: #4c4c4c;
    font-size: 0.875rem;
    font-weight: 400;
`;
export const ItemUniCount = styled.span`
    float: right;
    color: #a6a6a6;
    font-size: 0.75rem;
    font-weight: 400;
`;

/*******************
    Search Style
********************/
export const SearchWrap = styled.div`
    padding: 0 0.3125rem;
    position: relative;
    z-index: 1;
`;
export const SearchTitle = styled.h2`
    position: relative;
    padding: 8px 0;
    line-height: 1.25rem;
    color: #292929;
    font-size: 1rem;
    font-weight: 600;
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
    border: 1px solid #ddd;
    border-radius: 4px;
    overflow: hidden;
    margin-bottom: 10px;

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
        height: 42px;
        background-color: ${props => props.theme.themeColor};
        top: 0;
        transition: 0.2s all ease;
        border-radius: 4px;

        left: ${
            props => props.storeState !== false ?
            "33.333%"
            :
            "0px"
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
    padding: 4px 0px;
    position: relative;
    z-index: 1;
    width: 33.333%;
    height: 42px;

    > svg {
        transition: all 0.2s ease-in-out;
        position: absolute;
        left: 50%;
        top: 50%;
        margin-top: -14px;
        margin-left: -11px;
    }
    
    &:hover > div {
        visibility: visible;
        opacity: 1;
    }

    ${
        props => props.initSearch === true ?
        "&:hover > svg {left: 30px;}"
        :
        "&:hover > svg {visibility: hidden;opacity: 0;}"
    }
`;
export const SearchNotice = styled.div`
    text-align: center;
    padding: 20px 0;
`;
export const SearchSet = styled.div`
    border-bottom: 0.0625rem solid #e3e3e3;
    padding: 0 0.3125rem;
`;

export const SearchSetWrapCSS = css`
    display: flex;
    flex-wrap: wrap;
    padding: 10px 0;

    > div:first-child {
        ${
            props => props.initSearch ? 
            null
            :
            `display: none`
        }

        max-width: 80px;
        text-align: left;
    }

    > div {
        display: flex;
        flex-direction: column;

        font-size: 14px;
        font-weight: 500;
        text-align: center;
        margin: auto 0;
    }
    
    > div input {
        display: none;
    }
    > div input + label {
        text-align: center;
        padding: 7px 0px;
        cursor: pointer;
        font-size: 14px;
        font-weight: 500;
        border-radius: 3px;
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
            `flex-basis: 27%;`
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
            `flex-basis: 16.7%;`
            :
            `flex-basis: 20%;`
        }
    }
`;

export const NoticeText = styled.div`
    transition: all 0.2s ease-in-out;
    visibility: hidden;
    opacity: 0;
    font-weight: 600;
`;

/*******************
    UserBox Style
********************/
export const UserBox = styled.div`
    background-color: #fff;
    position: relative;
    z-index: 1;
`;