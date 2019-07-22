import styled from 'styled-components';

/*******************
    Member Modal Style
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
    margin-top: -230px;
    margin-left: -230px;
    width: 100%;
    max-width: 450px;
    border-radius: 10px;
    background-color: white;
    box-shadow: 0px 3px 6px rgba(0,0,0,0.16);
    z-index: 10;
    padding: 30px 40px;

    &.Modal-anim-enter {
        opacity: 0.00;
        transform: scale(0.7);
        transition: all 0.2s;
    }
     &.Modal-anim-enter.Modal-anim-enter-active {
        opacity: 1;
        transform: scale(1);
    }
    &.Modal-anim-leave {
        opacity: 1;
        transform: scale(1);
        transition: all 0.2s;
    }  
    &.Modal-anim-leave.Modal-anim-leave-active {
        opacity: 0.00;
        transform: scale(0.7);
    }
`;
export const Title = styled.h3`
    font-size: 28px;
    margin-bottom: 25px;
    font-weight: bold;
    color: #333;
    text-align: center
`;
export const Content = styled.div`
    
`;
