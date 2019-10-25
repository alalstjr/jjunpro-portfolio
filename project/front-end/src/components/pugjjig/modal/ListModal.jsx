import React, { Fragment, Component } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import ReactTransitionGroup from 'react-addons-css-transition-group';
import { pugjjigGetStoreList, pugjjigLike } from "../../../actions/KakaoMapActions"

import List from "../../../components/pugjjig/list/List"

import { 
    ModalCloseBtn,  
    ModalOverlay, 
    Modal
} from "../../../style/globalStyles";

class InsertModal extends Component {

    componentWillReceiveProps(nextProps) {
        if ((nextProps.modalState !== this.props.modalState) && this.props.modalState == false) {
            const { stoId } = nextProps;
            this.props.pugjjigGetStoreList(stoId);
        }
    }

    render() {

        // props Init
        const { 
            modalState, 
            closeModal, 
            pugjjigLike,
            pugjjig_store_list,
            pugjjig_like 
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
                    <Modal>
                        <ModalCloseBtn onClick={() => closeModal("listModalState")}/>
                        {
                            pugjjig_store_list.data !== undefined ?
                            <List
                                pugjjigLike = {pugjjigLike}
                                pugjjig_list = {pugjjig_store_list}
                                pugjjig_like = {pugjjig_like}
                            />
                            :
                            "푹찍이 존재하지 않습니다."
                        }
                    </Modal>
                    </ReactTransitionGroup>
                    :
                    <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200} transitionLeaveTimeout={200} />
                }
            </Fragment>
        )
    }
}

InsertModal.propTypes = {
    pugjjigLike: PropTypes.func.isRequired,
    pugjjig_store_list: PropTypes.object.isRequired,
    pugjjig_like: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired
}
  
const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_store_list: state.pugjjig.pugjjig_store_list,
    pugjjig_like: state.pugjjig.pugjjig_like
});
  
export default connect(
    mapStateToProps, 
    { 
        pugjjigGetStoreList,
        pugjjigLike 
    }
  )(InsertModal);