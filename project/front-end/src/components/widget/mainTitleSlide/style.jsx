import styled from 'styled-components';

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
    max-width: 75rem;
`;
export const SlideTitle = styled.div`
    font-size: ${props => props.titleFontSize};
`;
export const SlideContent = styled.div`
    font-size: ${props => props.contentFontSize};
`;
export const SlideLink = styled.div`
    a {
        padding: 0.625rem 1.25rem;
        border: 0.125rem solid #333;
        border-radius: 0.3125rem;
        font-size: 1rem;
        font-weight: 500;
    }   
`;