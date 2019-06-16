import styled from 'styled-components';
import { ClearFix } from "../../../style/globalStyles";

export const FooterBox = styled.footer`
    ${ClearFix};
    background-color: #2e4964;
    color: #fff;
    padding: 50px 0;
    
    a {
        color: #fff;
    }
`;

export const FooterLeft = styled.div`
    width: 75%;
    float:left;
`;
export const FooterRight = styled.div`
    width: 25%;
    float:left;
`;
export const FooterList = styled.div`
    flex-basis: 25%;
    display: flex;
    flex-direction: column;
`;
export const FooterListTitle = styled.div`
    font-size: 18px;
    margin-bottom: 10px;
    font-weight: 500;
`;
export const FooterListUl = styled.div`

    li a {
        display: block;
        padding: 2px 0;
    }
`;
export const FooterAbout = styled.div`
    margin-top: 30px;
`;
export const FooterAboutUl = styled.div`
    li {
        display: inline-block;
        padding-right: 15px;
        position: relative;
        margin-bottom: 3px;
    }
    li::after {
        display: inline-block;
        content: '|';
        position: absolute;
        right: 5px;
        top: 0px;
        font-size: 13px;
    }
    li:last-child {
        padding-right 0;
    }
    li:last-child::after {
        display: none;
    }
`;
export const Copyright = styled.div`
    letter-spacing: 1px;
`;