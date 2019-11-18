import React, { Fragment } from "react"
import ReactTransitionGroup from "react-addons-css-transition-group"

import PugjjigWrite from "../form/PugjjigWrite"

import { 
    ModalCloseBtn,  
    ModalOverlay, 
    Modal
} from "../../../style/globalStyles"

import {
    InsertModalWrap
} from "../style"

const InsertModal = ({
    modalState, 
    closeModal,
    stoId,
    stoAddress
}) => (
    <Fragment>
        {
            modalState ?
            <ReactTransitionGroup
                transitionName={'Modal-anim'}
                transitionEnterTimeout={200}
                transitionLeaveTimeout={200}
            >
            <ModalOverlay/>
            <InsertModalWrap>
                <ModalCloseBtn onClick={() => closeModal("insertModalState")}/>
                <PugjjigWrite
                    stoId = {stoId}
                    stoAddress = {stoAddress}
                />
            </InsertModalWrap>
            </ReactTransitionGroup>
            :
            <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200} transitionLeaveTimeout={200} />
        }
    </Fragment>
);

export default InsertModal;