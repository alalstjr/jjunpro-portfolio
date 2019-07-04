import React, { Component, Fragment } from 'react';
import { connect } from "react-redux";
import PropTypes from "prop-types";

import { getBoardTasks } from "../../../actions/boardTaskActions";

import { ListWrap, ListBg } from "../style";
import { Container, DataNone } from "../../../style/globalStyles";

import Item from "./item";

class BoardList extends Component {

    componentDidMount() {
        this.props.getBoardTasks();
    }

    render() {
        // props Init
        const { board_tasks } = this.props.board_tasks;

        // Variables Init
        let BoardContent;
        let items = [];

        // Board Get List
        const BoardGetList = board_tasks => {
            if(board_tasks.length > 0) {
                const tasks = board_tasks.map(board_task => (
                    <Item
                        key={board_task.num}
                        board_task={board_task}
                    />
                ));

                for(let i = 0; i < tasks.length; i++) {
                    items.push(tasks[i]);
                }
                
                return (
                    <Fragment>
                        {items}
                    </Fragment>
                );
            } else {
                return(
                    <DataNone>
                        게시글이 존재하지 않습니다.
                    </DataNone>
                );
            }
        };

        // Board Get List View
        BoardContent = BoardGetList(board_tasks);

        return (
            <ListBg>
                <Container>
                    <ListWrap>
                        {BoardContent}
                    </ListWrap>
                </Container>
            </ListBg>
        )
    }
}

BoardList.propTypes = {
    getBoardTasks: PropTypes.func.isRequired,
    board_tasks: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    board_tasks: state.board_task
});

export default connect(
    mapStateToProps,
    { getBoardTasks }
)(BoardList);