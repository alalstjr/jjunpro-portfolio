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
    padding: 0.9375rem;
`;
export const ModalWrapCSS = css`
    width: 100%; 
    max-width: 37.5rem;
    border-radius: 0.3125rem 0 0.3125rem 0.3125rem;
    padding: 0.625rem;
    height: 80vh;
`;

/*******************
    Form Style
********************/
export const TitleWrap = styled.div`
    padding-bottom: 0.625rem;
    margin-bottom: 0.625rem;
    border-bottom: 0.0625rem solid #e3e3e3;
`;
export const Title = styled.h2`
    font-size: 1.125rem;
    font-weight: 500;
`;
export const Form = styled.form`
    
`;
export const Content = styled.div`
    height: 70vh;
    overflow-y: scroll;
    padding-right: 0.1875rem;

    &::-webkit-scrollbar {
        background-color:#fff;
        width:1rem
    }

    &::-webkit-scrollbar-track {
        background-color:#fff
    }

    &::-webkit-scrollbar-thumb {
        background-color:#babac0;
        border-radius:1rem;
        border:0.25rem solid #fff
    }

    &::-webkit-scrollbar-button {
        display:none
    }
`;
export const RatingWrap = styled.div`
    
`;
export const Rating = styled.div`
    ${ClearFix}
    width: 9.375rem;
    margin: 0 auto;
    border: none;   
`;
export const RatingMessage = styled.div`
    font-weight: 300;
    text-align: center;
    font-size: 0.875rem;
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
        margin: 0.3125rem;
        display: inline-block;
        content: "";
        width: 1.25rem;
        height: 1.188rem;
        background: url(${require("../../static/images/icon/star.png")}) no-repeat center center;
    }
`;
export const TagWrap = styled.div`
    padding: 0.3125rem 0;
`;
export const TagPart = styled.span`
    padding: 0.125rem 0 0.125rem 0.625rem;
    background-color: ${props => props.theme.themeColor};
    color: #fff;
    font-weight: 400;
    margin-right: 0.3125rem;
    margin-bottom: 0.3125rem;
    display: inline-block;
    border-radius: 0.125rem;
`;
export const CloseBtn = styled.div`
    float: right;
    padding: 0 0.625rem;
    cursor: pointer;
`;

/*******************
    Item Style
********************/
export const PugjjigItemWrap = styled.ul`
    overflow-y: scroll;
    height: 100%;
    padding-right: 0.1875rem;

    &::-webkit-scrollbar {
        background-color: ${props => props.theme.backgroundColor};
        width:1rem
    }

    &::-webkit-scrollbar-track {
        background-color: ${props => props.theme.backgroundColor};
    }

    &::-webkit-scrollbar-thumb {
        background-color:#babac0;
        border-radius:1rem;
        border:0.25rem solid ${props => props.theme.backgroundColor};
    }

    &::-webkit-scrollbar-button {
        display:none
    }
`;
export const PugjjigItem = styled.li`
    padding: 0.625rem;
    background-color: #fff;
    border-radius: 0.1875rem;
    margin-bottom: 0.625rem;

    &:last-child {
        margin-bottom: 0;
    }
`;
export const ItemHead = styled.div`
    ${ClearFix}
    position: relative;
    margin-bottom: 0.625rem;
`;
export const ItemUserPhoto = styled.div`
    width: 2.375rem;
    height: 2.375rem;
    float: left;
`;
export const ItemRight = styled.div`
    float: left;
    width: calc(100% - 2.375rem);
    padding-left: 0.625rem;
`;
export const ItemUsername = styled.div`
    > a {
        font-size: 1rem;
        line-height: 1.25rem;
        font-weight: 300;
    }
`;
export const ItemUserInfo = styled.div`
    a > svg {
        margin-right: 0.1875rem;
    }
`;
export const ItemStar = styled.div`
    position: absolute;
    right: 0;
    top: 0;

    > svg {
        margin-left: 0.125rem;
    }
`;
export const ItemSubject = styled.div`
    font-size: 1.125rem;
    font-weight: 400;
    line-height: 1.625rem;
