import React, { Component } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { pugjjigGetUserList, pugjjigLike } from "../../../actions/KakaoMapActions"

import List from "../../../components/pugjjig/list/List"

class PugjjigViewList extends Component {

    componentDidMount() {
        let userId;

        if(this.props.match.params !== undefined) {
            userId = this.props.match.params.userId;
        }

        this.props.pugjjigGetUserList(this.props.history, userId);
    }

    render() {

        // props Init
        const { 
            pugjjigLike, 
            pugjjig_list, 
            pugjjig_like
        } = this.props;

        return (
            <div>
                <List
                    pugjjigLike = {pugjjigLike}
                    pugjjig_list = {pugjjig_list}
                    pugjjig_like = {pugjjig_like}
                /> 
            </div>
        )
    }
}

PugjjigViewList.propTypes = {
    pugjjigLike: PropTypes.func.isRequired,
    pugjjigGetUserList: PropTypes.func.isRequired,
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
  )(PugjjigViewList);