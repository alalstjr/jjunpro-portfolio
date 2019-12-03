import styled, { css } from "styled-components"
import { ClearFix, InitTransition, FlexInit } from "../../style/globalStyles"
import { PugjjigItemWrap, PugjjigItem, ItemContent } from "../pugjjig/style"

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

    }
`;