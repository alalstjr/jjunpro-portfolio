import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { USER_LONG_ID } from "../../../routes"

import ImageSlide from "../../widget/mainTitleSlide"
import NormalHeader from "../../layout/header/normal/NormalHeader"
import { pugjjigGetView, pugjjigLike } from "../../../actions/KakaoMapActions"

import ItemEditModal from "../list/item/ItemEditModal"
import InsertModal from "../modal/InsertModal"

import {
    ProfileIamge,
    NotPost
} from "../../../style/globalStyles"
import {
    ViewWrap,
    ViewBox,
    ItemHead,
    ItemRight,
    ItemUserPhoto,
    ItemUsername,
    ItemUserInfo,
    ItemStar,
    ItemSubject,
    ViewContent,
    ItemImgBox,
    ItemTagWrap,
    ItemStateWrap,
    ItemState,
    ItemDetailWrap,
    ItemDetail,
    ItemDate,
    ItemBottom,
    ItemTag,
    ItemLikeBtn,
    ItemLikeText,
    ItemEditBtn
} from "../style"

import SVG from "../../../static/svg/SVG"
import SVGEdit from "../../../static/svg/SVGEdit"

class PugjjigView extends Component {

    constructor(props){
        super(props);
    
        this.state = {
            pugjjig: [],
            insertModalState: false,
            selectModalState: false,
            editPugjjig: {},
            edit: false
        }
    }

    componentDidMount() {

        // Props Init
        const { 
            pugjjigGetView,
            match,
            history
        } = this.props;

        pugjjigGetView(match.params.id, history);
    }

    componentWillReceiveProps(nextProps) {

        // Props Init
        const { 
            pugjjig_view
        } = this.props;

        if(nextProps.pugjjig_view !== pugjjig_view) {
            this.setState({
                insertModalState: false,
                selectModalState: false
            });   
        }
    }

    handleLikeUpdate = (preData, postData) => {
        if(preData.uniLikeState !== postData.uniLikeState) {
            preData.uniLike = postData.uniLike;
            preData.uniLikeState = postData.uniLikeState;
        }
    } 

    /*
     *  DELETE 메소드
     */
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
            pugjjigLike, 
            pugjjig_view, 
            pugjjig_like,
        } = this.props;

        // State Init
        const { 
            insertModalState,
            selectModalState,
            editPugjjig,
            edit
        } = this.state;

        const pugjjig = pugjjig_view.data;
        
        if(pugjjig_like.data !==undefined) {
            this.handleLikeUpdate(pugjjig_view.data, pugjjig_like.data);
        }
        
        return (
            <ViewWrap>
                {
                    pugjjig_view.data !== undefined ?
                    <Fragment>
                        <NormalHeader/>
                        <ViewBox>
                            <ItemHead>
                                <ItemUserPhoto>
                                {
                                    pugjjig.photo === null ?
                                    <SVG name={"user"} width="38px" height="38px" color={"#E71D36"} />
                                    : 
                                    <ProfileIamge
                                        image = {require(`../../../../../data/file/thumbnail/pugjjig/${pugjjig.photo.fileThumbnail}`)}
                                    />
                                }
                                </ItemUserPhoto>
                                <ItemRight>
                                    <ItemUsername>{pugjjig.account_nickname}</ItemUsername>
                                    <ItemUserInfo>유저정보</ItemUserInfo>
                                </ItemRight>
                                {
                                    pugjjig.uniStar === 1 ?
                                    <ItemStar>
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    </ItemStar>
                                    :
                                    pugjjig.uniStar === 2 ?
                                    <ItemStar>
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    </ItemStar>
                                    :
                                    pugjjig.uniStar === 3 ?
                                    <ItemStar>
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    </ItemStar>
                                    :
                                    pugjjig.uniStar === 4 ?
                                    <ItemStar>
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    </ItemStar>
                                    :
                                    <ItemStar>
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                                    </ItemStar>
                                }
                            </ItemHead>
                            <ItemSubject>{pugjjig.uniSubject}</ItemSubject>
                            <ViewContent>
                                {pugjjig.uniContent}
                            </ViewContent>
                            <ItemImgBox>
                                <ImageSlide
                                    slideShow = {1}
                                    images = {pugjjig.files}
                                    thumbnail = {false}
                                />
                            </ItemImgBox>
                            <ItemTagWrap>
                                <ItemTag>#{pugjjig.uniName} 맛집</ItemTag>
                                {
                                    pugjjig.uniTag.map((tag, index) => (
                                        <ItemTag key={index}>#{tag}</ItemTag>
                                    ))
                                }
                            </ItemTagWrap>
                            <ItemDetailWrap>
                                <ItemStateWrap>
                                    <ItemDetail>
                                        좋아요 {pugjjig.uniLike}개
                                        댓글 0개
                                    </ItemDetail>
                                    <ItemDate>
                                        {pugjjig.modifiedDate.split("T")[0]}
                                    </ItemDate>
                                </ItemStateWrap>
                            </ItemDetailWrap>
                            <ItemBottom>
                                <ItemState>
                                    <ItemLikeBtn onClick={() => pugjjigLike(pugjjig.id)}>
                                        {
                                            pugjjig.uniLikeState == true ?  
                                            <SVG name={"heartCk"} width="14px" height="14px" color={"#d11d33"} />
                                            :
                                            <SVG name={"heart"} width="14px" height="14px" color={"#dddddd"} />
                                        }
                                        <ItemLikeText>
                                            좋아요
                                        </ItemLikeText>
                                    </ItemLikeBtn>

                                    <ItemLikeBtn>
                                        <SVG name={"comment"} width="14px" height="14px" color={"#dddddd"} />
                                        <ItemLikeText>
                                            댓글
                                        </ItemLikeText>
                                    </ItemLikeBtn>

                                    <ItemEditBtn onClick = {() => this.openModal("selectModalState", pugjjig)} >
                                        <SVGEdit width="16px" height="16px" color={"#333333"} />
                                    </ItemEditBtn>
                                </ItemState>
                            </ItemBottom>
                        </ViewBox>

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
                    </Fragment>
                    :
                    <NotPost>불러오는 중입니다...</NotPost>
                }
            </ViewWrap>
        )
    }
}

PugjjigView.propTypes = {
    pugjjigGetView: PropTypes.func.isRequired,
    pugjjigLike: PropTypes.func.isRequired,
    pugjjig_view: PropTypes.object.isRequired,
    pugjjig_like: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired
  }
  
  
  const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_view: state.pugjjig.pugjjig_view,
    pugjjig_like: state.pugjjig.pugjjig_like
  });
  
  export default connect(
    mapStateToProps, 
    { 
        pugjjigGetView,
        pugjjigLike
    }
  )(PugjjigView);