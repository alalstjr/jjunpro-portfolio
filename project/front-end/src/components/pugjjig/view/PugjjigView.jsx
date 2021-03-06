import React, {Component, Fragment} from "react";
import {Link} from "react-router-dom";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {USER_LONG_ID, SERVER_FILE_URL} from "../../../routes";

import ImageSlide from "../../widget/mainTitleSlide";
import NormalHeader from "../../layout/header/normal/NormalHeader";
import {
    getCommentListUniId,
    deleteUniversityuniId,
    deleteCommentId,
    getUniversityUniId,
    UpdateUniLikeUniId
} from "../../../actions/PugjjigActions";

import ItemEditModal from "../list/item/ItemEditModal";
import InsertModal from "../modal/InsertModal";
import CommentWrite from "../form/CommentWrite";

import { setAtmosphere, setPrice } from "../../../util/UniversityUtil";

import {
    ProfileIamge,
    NotPost,
    HiddenBtn,
    MobilePadding
} from "../../../style/globalStyles";
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
    ItemEditBtn,
    ItemLocalWrap,
    CommentNickname,
    CommentContent,
    CommentDate,
    CommentWrap,
    Comment,
    ItemOption
} from "../style";

import SVG from "../../../static/svg/SVG";
import SVGEdit from "../../../static/svg/SVGEdit";
import SVGYoutube from "../../../static/svg/SVGYoutube";
import SVGFacebook from "../../../static/svg/SVGFacebook";
import SVGInstar from "../../../static/svg/SVGInstar";
import SVGLink from "../../../static/svg/SVGLink";

class PugjjigView extends Component {

    constructor(props) {
        super(props);

        this.state = {
            pugjjig: [],
            comment: [],
            insertModalState: false,
            selectModalState: false,
            editPugjjig: {},
            edit: false
        }
    }

    componentDidMount() {

        // Props Init
        const {
            getUniversityUniId,
            getCommentListUniId,
            match,
            history
        } = this.props;

        getUniversityUniId(match.params.id, history);   // {id} 게시물의 {DATA}를 받아옵니다.
        getCommentListUniId(match.params.id);     // {id} 게시물의 {COMMENT}을 받아옵니다.
    }

    componentWillReceiveProps(nextProps) {

        // Props Init
        const {
            pugjjig_view,
            pugjjig_comment,
            pugjjig_comment_list,
            pugjjig_comment_delete,
            pugjjig_delete,
            history
        } = this.props;

        if (nextProps.pugjjig_view !== pugjjig_view) {
            this.setState({
                insertModalState: false,
                selectModalState: false
            });
        }

        // 댓글 {DATA} 배열 생성
        if (nextProps.pugjjig_comment_list !== pugjjig_comment_list) {
            this.handleUpdate(nextProps.pugjjig_comment_list);
        }

        // 댓글 {INSERT DATA} 배열 생성
        if (nextProps.pugjjig_comment !== pugjjig_comment) {
            this.handleUpdate(nextProps.pugjjig_comment);
        }

        // 댓글 {DELETE DATA} 재구성
        if (nextProps.pugjjig_comment_delete !== pugjjig_comment_delete) {
            this.handleCommentDeleteUpdate(nextProps.pugjjig_comment_delete);
        }

        // {DELETE DATA} POST 삭제 메인으로 이동
        if (nextProps.pugjjig_delete !== pugjjig_delete) {
            history.push("/");
        }
    }

    handleUpdate = (postData) => {

        // Props Init
        const {
            comment
        } = this.state;

        // 서버에서 받아온 COMMENT 존재한는 경우 실행
        if (postData.data.length > 0 || comment.length > 0) {
            this.setState({
                comment: postData.data.concat(comment)
            });
        }
    }

    // BUG
    handleLikeUpdate = (preData, postData) => {
        console.log(preData);
        console.log(postData);
        if (preData.id === postData.id && preData.uniLikeState !== undefined && postData.uniLikeState !== undefined) {
            if (preData.uniLikeState !== postData.uniLikeState) {
                preData.uniLike = postData.uniLike;
                preData.uniLikeState = postData.uniLikeState;
            }
        }
    }

