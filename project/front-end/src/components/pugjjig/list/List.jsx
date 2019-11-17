import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { pugjjigGetStoreList, pugjjigLike } from "../../../actions/KakaoMapActions"
import Item from "../../../components/pugjjig/list/item/Item"
import InfiniteScroll from "react-infinite-scroller"
import { PugjjigItemWrap } from "../style"

class List extends Component {

    constructor(props){
        super(props);
    
        this.state = {
            pugjjig: []
        }
    }

    componentDidMount() {
        const { stoId } = this.props;

        this.props.pugjjigGetStoreList(stoId, 0);
    }

    componentWillReceiveProps(nextProps) {
        if(nextProps.pugjjig_store_list !== this.props.pugjjig_store_list) {
            this.handleUpdate(nextProps.pugjjig_store_list);
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
        const { stoId, pugjjig_store_list } = this.props;
        
        if(pugjjig_store_list.data !== undefined) {
            if(pugjjig_store_list.data.length <= 0) {
                return false;
            }
        }

        this.props.pugjjigGetStoreList(stoId, page);
    }

    handleUpdate = (postData) => {
        
        this.setState({
            pugjjig: this.state.pugjjig.concat(postData.data)
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

List.propTypes = {
    pugjjigLike: PropTypes.func.isRequired,
    pugjjig_store_list: PropTypes.object.isRequired,
    pugjjig_like: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired
}
  
const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_store_list: state.pugjjig.pugjjig_store_list,
    pugjjig_like: state.pugjjig.pugjjig_like
});
  
export default connect(
    mapStateToProps, 
    { 
        pugjjigGetStoreList,
        pugjjigLike 
    }
  )(List);