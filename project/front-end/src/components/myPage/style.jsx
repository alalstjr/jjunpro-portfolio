import styled, { css } from "styled-components"
import { ClearFix } from "../../style/globalStyles"

/*******************
    Login Style
********************/
export const LoginWrap = styled.div`
    padding: 1.3125rem 2.3125rem;
`;
export const LoginLogo = styled.h1`
    text-align: center;
    font-size: 25px;
    font-weight: 600;
    padding: 10px 0 20px;
    color: ${props => props.theme.themeColorHover};
`;
export const LoginBtnCss = css`
    display: block;
    margin-bottom: 5px;
    height: 40px;
    line-height: 40px;
    box-sizing: border-box;
    font-size: 14px;
    font-weight: bold;
    text-align: center;
    width: 100%;
`;
export const LoginBtn = styled.button`
    ${LoginBtnCss}
    border: 1px solid #e3e3e3;
    color: #737373;
`;
export const SingUpBtn = styled.button`
    ${LoginBtnCss}
    background-color: ${props => props.theme.themeColorHover};
    color: #fff;
    margin-bottom: 0;
`;

/*******************
    Login Modal Style
********************/
export const ModalOverlay = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    background-color: rgba(0,0,0,0.6);
    z-index: 9;

    &.Modal-anim-enter {
        opacity: 0.00;
        transition: all 0.2s;
    }
    &.Modal-anim-enter.Modal-anim-enter-active {
        opacity: 1;
    }
    &.Modal-anim-leave {
        opacity: 1;
        transition: all 0.2s;
    }
    &.Modal-anim-leave.Modal-anim-leave-active {
        opacity: 0.00;
    }
`;
export const Modal = styled.div`
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 100%;
    max-width: 450px;
    border-radius: 3px;
    background-color: white;
    box-shadow: 0px 3px 6px rgba(0,0,0,0.16);
    z-index: 10;
    padding: 0px 20px;

    &.Modal-anim-enter {
        opacity: 0.00;
        transform: translate(-50%, -50%) scale(0.7);
        transition: all 0.2s;
    }
     &.Modal-anim-enter.Modal-anim-enter-active {
        opacity: 1;
        transform: translate(-50%, -50%) scale(1);
    }
    &.Modal-anim-leave {
        opacity: 1;
        transform: translate(-50%, -50%) scale(1);
        transition: all 0.2s;
    }  
    &.Modal-anim-leave.Modal-anim-leave-active {
        opacity: 0.00;
        transform: translate(-50%, -50%) scale(0.7);
    }
`;
export const TitleWrap = styled.div`
    ${ClearFix}
    margin-top: 15px;
`;
export const TitleLogo = styled.h1`
    color: ${props => props.theme.themeColorHover};
    float: left;
    font-size: 28px;
    font-weight: bold;
`;
export const Title = styled.h3`
    font-size: 18px;
    float: right;
    font-weight: bold;
    color: #3E4348;
`;
export const Content = styled.div`
    
`;