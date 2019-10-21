import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { pugjjigGetView, pugjjigLike } from "../../../actions/KakaoMapActions"

class PugjjigView extends Component {

    componentDidMount() {
        this.props.pugjjigGetView(this.props.match.params.id, this.props.history);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.modalState !== this.props.modalState) {
            const { stoId } = nextProps;
            this.props.pugjjigGet(stoId);
        }
    }

    render() {

        const { pugjjig_view } = this.props;
        const pugjjig = pugjjig_view.data;

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
                        <div>리뷰 푹찍 갯수 : {pugjjig.uniLike}</div>
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