import styled from "styled-components";
import { FlexCol2, ScrollBar } from "../../style/globalStyles";

/*******************
    Common CSS
********************/
export const AlarmWrap = styled.div`
    position: fixed;
    bottom: 50px;
    left: 50px;
    z-index: 10;
`;
export const AlarmItemWrap = styled.div`
    transition: all .5s cubic-bezier(0.11, 0.26, 0.2, 1.02);

    position: fixed;
    top: 0;
    width: 18.75rem;
    height: 100%;
    background-color: #f3f3f3;
    padding: 15px;

    ${
        props => props.modalState ?
        `left: 0;`
        :
        `left: -300px;`
    }
`;
export const AlarmHead = styled.div`
    display: flex;
    margin-bottom: 20px;

    > div {
        ${FlexCol2}
        margin: auto;
    }
    > div:last-child button {
        text-align: right;
    }
`;
export const AlarmTitle = styled.div`
    font-size: 21px;
    font-weight: bold;
    color: #8a8a8a;
`;
export const Item = styled.div`
    border-radius: 5px;
    -webkit-box-shadow: 0px 0px 3px 0px rgba(0,0,0,0.1);
    -moz-box-shadow: 0px 0px 3px 0px rgba(0,0,0,0.1);
    box-shadow: 0px 0px 3px 0px rgba(0,0,0,0.1);
    margin-bottom: 10px;
`;
export const ItemHead = styled.div`
    display: flex;
    background-color: #ececec;
    padding: 2px 10px;
    border-radius: 5px;

    > div {
        ${FlexCol2}
        margin: auto;
    }
    > div:last-child {
        text-align: right;
    }
`;
export const ItemContent = styled.div`
    background-color: #fff;
    padding: 10px;
    border-radius: 0 0 5px 5px;
`;
export const ItemNone = styled.div`
    text-align: center;
    padding: 50px 0;
    font-size: 14px;
`;
export const ItemWrite = styled.div`
    font-weight: bold;
`;
export const ItemDate = styled.div`
    padding-right: 20px;
    position: relative;

    > button {
        position: absolute;
        right: 0;
        top: 0;    
    }
`;
export const ItemWrap = styled.div`
    ${ScrollBar}
    height: 100%;
    padding-bottom: 40px;
    margin-right: -10px;
`;