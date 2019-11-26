import styled, { css } from "styled-components"
import { 
    ClearFix,
    ModalActive,
    ModalInit,
    FlexInit,
    FlexCenter
} from "../../style/globalStyles"

/*******************
    Common CSS
********************/
export const WrapPadding = css`
    padding: 15px;
`;
export const ModalWrapCSS = css`
    width: 100%; 
    max-width: 600px;
    border-radius: 5px 0 5px 5px;
    padding: 10px;
    height: 80vh;
`;

/*******************
    Form Style
********************/
export const TitleWrap = styled.div`
    padding-bottom: 10px;
    margin-bottom: 10px;
    border-bottom: 1px solid #e3e3e3;
`;
export const Title = styled.h2`
    font-size: 18px;
    font-weight: 500;
`;
export const Form = styled.form`
    
`;
export const Content = styled.div`
    height: 70vh;
    overflow-y: scroll;
    padding-right: 3px;

    &::-webkit-scrollbar {
        background-color:#fff;
        width:16px
    }

    &::-webkit-scrollbar-track {
        background-color:#fff
    }

    &::-webkit-scrollbar-thumb {
        background-color:#babac0;
        border-radius:16px;
        border:4px solid #fff
    }

    &::-webkit-scrollbar-button {
        display:none
    }
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
    padding: 5px 0;
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
    padding-right: 3px;

    &::-webkit-scrollbar {
        background-color: ${props => props.theme.backgroundColor};
        width:16px
    }

    &::-webkit-scrollbar-track {
        background-color: ${props => props.theme.backgroundColor};
    }

    &::-webkit-scrollbar-thumb {
        background-color:#babac0;
        border-radius:16px;
        border:4px solid ${props => props.theme.backgroundColor};
    }

    &::-webkit-scrollbar-button {
        display:none
    }
`;
export const PugjjigItem = styled.li`
    padding: 10px;
    background-color: #fff;
    border-radius: 3px;
    margin-bottom: 10px;

    &:last-child {
        margin-bottom: 0;
    }
`;
export const ItemHead = styled.div`
    ${ClearFix}
    position: relative;
    margin-bottom: 10px;
`;
export const ItemUserPhoto = styled.div`
    width: 38px;
    height: 38px;
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

    > svg {
        margin-left: 2px;
    }
`;
export const ItemSubject = styled.div`
    font-size: 18px;
    font-weight: 400;
    line-height: 26px;
`;
export const ItemContent = styled.div`
    max-height: 90px;
    overflow: hidden;
    margin-bottom: 10px;
`;
export const ItemImgBox = styled.div`
    ${ClearFix}
    margin: 10px 0;
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
export const ItemEditBtn = styled.button.attrs({
    type: "button"
})`
    float: right;
    margin-top: 3px;
`;
export const ItemEditModalBox = styled.div`
    ${FlexInit}
    ${FlexCenter}
    background-color: #fff;
    width: 400px;
    max-width: 100%;
    border-radius: 15px;
`;
export const ItemEditModalBtn = styled.button.attrs({
    type: "button"
})`
    font-size: 16px;
    font-weight: 500;
    height: 40px;
    border-bottom: 1px solid #ddd;

    ${
        props => props.warring ?
        `color: #ed4956`
        :
        `color: #333333`
    }

    &:last-child {
        border-bottom: none;
    }
`;

/*******************
    List Style
********************/
export const ListModalWrap = styled.div`
    ${FlexInit}
    ${FlexCenter}
    ${ModalWrapCSS}

    height: 80vh;
    background-color: ${props => props.theme.backgroundColor};
`;

/*******************
    Insert Style
********************/
export const InsertModalWrap = styled.div`
    ${FlexInit}
    ${FlexCenter}
    ${ModalWrapCSS}
    
    padding-bottom: 47px;
    background-color: #ffffff;
`;
export const InsertSubmitBtn = styled.button`
    background-color: ${props => props.theme.themeColor};
    width: 100%;
    text-align: center;
    color: #fff;
    border-radius: 3px;
    font-size: 16px;
    padding: 12px 0;
    margin-top: 15px;

    position: absolute;
    left: 0;
    bottom: 0;
`;

/*******************
    View Style
********************/
export const ViewWrap = styled.div`
    background-color: ${props => props.theme.backgroundColor};
`;
export const ViewBox = styled.div`
    background-color: #ffffff;
    width: 100%;
    max-width: 600px;
    margin: 0 auto 50px;
    margin-top: 10px;
    padding: 15px;
`;
export const ViewContent = styled.div`
    margin-bottom: 10px;
`;