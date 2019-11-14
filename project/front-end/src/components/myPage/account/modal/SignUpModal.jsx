import React, { Fragment, Component } from 'react';

import ReactTransitionGroup from 'react-addons-css-transition-group';
import SignUp from "../form/SignUp"
import SignUpResult from "../container/SingUpResult"

import { 
    Title,
    TitleLogo,
    TitleWrap,
    ModalOverlay, 
    Modal
} from "../../style";

const SingUpModal = ({
    isOpen, 
    close, 
    warning, 
    warningText, 
    initWarning,
    warningSet,
    singUp,
    SingUpHandler
}) => (
    <Fragment>
        {
            isOpen ?
            <ReactTransitionGroup
                transitionName={'Modal-anim'}
                transitionEnterTimeout={200}
                transitionLeaveTimeout={200}
            >
            <ModalOverlay onClick={close} />
            <Modal>
                <TitleWrap>
                    <TitleLogo>푹찍</TitleLogo>
                    <Title>
                        {singUp ? "환영합니다." : "푹찍 회원가입"}
                    </Title>
                </TitleWrap>
                {
                    singUp ? 
                    <SignUpResult/>
                    :
                    <SignUp
                        warning={warning}
                        warningText={warningText}
                        initWarning={initWarning}
                        warningSet={warningSet}
                        SingUpHandler={SingUpHandler}
                    />
                }
            </Modal>
            </ReactTransitionGroup>
            :
            <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200} transitionLeaveTimeout={200} />
        }
    </Fragment>
);

export default SingUpModal;