`;
export const ItemContent = styled.div`
    max-height: 5.625rem;
    overflow: hidden;
    margin-bottom: 0.625rem;
`;
export const ItemImgBox = styled.div`
    ${ClearFix}
    margin: 0.625rem 0;
`;
export const ItemTagWrap = styled.div`
`;
export const ItemLocalWrap = styled.div`
    a {
        display: inline-block;
        margin-right: 0.3125rem;
        margin-bottom: 0.1875rem;
    }
    span {
        color: ${props => props.theme.themeColor};
        font-weight: 400;
    }
`;
export const ItemStateWrap = styled.div`
`;
export const ItemState = styled.div`
    line-height: 0.625rem;
`;
export const ImgList = styled.div`
    width: calc(100% / 3 - 0.1875rem);
    margin-right: 0.25rem;
    float: left;
    height: 9.375rem;
    overflow: hidden;

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
    margin-right: 0.1875rem;
`;
export const ItemDetailWrap = styled.div`
    ${ClearFix}
    padding-top: 0.3125rem;
    color: #999;
`;
export const ItemDetail = styled.div`
    float: left;
`;
export const ItemDate = styled.div` 
    float: right;
`;
export const ItemBottom = styled.div`
    border-top: 0.0625rem solid #eee;
    padding-top: 0.3125rem;
    margin-top: 0.3125rem;
`;
export const ItemLikeBtn = styled.button.attrs({
    type: "button"
})`
    margin-right: 0.625rem;
    color: #999;
    > svg {
        float: left;
        margin-top: 0.125rem;
        margin-right: 0.3125rem;
    }

    a {
        color: #999;
    }
`;  
export const ItemLikeText = styled.div`
    float: left;
`;
export const ItemEditBtn = styled.button.attrs({
    type: "button"
})`
    float: right;
    margin-top: 0.1875rem;
`;
export const ItemEditModalBox = styled.div`
    ${FlexInit}
    ${FlexCenter}
    background-color: #fff;
    width: 25rem;
    max-width: 100%;
    border-radius: 0.9375rem;
`;
export const ItemEditModalBtn = styled.button.attrs({
    type: "button"
})`
    font-size: 1rem;
    font-weight: 500;
    height: 2.5rem;
    border-bottom: 0.0625rem solid #eee;

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
    
    padding-bottom: 2.938rem;
    background-color: #ffffff;
`;
export const InsertSubmitBtn = styled.button`
    background-color: ${props => props.theme.themeColor};
    width: 100%;
    text-align: center;
    color: #fff;
    border-radius: 0.1875rem;
    font-size: 1rem;
    padding: 0.75rem 0;
    margin-top: 0.9375rem;

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
    max-width: 37.5rem;
    margin: 0 auto 3.125rem;
    margin-top: 0.625rem;
    padding: 0.9375rem;
`;
export const ViewContent = styled.div`
    margin-bottom: 0.625rem;
`;

/*******************
    Comment Style
********************/
export const CommentNickname = styled.span`
    font-weight: 600;
    margin-right: 0.3125rem;
`;
export const CommentContent = styled.span`
    font-size: 0.875rem;
    font-weight: 300;
`;
export const CommentDate = styled.div`
    font-size: 0.75rem;
    color: #999;

    > button {
        margin-left: 0.5rem;
    }
`;
export const CommentWrap = styled.ul`
    margin-top: 0.1875rem;
`;
export const Comment = styled.li`
    padding: 0.1875rem 0;
`;
export const InputCommentWrap = styled.div`
    ${ClearFix}

    margin-top: 0.4375rem;
    height: 2.625rem;
    border: 0.0625rem solid #eee;
`;
export const InputComment = styled.textarea.attrs({
})`
    border: none;
    width: 100%;
    padding: 0.625rem;
    height: 2.5rem;
    width: calc(100% - 3.75rem);
    float: left;
    resize : none;
`;
export const CommentBtn = styled.button`
    width: 3.75rem;
    height: 2.5rem;
    float: left;

    font-size: 1rem;
    font-weight: 500;
    color: #3897f0;
`;