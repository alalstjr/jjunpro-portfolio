import styled, { createGlobalStyle, css } from 'styled-components'
import reset from './reset'
import { ShakeWeakly } from "./keyFrames"
import gyeonggiOTF from '../details/fonts/gyeonggi/Title_Medium.otf'
import gyeonggiWOFF from '../details/fonts/gyeonggi/Title_Medium.woff'

/*******************
    Global Style
********************/
export const GlobalStyle = createGlobalStyle`
    ${reset};
    @import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR:100,300,400,500,700,900&display=swap&subset=korean');

    @font-face {
        font-family: 'gyeonggi-ligth';
        font-style: normal;
        font-weight: 400;
        src: url('${gyeonggiOTF}');
        src: url('${gyeonggiWOFF}') format('woff2');
        font-weight: normal;
        font-style: normal;
    }

    html {
        background-color: ${props => props.theme.backgroundColor};
        color: ${props => props.theme.fontColor};
    }
    body { 
        font-family:'Noto Sans KR', sans-serif;
        font-weight: 300; 
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
    margin-bottom: 2rem;
`;
export const Formlabel = styled.label`
    display: inline-block;
    margin-bottom: .5rem;
    font-size: 1rem;
`;
export const Input = styled.input.attrs({
    // required: true
})`
    ${FormCss};
    ${FormCssBasic};
`;
export const InputClean = styled.input.attrs({
    // required: true
})`
    ${FormCss};
    border: none;
    border-bottom: 1px solid #e4e7ea;
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
    padding: 12px 0;
    margin-top: 15px;
`;

/*******************
    Active&Hover Style
********************/
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
export const Image = styled.img`
    max-width: 100%;
    display: block;
    vertical-align: top;
    margin: 0.625rem auto;
`;