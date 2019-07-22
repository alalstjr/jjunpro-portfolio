import React, { Component, Fragment } from 'react'
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { userLoginCheck } from "../actions/accountActions";


class LnitialComponent extends Component {

    componentDidMount() {
        this.props.userLoginCheck();
    }

    componentWillReceiveProps(nextProps) {
        // console.log(nextProps);
        // if (nextProps.userInfo !== this.props.userInfo) {
        //     this.props.userLoginCheck();
        // }
    }

    render() {
        return (
            <Fragment/>
        )
    }
}


userLoginCheck.propTypes = {
    userLoginCheck: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors,
    userInfo: state.account.userInfo
});

export default connect(
    mapStateToProps, 
    { userLoginCheck }
)(LnitialComponent);