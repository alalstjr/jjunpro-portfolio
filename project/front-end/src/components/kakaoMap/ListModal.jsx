import React, { Fragment, Component } from 'react';
import ReactTransitionGroup from 'react-addons-css-transition-group';
import Item from "./list/item/Item"
import { 
    ModalCloseBtn,  
    ModalOverlay, 
    Modal
} from "../../style/globalStyles";

class InsertModal extends Component {

    componentWillReceiveProps(nextProps) {
        if ((nextProps.modalState !== this.props.modalState) && this.props.modalState == false) {
            const { stoId } = nextProps;
            this.props.pugjjigGet(stoId);
        }
    }

    render() {

        // props Init
        const { modalState, closeModal, pugjjig } = this.props;

        // Variables Init
        let pugjjigContent;
        let pugjjigList = [];

        // pugjjigList
        const pugjjigGet = (pugjjig) => {
            if(pugjjig.data !== undefined) {
                console.log(pugjjig);
                const data = pugjjig.data.stoUniList.map(university => (
                    <Item 
                        key = {university.id}
                        university = {university}
                    />
                ));

                for(let i = 0; i < data.length; i++) {
                    pugjjigList.push(data[i]);
                }

                return (
                    <Fragment>
                        {pugjjigList}
                    </Fragment>
                );
            } else {
                return (
                    <div>게시글이 존재하지 않습니다.</div>
                );
            }
        }

        // pugjjig Get List View
        pugjjigContent = pugjjigGet(pugjjig);

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
                    <Modal onClick={this.asd}>
                        <ModalCloseBtn onClick={() => closeModal("listModalState")}/>
                        {pugjjigContent}
                    </Modal>
                    </ReactTransitionGroup>
                    :
                    <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200} transitionLeaveTimeout={200} />
                }
            </Fragment>
        )
    }
}

export default InsertModal;