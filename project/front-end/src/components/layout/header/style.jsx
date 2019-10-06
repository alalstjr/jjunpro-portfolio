import styled from 'styled-components';
import { ClearFix } from "../../../style/globalStyles";

export const Utilnav = styled.div`
    ${ClearFix};
    background-color: #434343;
    padding: 10px 0;
    
    li > button {
        color: #fff;    
    }
`;

export const LangLink = styled.a`
    color: #fff;
    margin-left: 10px;
`;