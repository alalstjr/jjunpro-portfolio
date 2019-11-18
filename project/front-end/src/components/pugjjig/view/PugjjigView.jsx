import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import ImageSlide from "../../widget/mainTitleSlide"
import NormalHeader from "../../layout/header/normal/NormalHeader"
import { pugjjigGetView, pugjjigLike } from "../../../actions/KakaoMapActions"

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
    ItemLikeText
} from "../style"

import SVG from "../../../static/svg/SVG"

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
            <ViewWrap>
                {
                    pugjjig_view.data !== undefined ?
                    <Fragment>
                        <NormalHeader/>
                        <ViewBox>
                            <ItemHead>
                                <ItemUserPhoto>
                                    <SVG name={"user"} width="38px" height="38px" color={"#E71D36"} />
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
                                </ItemState>
                            </ItemBottom>
                        </ViewBox>
                    </Fragment>
                    :
                    <div>불러오는 중입니다...</div>
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