    /*
     *  DELETE Post 메소드
     */
    handleDelete = () => {
        // Props Init
        const {deleteUniversityuniId} = this.props;

        // State Init
        const {editPugjjig} = this.state;

        deleteUniversityuniId(editPugjjig.id);
    }

    /*
     *  DELETE Comment 메소드
     */
    handleCommentDelete = (id) => {
        // Props Init
        const {deleteCommentId} = this.props;

        deleteCommentId(id);
    }

    handleCommentDeleteUpdate = (id) => {
        // State Init
        const {comment} = this.state;

        this.setState({
            comment: comment.filter(comment => comment.id !== id)
        });
    }

    /*
     *  String target, Object pugjjig
     *  모달상태를 true로 만들어주는 메소드 
     *  pugjjig 값이 전달될 경우 상태변화를 저장합니다.
     */
    openModal = (target, pugjjig) => {
        // 클릭한 Item의 정보를 담습니다.
        if (pugjjig) {
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

    /**
     *  실시간으로 댓글의 갯수를 반환하는 메소드
     */
    handleCommentCount = () => {
        const {
            comment
        } = this.state;

        return comment.length;
    }

    render() {

        // Props Init
        const {
            UpdateUniLikeUniId,
            pugjjig_view,
            pugjjig_like
        } = this.props;

        // State Init
        const {
            insertModalState,
            selectModalState,
            editPugjjig,
            edit,
            comment
        } = this.state;

        // Data Init
        const pugjjig = pugjjig_view.data;

        if (pugjjig_like.data !== undefined) {
            this.handleLikeUpdate(pugjjig_view.data, pugjjig_like.data);
        }

        return (
            <ViewWrap>
                {
                    pugjjig !== undefined ?
                        <Fragment>
                            <NormalHeader/>
                            <MobilePadding>
                                <ViewBox>
                                    <ItemHead>
                                        <ItemUserPhoto>
                                            {
                                                pugjjig.photo === null ?
                                                    <SVG name={"user"} width="38px" height="38px" color={"#E71D36"}/>
                                                    :
                                                    <ProfileIamge
                                                        image={`${SERVER_FILE_URL}${pugjjig.photo.fileThumbnail}`}
                                                    />
                                            }
                                        </ItemUserPhoto>
                                        <ItemRight>
                                            <ItemUsername>
                                                <Link to={`/pugjjig/userSearch/${pugjjig.account_nickname}`}>
                                                    {pugjjig.account_nickname}
                                                </Link>
                                            </ItemUsername>
                                            <ItemUserInfo>
                                                {
                                                    (pugjjig.account_urlList !== null && pugjjig.account_urlList.length > 0) ?
                                                        pugjjig.account_urlList.map((url, index) => (
                                                            <a key={index} href={url} target="_blank">
                                                                {
                                                                    url.indexOf("instagram") !== -1 ?
                                                                        <SVGInstar width="14px" height="14px"/>
                                                                        :
                                                                        url.indexOf("facebook") !== -1 ?
                                                                            <SVGFacebook width="14px" height="14px"/>
                                                                            :
                                                                            url.indexOf("youtube") !== -1 ?
                                                                                <SVGYoutube width="14px" height="14px"/>
                                                                                :
                                                                                <SVGLink width="14px" height="14px"/>
                                                                }
                                                            </a>
                                                        ))
                                                        :
                                                        "회원"
                                                }
                                            </ItemUserInfo>
                                        </ItemRight>
                                        <ItemStar>
                                            {
                                                pugjjig.uniStar === 1 ?
                                                    <SVG name={"star"} width="14px" height="14px" color={"#ffd700"}/>
                                                    :
                                                    pugjjig.uniStar === 2 ?
                                                        <Fragment>
                                                            <SVG name={"star"} width="14px" height="14px"
                                                                 color={"#ffd700"}/>
                                                            <SVG name={"star"} width="14px" height="14px"
                                                                 color={"#ffd700"}/>
                                                        </Fragment>
                                                        :
                                                        pugjjig.uniStar === 3 ?
                                                            <Fragment>
                                                                <SVG name={"star"} width="14px" height="14px"
                                                                     color={"#ffd700"}/>
                                                                <SVG name={"star"} width="14px" height="14px"
                                                                     color={"#ffd700"}/>
                                                                <SVG name={"star"} width="14px" height="14px"
                                                                     color={"#ffd700"}/>
                                                            </Fragment>
                                                            :
                                                            pugjjig.uniStar === 4 ?
                                                                <Fragment>
                                                                    <SVG name={"star"} width="14px" height="14px"
                                                                         color={"#ffd700"}/>
                                                                    <SVG name={"star"} width="14px" height="14px"
                                                                         color={"#ffd700"}/>
                                                                    <SVG name={"star"} width="14px" height="14px"
                                                                         color={"#ffd700"}/>
                                                                    <SVG name={"star"} width="14px" height="14px"
                                                                         color={"#ffd700"}/>
                                                                </Fragment>
                                                                :
                                                                <Fragment>
                                                                    <SVG name={"star"} width="14px" height="14px"
                                                                         color={"#ffd700"}/>
                                                                    <SVG name={"star"} width="14px" height="14px"
                                                                         color={"#ffd700"}/>
                                                                    <SVG name={"star"} width="14px" height="14px"
                                                                         color={"#ffd700"}/>
                                                                    <SVG name={"star"} width="14px" height="14px"
                                                                         color={"#ffd700"}/>
                                                                    <SVG name={"star"} width="14px" height="14px"
                                                                         color={"#ffd700"}/>
                                                                </Fragment>
                                            }
                                            <ItemOption>{setAtmosphere(pugjjig.uniAtmosphere)}</ItemOption>
                                            <ItemOption>{setPrice(pugjjig.uniPrice)}</ItemOption>
                                        </ItemStar>
                                    </ItemHead>
                                    <ItemSubject>{pugjjig.uniSubject}</ItemSubject>
                                    <ViewContent>
                                        {
                                            pugjjig.uniContent.split('\n').map((line, index) => {
                                                return (<div key={index}>{line}</div>)
                                            })
                                        }
                                    </ViewContent>
                                    {
                                        pugjjig.files ?
                                            <ItemImgBox>
                                                <ImageSlide
                                                    slideShow={1}
                                                    images={pugjjig.files}
                                                    thumbnail={false}
                                                />
                                            </ItemImgBox>
                                            :
                                            null
                                    }
                                    <ItemLocalWrap>
                                        {
                                            // SERVER 에서 불러오는 속도와 React 에서 storePublic 랜더링하는 속도와 맞춰주는 코드
                                            pugjjig.storePublic !== null ?
                                                <Fragment>
                                                    {
                                                        pugjjig.uniName !== "" ?
                                                            <Link to={`/pugjjig/uniSearch/${pugjjig.uniName}`}>
                                                                <span>{pugjjig.uniName}</span>
                                                            </Link>
                                                            :
                                                            null
                                                    }
                                                    <Link to={`/pugjjig/stoSearch/${pugjjig.storePublic.stoName}`}>
                                                        <span>{pugjjig.storePublic.stoName}</span>
                                                    </Link>
                                                    <a href={`${pugjjig.storePublic.stoUrl}`} target="_blank">
                                                        {pugjjig.storePublic.stoAddress}
                                                    </a>
                                                </Fragment>
                                                :
                                                null
                                        }
                                    </ItemLocalWrap>
                                    <ItemTagWrap>
                                        {
                                            pugjjig.uniTag !== "" ?
                                                pugjjig.uniTag.split(",").map((tag, index) => (
                                                    <ItemTag key={index}>
                                                        <Link to={`/pugjjig/tagSearch/${tag}`}>#{tag}</Link>
                                                    </ItemTag>
                                                ))
                                                :
                                                null
                                        }
                                    </ItemTagWrap>
                                    <ItemDetailWrap>
                                        <ItemStateWrap>
                                            <ItemDetail>
                                                좋아요 {pugjjig.uniLike}개
                                                댓글 {this.handleCommentCount()}개
                                            </ItemDetail>
                                            <ItemDate>
                                                {pugjjig.modifiedDate.split("T")[0]}
                                            </ItemDate>
                                        </ItemStateWrap>
                                    </ItemDetailWrap>
                                    <ItemBottom>
                                        <ItemState>
                                            <ItemLikeBtn onClick={() => UpdateUniLikeUniId(pugjjig.id)}>
                                                {
                                                    pugjjig.uniLikeState === true ?
                                                        <SVG name={"heartCk"} width="14px" height="14px"
                                                             color={"#d11d33"}/>
                                                        :
                                                        <SVG name={"heart"} width="14px" height="14px"
                                                             color={"#dddddd"}/>
                                                }
                                                <ItemLikeText>
                                                    좋아요
                                                </ItemLikeText>
                                            </ItemLikeBtn>

                                            <ItemLikeBtn>
                                                <SVG name={"comment"} width="14px" height="14px" color={"#dddddd"}/>
                                                <ItemLikeText>
                                                    댓글
                                                </ItemLikeText>
                                            </ItemLikeBtn>

                                            <ItemEditBtn onClick={() => this.openModal("selectModalState", pugjjig)}>
                                                <SVGEdit width="16px" height="16px" color={"#333333"}/>
                                            </ItemEditBtn>
                                        </ItemState>
                                        <CommentWrite
                                            uniId={pugjjig.id}
                                        />
                                        {
                                            (comment !== undefined && comment.length > 0) ?
                                                <CommentWrap>
                                                    {
                                                        comment.map((comment, index) => (
                                                            <Comment key={index}>
                                                                <CommentNickname>
                                                                    {comment.account_nickname}
                                                                </CommentNickname>
                                                                <CommentContent>
                                                                    {comment.content}
                                                                </CommentContent>
                                                                <CommentDate>
                                                                    {comment.modifiedDate.split("T")[0]}
                                                                    {
                                                                        // 작성자가 본인인 경우 수정 권한
                                                                        USER_LONG_ID() === comment.account_id ?
                                                                            <HiddenBtn
                                                                                onClick={() => this.handleCommentDelete(comment.id)}
                                                                            >
                                                                                <SVG name={"close"} width="8px"
                                                                                     height="8px" color={"#333333"}/>
                                                                            </HiddenBtn>
                                                                            :
                                                                            null
                                                                    }
                                                                </CommentDate>
                                                            </Comment>
                                                        ))
                                                    }
                                                </CommentWrap>
                                                :
                                                null
                                        }
                                    </ItemBottom>
                                </ViewBox>

                                {/* Item Edit Select modal */}
                                <ItemEditModal
                                    modalState={selectModalState}
                                    closeModal={this.closeModal}
                                    openModal={this.openModal}
                                    handleDelete={this.handleDelete}
                                    edit={edit}
                                />
                                {/* Edit Modal */}
                                <InsertModal
                                    modalState={insertModalState}
                                    closeModal={this.closeModal}
                                    stoId={null}
                                    stoAddress={null}
                                    editPugjjig={editPugjjig}
                                />
                            </MobilePadding>
                        </Fragment>
                        :
                        <NotPost>불러오는 중입니다...</NotPost>
                }
            </ViewWrap>
        )
    }
}

PugjjigView.propTypes = {
    getUniversityUniId: PropTypes.func.isRequired,
    UpdateUniLikeUniId: PropTypes.func.isRequired,
    getCommentListUniId: PropTypes.func.isRequired,
    deleteUniversityuniId: PropTypes.func.isRequired,
    deleteCommentId: PropTypes.func.isRequired,
    pugjjig_view: PropTypes.object.isRequired,
    pugjjig_like: PropTypes.object.isRequired,
    pugjjig_comment: PropTypes.object.isRequired,
    pugjjig_comment_list: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired,
    pugjjig_delete: PropTypes.number.isRequired,
    pugjjig_comment_delete: PropTypes.number.isRequired
}


const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_view: state.pugjjig.pugjjig_view,
    pugjjig_like: state.pugjjig.pugjjig_like,
    pugjjig_comment: state.pugjjig.pugjjig_comment,
    pugjjig_comment_list: state.pugjjig.pugjjig_comment_list,
    pugjjig_comment_delete: state.pugjjig.pugjjig_comment_delete,
    pugjjig_delete: state.pugjjig.pugjjig_delete
});

export default connect(
    mapStateToProps,
    {
        getUniversityUniId,
        UpdateUniLikeUniId,
        getCommentListUniId,
        deleteUniversityuniId,
        deleteCommentId
    }
)(PugjjigView);