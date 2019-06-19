import styled from 'styled-components';
import { FlexContainer, FlexCol4, Ellipsis } from "../../style/globalStyles";

/*******************
    Header Style
********************/
export const BoardHeader = styled.div`
    padding: 3.75rem 0;
`;
export const BoardTitle = styled.h2`
    font-size: 2.375rem;
    font-weight: 500;
    margin-bottom: 0.3125rem;
`;
export const BoardIntro = styled.p`
    font-size: 1.25rem;
`;

/*******************
    Category Style
********************/
export const CateGoryWrap = styled.div`
    margin-bottom: 0.625rem;
`;
export const CateGoryUl = styled.ul`
    ${FlexContainer};
    li a {
        font-size: 1rem;
        padding: 0.625rem 1.25rem;
        display: inline-block;
        background-color: #ededed;
        margin-right: 0.3125rem;
    };
    li a.active,
    li a:hover {
        background-color: #434343;
        color: #fff;
    };
`;

/*******************
    List Style
********************/
export const ListBg = styled.div`
    background-color: #f9f9f9;
`;
export const ListWrap = styled.div`
    ${FlexContainer};
    margin-left: -0.9375rem;
    margin-right: -0.9375rem;
    padding: 1.875rem 0;
`;
export const ItemWrap = styled.div`
    ${FlexCol4};

    a {
        display: block;
        padding: 0.9375rem;
    }
`;
export const ItemBox = styled.div`
    margin: 0.3125rem 0;
`;
export const ItemTitle = styled.div`
    ${Ellipsis};
    font-size: 1rem;
    font-weight: 500;
    margin-bottom: 0.3125rem;
`;
export const ItemContent = styled.div`
    height: 2.5rem;
    overflow: hidden;
`;

/*******************
    Bottom Style
********************/
export const ListBtnWrap = styled.div`
    padding: 1.875rem 0;
    text-align: right;
`;

/*******************
    View Style
********************/
