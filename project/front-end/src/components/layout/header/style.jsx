import styled from 'styled-components';
import { ClearFix } from "../../../style/globalStyles";

export const NormalHeaderWrap = styled.div`
    padding: 11px 0px;
    background-color: #fff;
    text-align: center;
    border-bottom: 1px solid #e2e2e2;

    > a {
        font-weight: 600;
        color: ${props => props.theme.themeColor};
        font-size: 25px;
        display: inline-block;
    }
`;