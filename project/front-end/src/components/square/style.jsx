import styled from "styled-components";
import { SERVER_FILE_URL } from "../../routes"
import { PugjjigItemWrap, PugjjigItem } from "../pugjjig/style"
import { CommonSpace, FlexInit, FlexCol3 } from "../../style/globalStyles";

const PC_S = 1200;
const TABLET = 991;
const MOBILE_B = 768;
const MOBILE_M = 640;
const MOBILE_S = 480;

export const BestWrap = styled.div`
    ${CommonSpace}
`;
export const Title = styled.h2`
    font-size: 24px;
    font-weight: 600;
    margin-bottom: 10px;  

    @media only screen and (max-width: ${MOBILE_M}px) {
        padding: 0 10px;
    }
`;
export const Content = styled.div`
    display: flex;

    @media only screen and (max-width: ${MOBILE_M}px) {
        display: block;
    }

    ${PugjjigItemWrap} {
        width: 100%;
        overflow-y: visible;

        @media only screen and (max-width: ${MOBILE_S}px) {
            padding: 0 10px;
        }
    }

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

    > a {
        ${FlexCol3}

        color: #fff;
        text-align: center;
        padding: 90px 0;
        position: relative;
        background-size: cover;
        background-position: center center;

        @media only screen and (max-width: ${MOBILE_M}px) {
            padding: 40px 0;
        }
    }
    > a::after {
        content: "";
        display: block;
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0,0,0,0.3);
    }
    > a:nth-child(1) {
        background-image:url("${SERVER_FILE_URL}front-images/search/food-01.jpg?folder=true&hl=ko&organizationId=true");
    }
    > a:nth-child(2) {
        background-image:url("${SERVER_FILE_URL}front-images/search/food-02.jpg?folder=true&hl=ko&organizationId=true");
    }
    > a:nth-child(3) {
        background-image:url("${SERVER_FILE_URL}front-images/search/food-03.jpg?folder=true&hl=ko&organizationId=true");
    }
`;
export const Group = styled.div`
    margin-bottom: 30px;
`;
export const ContentTitle = styled.div`
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 5px;
    position: relative;
    z-index: 1;
`;
export const ContentP = styled.div`
    font-size: 16px;
    position: relative;
    z-index: 1;
`;