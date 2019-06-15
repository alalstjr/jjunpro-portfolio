import styled, { createGlobalStyle, css } from 'styled-components';
import reset from './reset';

export const GlobalStyle = createGlobalStyle`
    ${reset};
    @import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR:100,300,400,500,700,900&display=swap&subset=korean');
    html {
        background-color: ${props => props.theme.backColor};
    }
    body { 
        font-family:'Noto Sans KR', sans-serif;
        font-weight:300; 
        font-size: ${props => props.theme.fontSizeSm};
    }
`;

export const ClearFix = css`
    &:after {
        display: block;
        content: '';
        clear: both;
    }
`;

export const Container = styled.div`
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
`;

export const FlexContainer  = styled.div`
    display: flex;
    flex-wrap: wrap;
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
    margin-bottom: 20px;
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
        padding: 0 10px;
        float: left;
    }
    li:last-child {
        padding-right: 0;
    }
`;