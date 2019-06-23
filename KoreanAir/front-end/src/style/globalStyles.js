import styled, { createGlobalStyle, css } from 'styled-components';
import reset from './reset';

/*******************
    Global Style
********************/
export const GlobalStyle = createGlobalStyle`
    ${reset};
    @import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR:100,300,400,500,700,900&display=swap&subset=korean');
    html {
        background-color: ${props => props.theme.backgroundColor};
        color: ${props => props.theme.fontColor};
    }
    body { 
        font-family:'Noto Sans KR', sans-serif;
        font-weight:300; 
        font-size: ${props => props.theme.fontSizeSm};
    }
    :before, :after, * {
        box-sizing: border-box;
    }
`;
export const ClearFix = css`
    &:after {
        display: block;
        content: '';
        clear: both;
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
export const FlexContainer  = css`
    display: flex;
    flex-wrap: wrap;
`;
export const FlexCol4  = css`
    display: flex;
    flex-direction: column;
    flex-basis: 25%;
    width: 25%;
`;

export const Container = styled.div`
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
`;


/*******************
    Botton Style
********************/
export const Btn  = styled.button`
    border: 0.0625rem solid #ddd;
    padding: 0.3125rem 0.9375rem;
    background-color: #f2f2f2;
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
    border: 1px solid #e4e7ea;
    border-radius: .25rem;
`;
export const Form = styled.form`
    padding 1.25rem 0;
`;
export const FormGroup = styled.div`
    margin-bottom: 1rem;
`;
export const Formlabel = styled.label`
    display: inline-block;
    margin-bottom: .5rem;
    font-size: 1rem;
`;
export const Input = styled.input.attrs({
    required: true
})`
    ${FormCss};
`;
export const Textarea = styled.textarea`
    ${FormCss};
    height: calc(9rem + 2px);
`;
export const SelectBox = styled.select`
    ${FormCss};
`;


// export const BoxGap = css`
//     padding-left: 10px;
//     padding-right: 10px;
// `;
// export const UpDownGap = css`
//     padding-top: 6px;
//     padding-bottom: 6px;
// `;
// export const PostBtn = styled.button`
//     cursor: ${props => props.cursorState};
//     background-color: ${props => props.postColor ? props.theme.PostBtnOn : props.theme.PostBtnOff};
//     border-radius: ${props => props.theme.borderRadius};
//     text-align: center;
//     color: #fff;
//     font-weight: bold;
//     font-size: 16px;
//     width: 100%;
//     padding: 6px;
//     transition: 0.6s all;
//     outline: 0;
//     border: 0;
//     ${props => props.postState ? `&:hover{ background-color : ${props.theme.PostBtnHover} }` : ''}
// `;
// export const UserName = styled.div`
//     font-weight: bold;
//     margin-right: 10px;
//     display: inline-block;
// `;

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

/* Main Style */
export const MainSection = styled.section`
    margin-bottom: 3.75rem;
`;

/* Img */
export const ImgBox = styled.div`
    background-image: url(${props => props.bgImg});
    padding-bottom: ${props => props.bgSize};
    background-size: cover;
    background-position: center center;
`;