import styled, { css } from "styled-components"
import { ClearFix } from "../../style/globalStyles"

/*******************
    List Style
********************/
export const ListWrap = styled.div`
    border-top: 0.0625rem solid #e3e3e3;
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
    &:hover {
        background-color: #fafafa;
    }
`;
export const ItemUniName = styled.span`
    float: left;
    color: #4c4c4c;
    font-size: 0.875rem;
`;
export const ItemUniCount = styled.span`
    float: right;
    color: #a6a6a6;
    font-size: 0.75rem;
`;

/*******************
    Search Style
********************/
export const SearchWrap = styled.div`
    padding: 0.3125rem;
`;
export const SearchTitle = styled.h2`
    padding: 0.9375rem 0;
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