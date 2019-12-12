import React, { Fragment, Component } from 'react';

import ReactTransitionGroup from 'react-addons-css-transition-group';
import SignUp from "../form/SignUp"

import { 
    Title,
    TitleLogo,
    TitleWrap,
    ModalOverlay, 
    Modal
} from "../../style";

const SignUpModal = ({
    signUpModal, 
    closeModal, 
    warning, 
    warningText, 
    initWarning,
    warningSet,
    openModal
}) => (
    <Fragment>
        {
            signUpModal ?
            <ReactTransitionGroup
                transitionName={'Modal-anim'}
                transitionEnterTimeout={200}
                transitionLeaveTimeout={200}
            >
            <ModalOverlay onClick = {closeModal} />
            <Modal>
                <TitleWrap>
                    <Title>푹찍 회원가입</Title>
                </TitleWrap>
                <SignUp
                    warning     = {warning}
                    warningText = {warningText}
                    initWarning = {initWarning}
                    warningSet  = {warningSet}
                    closeModal  = {closeModal}
                    openModal   = {openModal}
                />
            </Modal>
            </ReactTransitionGroup>
            :
            <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200} transitionLeaveTimeout={200} />
        }
    </Fragment>
);

export default SignUpModal;