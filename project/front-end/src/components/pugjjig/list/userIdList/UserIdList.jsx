import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { pugjjigGetUserList, pugjjigLike } from "../../../../actions/KakaoMapActions"
import Item from "../../../../components/pugjjig/list/item/Item"
import InfiniteScroll from "react-infinite-scroller"
import { PugjjigItemWrap } from "../../style"

class UserIdList extends Component {

    constructor(props){
        super(props);
    
        this.state = {
            pugjjig: []
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
            pugjjig_list
        } = this.props;

        if(nextProps.pugjjig_list !== pugjjig_list) {
            this.handleUpdate(nextProps.pugjjig_list);
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

    render() {
        // Props Init
        const { 
            pugjjigLike,
            pugjjig_like
        } = this.props;

        // State Init
        const {
            pugjjig
        } = this.state;

        // Variables Init
        let pugjjigContent;
        let pugjjigList = [];
        
        // pugjjigList
        const pugjjigGet = (pugjjig) => {
            if(pugjjig !== undefined && pugjjig.length > 0) {
                const data = pugjjig.map((pugjjig, index) => (
                    <Item 
                        key         = {index}
                        pugjjig     = {pugjjig}
                        pugjjigLike = {pugjjigLike}
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
                    <div>푹찍이 존재하지 않습니다.</div>
                );
            }
        }

        // pugjjig Get List View
        pugjjigContent = pugjjigGet(pugjjig);

        return (
            <PugjjigItemWrap>
                <InfiniteScroll
                    pageStart   = {0}
                    loadMore    = {this.handleLoad}
                    hasMore     = {true}
                    initialLoad = {false}
                    useWindow   = {false}
                    threshold   = {500}
                >
                    {pugjjigContent}
                </InfiniteScroll>
            </PugjjigItemWrap>
        )
    }
}

UserIdList.propTypes = {
    pugjjigGetUserList: PropTypes.func.isRequired,
    pugjjigLike: PropTypes.func.isRequired,
    pugjjig_list: PropTypes.object.isRequired,
    pugjjig_like: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired
}
  
const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_list: state.pugjjig.pugjjig_list,
    pugjjig_like: state.pugjjig.pugjjig_like
});
  
export default connect(
    mapStateToProps, 
    { 
        pugjjigGetUserList,
        pugjjigLike 
    }
  )(UserIdList);