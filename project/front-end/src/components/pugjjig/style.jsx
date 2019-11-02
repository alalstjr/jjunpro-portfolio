import styled, { css } from "styled-components"
import { ClearFix } from "../../style/globalStyles"

/*******************
    Common CSS
********************/
export const WrapPadding = css`
    padding: 15px;
`;

/*******************
    Form Style
********************/
export const TitleWrap = styled.div`
    ${WrapPadding}
    border-bottom: 1px solid #e3e3e3;
`;
export const Title = styled.h2`
    font-size: 18px;
    font-weight: 500;
`;
export const Form = styled.form`
    
`;
export const Content = styled.div`
    ${WrapPadding}
    height: 540px;
    overflow-y: scroll;
`;
export const RatingWrap = styled.div`
    
`;
export const Rating = styled.div`
    ${ClearFix}
    width: 150px;
    margin: 0 auto;
    border: none;   
`;
export const RatingMessage = styled.div`
    font-weight: 300;
    text-align: center;
    font-size: 14px;
`;
export const RatingPointInput = styled.input.attrs({
    type: "radio"
})`
    display: none;
    
    &:checked + label,
    &:checked ~ label {
        opacity: 1;
    }
`;
export const RatingPointLabel = styled.label`
    opacity: 0.2;
    float: right;
    transition: 0.3s all ease;
    cursor: pointer;

    &:hover,
    &:hover ~ label {
        opacity: 1;
        transform: scale(1.1);
    }
    &:before {
        margin: 5px;
        display: inline-block;
        content: "";
        width: 20px;
        height: 19px;
        background: url(${require("../../static/images/icon/star.png")}) no-repeat center center;
    }
`;
export const TagWrap = styled.div`
    padding: 8px;
    border: 1px solid #ddd;
    cursor: text;
`;
export const TagPart = styled.span`
    border-radius: 3;
    padding: 3px 0px 3px 10px;
    background-color: ${props => props.theme.themeColor};
    color: #fff;
    font-weight: bold;
    margin-right: 5px;
    margin-bottom: 5px;
    display: inline-block;
`;
export const CloseBtn = styled.div`
    float: right;
    padding: 0 10px;
    cursor: pointer;
`;