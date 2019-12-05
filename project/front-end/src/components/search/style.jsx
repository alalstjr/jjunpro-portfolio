import styled, { css } from "styled-components"
import { ClearFix, InitTransition, FlexInit } from "../../style/globalStyles"
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
    margin: 60px auto 0;

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
    }

    @media only screen and (max-width: ${MOBILE_M}px) {
        margin: 10px auto 0;
    }
`;