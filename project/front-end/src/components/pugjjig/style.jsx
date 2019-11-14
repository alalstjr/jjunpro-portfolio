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

/*******************
    Item Style
********************/
export const PugjjigItemWrap = styled.ul`
    overflow-y: scroll;
    height: 100%;
`;
export const PugjjigItem = styled.li`
    padding: 10px;
    background-color: #fff;
    margin-bottom: 10px;
    border-radius: 3px;
`;
export const ItemHead = styled.div`
    ${ClearFix}
    position: relative;
    margin-bottom: 10px;
`;
export const ItemUserPhoto = styled.div`
    width: 38px;
    height: 38px;
    background-color: #333;
    border-radius: 50%;
    float: left;
`;
export const ItemRight = styled.div`
    float: left;
    width: calc(100% - 38px);
    padding-left: 10px;
`;
export const ItemUsername = styled.div`
    font-size: 16px;
    line-height: 20px;
    font-weight: 300;
`;
export const ItemUserInfo = styled.div`
`;
export const ItemStar = styled.div`
    position: absolute;
    right: 0px;
    top: 0px;
`;
export const ItemSubject = styled.div`
    font-size: 18px;
    font-weight: 400;
    line-height: 26px;
`;
export const ItemContent = styled.div`
    margin-bottom: 5px;
`;
export const ItemImgBox = styled.div`
    ${ClearFix}
`;
export const ItemTagWrap = styled.div`
`;
export const ItemStateWrap = styled.div`
`;
export const ItemState = styled.div`
    line-height: 10px;
`;
export const ImgList = styled.div`
    width: calc(100% / 3 - 3px);
    margin-right: 4px;
    float: left;

    > img {
        width: 100%;
    }

    &:last-child {
        margin-right: 0;
    }
`;
export const ItemTag = styled.div`
    color: #003569;
    display: inline-block;
    margin-right: 3px;
`;
export const ItemDetailWrap = styled.div`
    ${ClearFix}
    padding-top: 5px;
    color: #999;
`;
export const ItemDetail = styled.div`
    float: left;
`;
export const ItemDate = styled.div`
    float: right;
`;
export const ItemBottom = styled.div`
    border-top: 1px solid #eee;
    padding-top: 5px;
    margin-top: 5px;
`;
export const ItemLikeBtn = styled.button.attrs({
    type: "button"
})`
    margin-right: 10px;
    color: #999;
    > svg {
        float: left;
        margin-top: 2px;
        margin-right: 5px;
    }
`;  
export const ItemLikeText = styled.div`
    float: left;
`;