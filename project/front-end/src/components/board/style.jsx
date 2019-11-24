import styled from 'styled-components';
import { 
    FlexCol4, 
    Ellipsis,
    Btn
} from "../../style/globalStyles";

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
    li {
        font-size: 1rem;
        padding: 0.625rem 1.25rem;
        display: inline-block;
        background-color: #ededed;
        margin-right: 0.3125rem;
        cursor: pointer;
    };
    li.active,
    li:hover {
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
export const ListLink = styled.div`
    > a {
        font-weight: 500;
        font-size: 1.125rem;
        color: #0080cc;
    }
`;

/*******************
    View Style
********************/
export const ContainerView = styled.div`
    width: 100%;
    max-width: 48.75rem;
    margin: 0 auto;
`;
export const ViewWrap = styled.div`
    padding: 1.875rem 0;
`;
export const TitleWrap = styled.div`
    ${Ellipsis};
    margin: 0.3125rem 0 1.875rem 0;
    display: block;
    color: rgb(59, 62, 67);
    letter-spacing: -0.0625rem;
    text-align: center;
    margin-top: 1.875rem;
    border-bottom: 0.0625rem solid #000;
    padding-bottom: 0.625rem;
`;
export const ViewTitle = styled.h2`
    font-weight: 500;
    line-height: 3.125rem;
    font-size: 1.75rem;
    margin-bottom: 10px;
`;
export const ViewWriter = styled.div`
    padding: 0.0625rem 0;
    font-size: 0.875rem;
`;
export const ViewDate = styled.div`
    padding: 0.0625rem 0;
    font-size: 0.875rem;
`;
export const ViewContent = styled.div`
    font-size: 1rem;
`;
export const EditWrap = styled.div`
    float: right;
    button {
        margin: 0 0.1875rem;
    }
`;

/*******************
    Paging Style
********************/
export const PagingWrap = styled.div`
    text-align: center;

    a,
    span {
        font-size: 16px;
        padding: 0 10px;
    }
    a:hover,
    span {
        font-weight: bold;
    }
`;