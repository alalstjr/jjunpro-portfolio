import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { pugjjigGetView, pugjjigLike } from "../../../actions/KakaoMapActions"

class PugjjigView extends Component {

    constructor(props){
        super(props);

        this.state = {
            pugjjig_view_like: 0
        }
    }

    componentDidMount() {
        // 처음 rendering 시점의 푹찍 리뷰 정보를 가져옵니다.
        this.props.pugjjigGetView(this.props.match.params.id, this.props.history);
    }

    componentWillReceiveProps(nextProps) {
        // 처음 rendering 시점의 푹찍 좋아요 갯수
        if (nextProps.pugjjig_view !== this.props.pugjjig_view) {
            this.setState({
                pugjjig_view_like: nextProps.pugjjig_view.data.uniLike
            });
        }

        // 사용자가 푹찍을 눌렀을경우 좋아요 갯수 state 카운팅
        if (nextProps.pugjjig_view_like !== this.props.pugjjig_view_like) {
            this.setState({
                pugjjig_view_like: nextProps.pugjjig_view_like.data
            });
        }
    }

    render() {

        const { pugjjig_view } = this.props;
        const pugjjig = pugjjig_view.data;

        const { pugjjig_view_like } = this.state;

        return (
            <Fragment>
                {
                    pugjjig_view.data !== undefined ? 
                    <div>
                        <div>작성자 : {pugjjig.account_nickname}</div>
                        <div>리뷰수정 날짜 : {pugjjig.modifiedDate}</div>
                        <div>리뷰 제목 : {pugjjig.uniSubject}</div>
                        <div>리뷰 내용 : {pugjjig.uniContent}</div>
                        <div>리뷰 태그 : {pugjjig.uniTag}</div>
                        <div>가게 평점 : {pugjjig.uniStar}</div>
                        <div>리뷰 푹찍 갯수 : {pugjjig_view_like}</div>
                        <div>좋아요를 누른 상태 : {pugjjig.uniLikeState == true ? "푹찍~!" : "X"}</div>
                        <button type="button" onClick={() => this.props.pugjjigLike(pugjjig.id)}>푹찍</button>
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
    pugjjig_view_like: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired
  }
  
  
  const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_view: state.pugjjig.pugjjig_view,
    pugjjig_view_like: state.pugjjig.pugjjig_view_like
  });
  
  export default connect(
    mapStateToProps, 
    { 
        pugjjigGetView,
        pugjjigLike
    }
  )(PugjjigView);