import React, {Fragment} from "react"
import ReactTransitionGroup from "react-addons-css-transition-group"

import PugjjigWrite from "../form/PugjjigWrite"

import {
    ModalCloseBtn,
    ModalWrap
} from "../../../style/globalStyles"

import {
    InsertModalWrap
} from "../style"

const InsertModal = ({
                         modalState,
                         closeModal,
                         stoId,
                         stoName,
                         stoAddress,
                         stoUrl,
                         editPugjjig
                     }) => (
    <Fragment>
        {
            modalState ?
                <ReactTransitionGroup
                    transitionName={'Modal-anim'}
                    transitionEnterTimeout={200}
                    transitionLeaveTimeout={200}
                >
                    <ModalWrap>
                        <InsertModalWrap>
                            <ModalCloseBtn onClick={() => closeModal("insertModalState")}/>
                            <PugjjigWrite
                                stoId={stoId}
                                stoName={stoName}
                                stoAddress={stoAddress}
                                stoUrl={stoUrl}
                                editPugjjig={editPugjjig}
                            />
                        </InsertModalWrap>
                    </ModalWrap>
                </ReactTransitionGroup>
                :
                <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200}
                                      transitionLeaveTimeout={200}/>
        }
    </Fragment>
);

export default InsertModal;