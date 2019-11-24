import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { pugjjigGetUserList, pugjjigLike } from "../../../../actions/KakaoMapActions"
import { pugjjigDelete } from "../../../../actions/PugjjigActions"
import Item from "../../../../components/pugjjig/list/item/Item"
import InfiniteScroll from "react-infinite-scroller"
import ItemEditModal from "../item/ItemEditModal"
import InsertModal from "../../modal/InsertModal"

import { PugjjigItemWrap } from "../../style"
import { NotPost } from "../../../../style/globalStyles"

class UserIdList extends Component {

    constructor(props){
        super(props);
    
        this.state = {
            pugjjig: [],
            editModalState: false,
            selectModalState: false,
            editPugjjig: {}
        }
    }

    componentDidMount() {

        // Props Init
        const { 
            pugjjigGetUserList
        } = this.props;

        // {userId} 게시글 정보를 가져옵니다.
        pugjjigGetUserList(null, 0);
    }

    componentWillReceiveProps(nextProps) {

        // Props Init
        const { 
            pugjjig_list,
            pugjjig_delete
        } = this.props;

        if(nextProps.pugjjig_list !== pugjjig_list) {
            this.handleUpdate(nextProps.pugjjig_list);
        }
        
        if(nextProps.pugjjig_delete !== pugjjig_delete) {
            this.handleDeleteUpdate(nextProps.pugjjig_delete);
        }
    }

    handleLikeUpdate = (preData, postData) => {
        
        preData.map(preData => {
            // id 가 일치하면 변경되는 값만 찾아서 변경합니다.
            if(preData.id === postData.id) {
                preData.uniLike      = postData.uniLike;
                preData.uniLikeState = postData.uniLikeState;
            }
        });
    } 

    handleLoad = (page) => {

        // Props Init
        const { 
            pugjjigGetUserList,
            pugjjig_list
        } = this.props;
        
        if(pugjjig_list.data !== undefined) {
            if(pugjjig_list.data.length <= 0) {
                return false;
            }
        }

        pugjjigGetUserList(null, page);
    }

    handleUpdate = (postData) => {

        // Props Init
        const {
            pugjjig
        } = this.state;
        
        this.setState({
            pugjjig: pugjjig.concat(postData.data)
        });
    }

    handleDelete = () => {
        // Props Init
        const { pugjjigDelete } = this.props;
        
        // State Init
        const { editPugjjig } = this.state;

        pugjjigDelete(editPugjjig.id);
    }

    handleDeleteUpdate = (postPugjjig) => {
        // State Init
        const { pugjjig } = this.state;
        
        this.closeModal("selectModalState");
        this.setState({
            pugjjig: pugjjig.filter(pugjjig => pugjjig.id !== postPugjjig)
        });
    }

    /*
     *  String target, Object pugjjig
     *  모달상태를 true로 만들어주는 메소드 
     *  pugjjig 값이 전달될 경우 상태변화를 저장합니다.
     */
    openModal = (target, pugjjig) => {
        if(pugjjig) {
            this.setState({
                editPugjjig: pugjjig
            });
        }

        this.setState({
            editModalState: false,
            selectModalState: false,
            [target]: true
        });
    }
    closeModal = (target) => {
        this.setState({
            [target]: false
        });
    }

    render() {
        // Props Init
        const { 
            pugjjigLike,
            pugjjig_like
        } = this.props;

        // State Init
        const {
            pugjjig,
            editModalState,
            selectModalState,
            editPugjjig
        } = this.state;

        // Variables Init
        let pugjjigContent;
        let pugjjigList = [];
        
        // pugjjigList
        const pugjjigGet = (pugjjig) => {
            if(pugjjig !== undefined && pugjjig.length > 0) {
                const data = pugjjig.map((pugjjig, index) => (
                    <Item 
                        key            = {index}
                        pugjjig        = {pugjjig}
                        pugjjigLike    = {pugjjigLike}
                        selectModalState = {selectModalState}
                        openModal      = {this.openModal}
                    />
                ));

                for(let i = 0; i < data.length; i++) {
                    pugjjigList.push(data[i]);
                }
                
                // 푹찍 Like 클릭시 Re rendering 여부 체크
                if(pugjjig_like.data !== undefined) {
                    this.handleLikeUpdate(pugjjig, pugjjig_like.data);
                }

                return (
                    <Fragment>
                        {pugjjigList}
                    </Fragment>
                );
            } else {
                return (
                    <NotPost>푹찍이 존재하지 않습니다.</NotPost>
                );
            }
        }

        // pugjjig Get List View
        pugjjigContent = pugjjigGet(pugjjig);

        return (
            <PugjjigItemWrap>
                <InfiniteScroll
                    pageStart    = {0}
                    loadMore     = {this.handleLoad}
                    hasMore      = {true}
                    initialLoad  = {false}
                    useWindow    = {false}
                    threshold    = {500}
                >
                    {pugjjigContent}
                </InfiniteScroll>
                {/* Item Edit Select modal */}
                <ItemEditModal
                    modalState   = {selectModalState}
                    closeModal   = {this.closeModal}
                    openModal    = {this.openModal}
                    handleDelete = {this.handleDelete}
                />
                {/* Edit Modal */}
                <InsertModal
                    modalState   = {editModalState}
                    closeModal   = {this.closeModal}
                    stoId        = {null}
                    stoAddress   = {null}
                    editPugjjig  = {editPugjjig}
                />
            </PugjjigItemWrap>
        )
    }
}

UserIdList.propTypes = {
    pugjjigGetUserList: PropTypes.func.isRequired,
    pugjjigLike: PropTypes.func.isRequired,
    pugjjigDelete: PropTypes.func.isRequired,
    pugjjig_list: PropTypes.object.isRequired,
    pugjjig_like: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired,
    pugjjig_delete: PropTypes.number.isRequired
}
  
const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_list: state.pugjjig.pugjjig_list,
    pugjjig_like: state.pugjjig.pugjjig_like,
    pugjjig_delete: state.pugjjig.pugjjig_delete
});
  
export default connect(
    mapStateToProps, 
    { 
        pugjjigGetUserList,
        pugjjigLike,
        pugjjigDelete
    }
  )(UserIdList);