import styled from 'styled-components';
import { 
    ClearFix, 
    Ellipsis
} from "../../../style/globalStyles";

export const SliderGap = styled.div`
    margin-left: -0.625rem;
    margin-right: -0.625rem;
`;
export const SliderWrap = styled.div`
    a {
        padding: 0.625rem;
        display:block
    }
`;
export const SliderBox = styled.div`
    background-color: #434343;
    color: #fff;
    padding: 0.9375rem 0.625rem;
`;
export const SlideTitle = styled.div`
    ${Ellipsis};
    font-size: 1rem ;
`;
export const SlideContent = styled.div`
    ${Ellipsis};
    color: #3cbede;
    font-weight: 400;
    font-size: 0.9375rem;
`;
export const NoSlider = styled.div`
    ${ClearFix};
    ${SliderWrap} {
        width: 20%;
        float: left;
    };
`;