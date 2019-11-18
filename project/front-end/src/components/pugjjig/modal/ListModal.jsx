import React, { Fragment, Component } from "react"
import ReactTransitionGroup from "react-addons-css-transition-group"

import List from "../../../components/pugjjig/list/List"

import { 
    ModalCloseBtn,  
    ModalOverlay
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
                    <ModalOverlay/>
                    <ListModalWrap>
                        <ModalCloseBtn onClick={() => closeModal("listModalState")}/>
                        <List
                            modalState = {modalState}
                            stoId = {stoId}
                        />
                    </ListModalWrap>
                    </ReactTransitionGroup>
                    :
                    <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200} transitionLeaveTimeout={200} />
                }
            </Fragment>
        )
    }
}

export default ListModal;