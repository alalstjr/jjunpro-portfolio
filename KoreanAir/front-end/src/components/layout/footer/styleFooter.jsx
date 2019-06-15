import styled from 'styled-components';
import { ClearFix } from "../../../style/globalStyles";

export const FooterBox = styled.footer`
    ${ClearFix};
    background-color: #2e4964;
    color: #fff;
    padding: 10px 0;
    
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
    
`;
export const FooterListUl = styled.div`
    
`;