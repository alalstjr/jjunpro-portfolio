import React, { Fragment, Component } from 'react';
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { pugjjigGetUser, pugjjigLikeGetUser } from "../../../actions/KakaoMapActions"
import Item from "../../kakaoMap/list/item/Item"
import { pugjjigLike } from "../../../actions/KakaoMapActions"

class PugjjigViewList extends Component {

    componentDidMount() {
        let userId;
        let path = this.props.match.path;

        if(this.props.match.params !== undefined) {
            userId = this.props.match.params.userId;
        }

        if(path.indexOf("/pugjjigs") === 0) {
            this.props.pugjjigGetUser(this.props.history, userId);
        } else if(path.indexOf("/pugjjigLikes") === 0) {
            this.props.pugjjigLikeGetUser(this.props.history, userId);
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
        const { pugjjigLike, pugjjig_list_user, pugjjig_view_like } = this.props;

        // Variables Init
        let pugjjigContent;
        let pugjjigList = [];
        
        // pugjjigList
        const pugjjigGet = (pugjjig) => {
            if(pugjjig.data !== undefined) {
                const data = pugjjig.data.map(university => (
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
                if(pugjjig_view_like.data !== undefined) {
                    this.handleLikeUpdate(pugjjigList, pugjjig_view_like.data);
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
        pugjjigContent = pugjjigGet(pugjjig_list_user);

        return (
            <div>
               {pugjjigContent} 
            </div>
        )
    }
}

PugjjigViewList.propTypes = {
    pugjjigLike: PropTypes.func.isRequired,
    pugjjigGetUser: PropTypes.func.isRequired,
    pugjjigLikeGetUser: PropTypes.func.isRequired,
    pugjjig_list_user: PropTypes.object.isRequired,
    pugjjig_view_like: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired
}
  
const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_list_user: state.pugjjig.pugjjig_list_user,
    pugjjig_view_like: state.pugjjig.pugjjig_view_like
});

export default connect(
    mapStateToProps, 
    { pugjjigGetUser, pugjjigLikeGetUser, pugjjigLike }
  )(PugjjigViewList);