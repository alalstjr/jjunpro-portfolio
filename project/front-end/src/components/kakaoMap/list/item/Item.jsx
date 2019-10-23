import React, { Component } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { Link } from "react-router-dom"
import { pugjjigLike } from "../../../../actions/KakaoMapActions"

class Item extends Component {

    constructor(props){
        super(props);

        this.state = {
            likeCount: 0,
            likeState: false
        }
    }

    componentDidMount() {
        const { university } = this.props;
        let like_count = university.uniLike;
        let like_state = university.uniLikeState;

        this.setLike(like_count, like_state);
    }

    setLike = (likeCount, likeState) => {
        this.setState({
            likeCount, 
            likeState
        });
    }

    render() {
        const { university, pugjjigLike, pugjjig_view_like } = this.props;
        const { likeCount, likeState } = this.state;
        
        let like_count = university.uniLike;
        let like_state = university.uniLikeState;

        // 여기 작업해야함 카운트 실시간 변화
        const likeHendler = () => {
            pugjjigLike(university.id);
            if(pugjjig_view_like.data !== undefined) {
                like_count = pugjjig_view_like.data.likeCount;
                like_state = pugjjig_view_like.data.likeState;
                this.setLike(like_count, like_state);
            }
        }

        return (
            <div>
                <Link to={`/pugjjig/${university.id}`} target="_blank">
                    <div>근처 대학교 : {university.uniName}</div>
                    <div>작성자 : {university.account_nickname}</div>
                    <div>제목 : {university.uniSubject}</div>
                    <div>내용 : {university.uniContent}</div>
                    <div>상점평점 : {university.uniStar}</div>
                    <div>리뷰의 좋아요 갯수 : {likeCount}</div>
                    <div>리뷰 태그 : {university.uniTag[0]}</div>
                    <div>리뷰 최종 수정 날짜 : {university.modifiedDate}</div>
                    <div>좋아요를 누른 상태 : {likeState == true ? "푹찍~!" : "X"}</div>
                </Link>
                <button type="button" onClick={likeHendler}>푹찍</button>
            </div>
        )
    }
}

Item.propTypes = {
    pugjjigLike: PropTypes.func.isRequired,
    pugjjig_view_like: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_view_like: state.pugjjig.pugjjig_view_like
});

export default connect(
    mapStateToProps, 
    { pugjjigLike }
  )(Item);