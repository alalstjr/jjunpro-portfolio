import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import BoardHead from "./header";
import CateGory from "./category";
import BoardList from "./list";
import ListBtn from "./bottom/listBtn";

class Board extends Component {
    render() {
        const { page_num } = this.props.match.params;
        const { account } = this.props;

        return (
            <div>
                <BoardHead/>
                <CateGory
                    pageNum={page_num}
                />
                <BoardList
                    pageNum={page_num}
                />
                {
                    account.token && 
                    <ListBtn
                        option={"list"}
                    />
                }
            </div>
        )
    }
}

Board.propTypes = {
    account: PropTypes.object.isRequired,
}

const mapStateToProps = state => ({
    account: state.account.userInfo
});

export default connect(
    mapStateToProps
)(Board);