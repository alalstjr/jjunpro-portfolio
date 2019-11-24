import React, { Fragment, Component } from "react"
import ReactTransitionGroup from "react-addons-css-transition-group"

import StoreList from "../list/storeList/StoreList"

import { 
    ModalCloseBtn, 
    ModalWrap
} from "../../../style/globalStyles";

import {
    ListModalWrap
} from "../style"

class ListModal extends Component {

    render() {

        // props Init
        const { 
            modalState, 
            closeModal,
            stoId
        } = this.props;
        
        return (
            <Fragment>
                {
                    modalState ?
                    <ReactTransitionGroup
                        transitionName={'Modal-anim'}
                        transitionEnterTimeout={200}
                        transitionLeaveTimeout={200}
                    >
                    <ModalWrap>
                        <ListModalWrap>
                            <ModalCloseBtn onClick={() => closeModal("listModalState")}/>
                            <StoreList
                                modalState = {modalState}
                                stoId = {stoId}
                            />
                        </ListModalWrap>
                    </ModalWrap>
                    </ReactTransitionGroup>
                    :
                    <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200} transitionLeaveTimeout={200} />
                }
            </Fragment>
        )
    }
}

export default ListModal;