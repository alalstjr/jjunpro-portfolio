import React, { Fragment, Component } from 'react';
import PropTypes from "prop-types"
import { connect } from "react-redux"
import ReactTransitionGroup from 'react-addons-css-transition-group';
import Item from "./list/item/Item"
import { pugjjigLike } from "../../actions/KakaoMapActions"
import { 
    ModalCloseBtn,  
    ModalOverlay, 
    Modal
} from "../../style/globalStyles";

class InsertModal extends Component {

    componentWillReceiveProps(nextProps) {
        if ((nextProps.modalState !== this.props.modalState) && this.props.modalState == false) {
            const { stoId } = nextProps;
            this.props.pugjjigGetStoreList(stoId);
        }
    }

    handleLikeUpdate = (preData, postData) => {
        let props;
        preData.map(preData => {
            // id 가 일치하면 변경되는 값만 찾아서 변경합니다.
            if(preData.key*1 === postData.id) {
               props = preData.props;
               props.university.uniLike = postData.uniLike;
               props.university.uniLikeState = postData.uniLikeState;
            }
        });
    }

    render() {

        // props Init
        const { modalState, closeModal, pugjjig, pugjjigLike, pugjjig_like } = this.props;

        // Variables Init
        let pugjjigContent;
        let pugjjigList = [];
        
        // pugjjigList
        const pugjjigGet = (pugjjig) => {
            if(pugjjig.data !== undefined) {
                const data = pugjjig.data.stoUniList.map(university => (
                    <Item 
                        key = {university.id}
                        university = {university}
                        pugjjigLike = {pugjjigLike}
                    />
                ));

                for(let i = 0; i < data.length; i++) {
                    pugjjigList.push(data[i]);
                }

                // 푹찍 Like 클릭시 Re rendering 여부 체크
                if(pugjjig_like.data !== undefined) {
                    this.handleLikeUpdate(pugjjigList, pugjjig_like.data);
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

InsertModal.propTypes = {
    pugjjigLike: PropTypes.func.isRequired,
    pugjjig_like: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired
}
  
const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_like: state.pugjjig.pugjjig_like
});
  
export default connect(
    mapStateToProps, 
    { pugjjigLike }
  )(InsertModal);