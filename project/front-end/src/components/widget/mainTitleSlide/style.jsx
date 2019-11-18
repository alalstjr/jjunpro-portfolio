import styled from 'styled-components';
import { ClearFix } from "../../../style/globalStyles";

export const SliderWrap = styled.div`
    position: relative;
`;
export const SliderImg = styled.div`
    background-image: url(${props => props.bgImg});
    padding-bottom: ${props => props.bgSize};
    background-size: ${props => props.bgState};
    background-position: center center;
    background-repeat: no-repeat;
    background-color: #231f1f;
`;
export const SliderContent = styled.div`
    position: absolute;
    left: 50%;
    top: 11%;
    transform: translate(-50%, -5%);
    width: 100%;
    max-width: 1200px;
`;
export const SlideTitle = styled.div`
    font-size: ${props => props.titleFontSize};
`;
export const SlideContent = styled.div`
    font-size: ${props => props.contentFontSize};
`;
export const SlideLink = styled.div`
    a {
        padding: 10px 20px;
        border: 2px solid #333;
        border-radius: 5px;
        font-size: 16px;
        font-weight: 500;
    }   
`;