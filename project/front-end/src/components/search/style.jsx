import styled, { css } from "styled-components"
import { ClearFix, InitTransition, FlexInit, WaringActive } from "../../style/globalStyles"
import { PugjjigItemWrap, PugjjigItem, ItemContent } from "../pugjjig/style"

const PC_S = 1200;
const TABLET = 991;
const MOBILE_B = 768;
const MOBILE_M = 640;
const MOBILE_S = 480;

/*******************
    Search Style
********************/
export const SearchWrap = styled.div`
    width: 100%;
    max-width: 1200px;
    margin: 60px auto;

    ${PugjjigItemWrap} > div {
        display: flex;
        flex-wrap: wrap;
    }

    ${PugjjigItem} {
        ${FlexInit}
        
        margin: 0.5%;
        flex-basis: 32.333%;
        max-width: 32.333%;

        @media only screen and (max-width: ${MOBILE_M}px) {
            margin: 1%;
            flex-basis: 48%;
            max-width: 48%;
        }
        @media only screen and (max-width: ${MOBILE_S}px) {
            margin-left: 0;
            margin-right: 0;
            flex-basis: 100%;
            max-width: 100%;
        }
    }

    @media only screen and (max-width: ${MOBILE_M}px) {
        margin: 10px auto 0;
        padding-left: 15px;
    }
`;

/*******************
    Category Style
********************/
export const CategoryWrap = styled.div`
    margin: 0.5%;

    > select {
        height: 30px;
        margin-right: 10px;
    }

    > button {
        height: 30px;
        background-color: ${props => props.theme.themeColor};
        color: #fff;
        padding: 0 20px;
        border-radius: 3px;
    }
    > button:hover {
        background-color: ${props => props.theme.themeColorHover};
    }
`;

export const ScrollUpBtn = styled.button.attrs({
    type: "button"
})`
    ${WaringActive}
    
    position: fixed;
    right: 30px;
    bottom: 30px;
    
`;