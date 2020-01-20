import styled from "styled-components"
import { FlexInit, WaringActive, CommonSpace, FormCssBasic } from "../../style/globalStyles"
import { PugjjigItemWrap, PugjjigItem } from "../pugjjig/style"

const PC_S = 1200;
const TABLET = 991;
const MOBILE_B = 768;
const MOBILE_M = 640;
const MOBILE_S = 480;

/*******************
    Search Style
********************/
export const SearchWrap = styled.div`
    ${CommonSpace}

    width: 100%;
    max-width: 75rem;

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
        margin: 0.625rem auto 0;
        padding-left: 0.9375rem;
    }
`;

/*******************
    Category Style
********************/
export const CategoryWrap = styled.div`
    margin: 0.5%;

    > input {
        display: inline-block;
        width: 12.5rem;    
        margin-right: 0.625rem;
    }
`;
export const ScrollUpBtn = styled.button.attrs({
    type: "button"
})`
    ${WaringActive}
    
    position: fixed;
    right: 1.875rem;
    bottom: 1.875rem;
    
`;
export const SelectBox = styled.span`
    > select {
        ${FormCssBasic}

        margin-right: 0.625rem;
    }

    @media only screen and (max-width: ${MOBILE_M}px) {
        display: block;
        margin-bottom: 0.3125rem;
    }
`;
export const SearchTitle = styled.h2`
    text-align: left;
    font-size: 20px;
    margin-bottom: 20px;
    font-weight: 500;
    margin-left: 0.5rem;
`;