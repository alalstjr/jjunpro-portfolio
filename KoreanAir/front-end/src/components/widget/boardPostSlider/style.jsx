import styled from 'styled-components';
import { ClearFix } from "../../../style/globalStyles";

export const SliderWrap = styled.div`
    a {
        padding: 10px;
        display:block
    }
`;
export const SliderBox = styled.div`
    background-color: #434343;
    color: #fff;
    padding: 15px 10px;
`;
export const SlideTitle = styled.div`
    font-size: 16px;
`;
export const SlideContent = styled.div`
    color: #3cbede;
    font-weight: 400;
    font-size: 15px;
`;
export const NoSlider = styled.div`
    ${ClearFix};
    ${SliderWrap} {
        width: 20%;
        float: left;
    };
`;