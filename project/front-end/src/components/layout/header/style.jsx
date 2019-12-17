import styled from 'styled-components';
import { ClearFix, CookieFontB } from "../../../style/globalStyles";

export const NormalHeaderWrap = styled.div`
    padding: 0.6875rem 0;
    background-color: #fff;
    text-align: center;
    border-bottom: 0.0625rem solid #e2e2e2;

    > a {
        ${CookieFontB}
        font-weight: 600;
        color: ${props => props.theme.themeColor};
        font-size: 1.563rem;
        display: inline-block;
    }
`;