import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { USER_LONG_ID } from "../../../routes"

import { 
    tempUniversityList,
    deleteUniversityuniId, 
    getUniListStoreId,
    getUniListUniLike,
    getUniListUserId,
    getUniListSearch,
    UpdateUniLikeUniId
} from "../../../actions/PugjjigActions"

import Item from "./item/Item"
import InfiniteScroll from "react-infinite-scroller"

import ItemEditModal from "./item/ItemEditModal"
import InsertModal from "../modal/InsertModal"

import { NotPost } from "../../../style/globalStyles"
import { PugjjigItemWrap } from "../style"

class List extends Component {

    constructor(props){
        super(props);
    
        this.state = {
            insertModalState: false,
            selectModalState: false,
            editPugjjig: {},
            edit: false
        }
    }

    componentDidMount() {

        // Props Init
        const {
            keyword,
            classification,
            offsetCount,
            ifCateA,
            ifCateB,
            tempUniversityList
        } = this.props;

        // Search DTO 생성
        const searchDTO = {
            keyword,
            classification,
            offsetCount: 0,
            ifCateA,
            ifCateB
        };
        
        let result = true;
        tempUniversityList(result);

        // {stoId} 게시글 정보를 가져옵니다.
        this.handleAction(searchDTO);
    }

    componentWillReceiveProps(nextProps) {

        // Props Init
        const { 
            keyword,
            pugjjig_list,
            pugjjig_delete,
            tempUniversityList,
            reSearch
        } = this.props;

        if(nextProps.pugjjig_list !== pugjjig_list) {
            this.handleUpdate(nextProps.pugjjig_list);
        }

        if(nextProps.pugjjig_delete !== pugjjig_delete) {
            this.handleDeleteUpdate(nextProps.pugjjig_delete);
        }
        
        // keyword 달라지거나 카테고리 검색일경우 새로고침
        if(nextProps.keyword !== keyword || nextProps.reSearch !== reSearch) {
            // Search DTO 생성
            const searchDTO = {
                keyword: nextProps.keyword,
                classification:nextProps.classification,
                offsetCount: 0,
                ifCateA: nextProps.ifCateA,
                ifCateB: nextProps.ifCateB
            };

            let result = true;
            tempUniversityList(result);
            
            this.handleAction(searchDTO);
        }
    }

