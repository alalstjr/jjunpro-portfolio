import React, {Fragment, Component} from "react"
import ReactTransitionGroup from "react-addons-css-transition-group"

import List from "../list/List"

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
            keyword,
            modalState,
            closeModal
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
                                    <List
                                        keyword={keyword}
                                        classification={"storeId"}
                                        modalState={modalState}
                                    />
                                </ListModalWrap>
                            </ModalWrap>
                        </ReactTransitionGroup>
                        :
                        <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200}
                                              transitionLeaveTimeout={200}/>
                }
            </Fragment>
        )
    }
}

export default ListModal;