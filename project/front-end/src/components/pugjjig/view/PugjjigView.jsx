import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { pugjjigGetView, pugjjigLike } from "../../../actions/KakaoMapActions"

class PugjjigView extends Component {

    componentDidMount() {
        this.props.pugjjigGetView(this.props.match.params.id, this.props.history);
    }

    handleLikeUpdate = (preData, postData) => {
        if(preData.uniLikeState !== postData.uniLikeState) {
            preData.uniLike = postData.uniLike;
            preData.uniLikeState = postData.uniLikeState;
        }
    } 

    render() {

        const { pugjjigLike, pugjjig_view, pugjjig_like } = this.props;
        const pugjjig = pugjjig_view.data;
        
        if(pugjjig_like.data !==undefined) {
            this.handleLikeUpdate(pugjjig_view.data, pugjjig_like.data);
        }

        return (
            <Fragment>
                {
                    pugjjig_view.data !== undefined ? 
                    <div>
                        <div>근처 대학교 : {pugjjig.uniName}</div>
                        <div>작성자 : {pugjjig.account_nickname}</div>
                        <div>제목 : {pugjjig.uniSubject}</div>
                        <div>내용 : {pugjjig.uniContent}</div>
                        <div>상점평점 : {pugjjig.uniStar}</div>
                        <div>리뷰의 푹찍 갯수 : {pugjjig.uniLike}</div>
                        {/* <div>리뷰 태그 : {pugjjig.uniTag}</div> */}
                        <div>리뷰 최종 수정 날짜 : {pugjjig.modifiedDate}</div>
                        <div>좋아요를 누른 상태 : {pugjjig.uniLikeState == true ? "푹찍~!" : "X"}</div>
                        <button type="button" onClick={() => pugjjigLike(pugjjig.id)}>푹찍</button>
                    </div>
                    :
                    <div>불러오는 중입니다...</div>
                }
            </Fragment>
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