    // { keyword } 게시글 정보를 가져옵니다.
    handleAction = (searchDTO) => {
        const {
            getUniListStoreId,
            getUniListUniLike,
            getUniListUserId,
            getUniListSearch
        } = this.props;

        switch(searchDTO.classification) {
            case "storeId" :
                getUniListStoreId(searchDTO);
                break;

            case "uniLike" :
                getUniListUniLike(searchDTO);
                break;

            case "userId" :
                getUniListUserId(searchDTO);
                break;

            case "stoId" :
                getUniListStoreId(searchDTO);
                break;

            case "uniName" :
                getUniListSearch(searchDTO);
                break;

            case "uniTag" :
                getUniListSearch(searchDTO);
                break;

            default:
                break;
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

    handleLoad = (offsetCount) => {
        // Props Init
        const { 
            // search value
            keyword,
            classification,
            ifCateA,
            ifCateB,
            pugjjig_list
        } = this.props;

        // Search DTO 생성
        const searchDTO = {
            keyword,
            classification,
            offsetCount,
            ifCateA,
            ifCateB
        };
        
        if(pugjjig_list.data !== undefined) {
            if(pugjjig_list.data.length <= 0) {
                return false;
            }
        }

        this.handleAction(searchDTO);
    }

    handleUpdate = (postData) => {

        const { 
            tempUniversityList, 
            temp_pugjjig_list 
        } = this.props;
        
        tempUniversityList(false, temp_pugjjig_list, postData.data);
    }

    /*
     *  DELETE 메소드
     */
    handleDelete = () => {
        // Props Init
        const { deleteUniversityuniId } = this.props;
        
        // State Init
        const { editPugjjig } = this.state;

        deleteUniversityuniId(editPugjjig.id);
    }

    handleDeleteUpdate = (postPugjjig) => {
        // State Init
        const { temp_pugjjig_list } = this.props;
        
        this.closeModal("selectModalState");
        this.setState({
            pugjjig: temp_pugjjig_list.filter(pugjjig => pugjjig.id !== postPugjjig)
        });
    }

    /*
     *  String target, Object pugjjig
     *  모달상태를 true로 만들어주는 메소드 
     *  pugjjig 값이 전달될 경우 상태변화를 저장합니다.
     */
    openModal = (target, pugjjig) => {

        // 클릭한 Item의 정보를 담습니다.
        if(pugjjig) {
            // 클릭한 DATA의 정보가 로그인한 유저의 DATA인지 확인합니다.
            let edit = pugjjig.account_id === USER_LONG_ID() ? true : false;

            this.setState({
                editPugjjig: pugjjig,
                edit
            });
        }

        this.setState({
            insertModalState: false,
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
            temp_pugjjig_list,
            UpdateUniLikeUniId,
            pugjjig_like
        } = this.props;

        // State Init
        const {
            insertModalState,
            selectModalState,
            editPugjjig,
            edit
        } = this.state;

        // Variables Init
        let pugjjigContent;
        let pugjjigList = [];
        
        // pugjjigList
        const pugjjigGet = (pugjjig) => {
            if(pugjjig !== undefined && pugjjig.length > 0) {
                const data = pugjjig.map((pugjjig, index) => (
                    <Item 
                        key                = {index}
                        pugjjig            = {pugjjig}
                        UpdateUniLikeUniId = {UpdateUniLikeUniId}
                        selectModalState   = {selectModalState}
                        openModal          = {this.openModal}
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
        pugjjigContent = pugjjigGet(temp_pugjjig_list);

        return (
            <PugjjigItemWrap>
                {
                    temp_pugjjig_list.length > 0 ?
                    <InfiniteScroll
                    pageStart   = {0}
                    loadMore    = {this.handleLoad}
                    hasMore     = {true}
                    initialLoad = {false}
                    useWindow   = {true}
                    threshold   = {500}
                    >
                        {pugjjigContent}
                    </InfiniteScroll>
                    :
                    <NotPost>푹찍이 존재하지 않습니다.</NotPost>
                }
                {/* Item Edit Select modal */}
                <ItemEditModal
                    modalState   = {selectModalState}
                    closeModal   = {this.closeModal}
                    openModal    = {this.openModal}
                    handleDelete = {this.handleDelete}
                    edit         = {edit}
                />
                {/* Edit Modal */}
                <InsertModal
                    modalState   = {insertModalState}
                    closeModal   = {this.closeModal}
                    stoId        = {null}
                    stoAddress   = {null}
                    editPugjjig  = {editPugjjig}
                />
            </PugjjigItemWrap>
        )
    }
}

List.propTypes = {
    tempUniversityList: PropTypes.func.isRequired,
    getUniListStoreId: PropTypes.func.isRequired,
    getUniListUniLike: PropTypes.func.isRequired,
    getUniListUserId: PropTypes.func.isRequired,
    getUniListSearch: PropTypes.func.isRequired,
    UpdateUniLikeUniId: PropTypes.func.isRequired,
    deleteUniversityuniId: PropTypes.func.isRequired,
    temp_pugjjig_list: PropTypes.array.isRequired,
    pugjjig_list: PropTypes.object.isRequired,
    pugjjig_like: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired,
    pugjjig_delete: PropTypes.number.isRequired
}
  
const mapStateToProps = state => ({
    error: state.errors,
    temp_pugjjig_list: state.pugjjig.temp_pugjjig_list,
    pugjjig_list: state.pugjjig.pugjjig_list,
    pugjjig_like: state.pugjjig.pugjjig_like,
    pugjjig_delete: state.pugjjig.pugjjig_delete
});
  
export default connect(
    mapStateToProps, 
    { 
        tempUniversityList,
        getUniListStoreId,
        getUniListUniLike,
        getUniListUserId,
        getUniListSearch,
        UpdateUniLikeUniId,
        deleteUniversityuniId
    }
  )(List);