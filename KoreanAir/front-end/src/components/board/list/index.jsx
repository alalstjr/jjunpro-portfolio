import React, { Component, Fragment } from 'react';
import { connect } from "react-redux";
import PropTypes from "prop-types";

import { getPaging } from "../../../actions/boardTaskActions";

import { ListWrap, ListBg } from "../style";
import { Container, DataNone } from "../../../style/globalStyles";

import Item from "./item";
import Paging from "./paging";


class BoardList extends Component {

    componentDidMount() {
        // 처음 화면 출력시 페이징 출력
        const { pageNum } = this.props;
        this.props.getPaging(pageNum - 1);
    }

    componentWillReceiveProps(nextProps) {
        // 페이징시 뒤로가기 혹은 앞으로가기 클릭시 이전페이지 출력
        const { pageNum } = this.props;
        if(nextProps.pageNum !== pageNum) {
            this.props.getPaging(nextProps.pageNum - 1);
        }
    }

    render() {
        // props Init
        const { board_paging } = this.props;
        const board_paging_content = board_paging.content;

        // Variables Init
        let BoardContent;
        let items = [];

        // Board Get List
        const BoardGetList = board_paging => {
            if(board_paging_content !== undefined && board_paging_content.length > 0) {
                const tasks = board_paging.map(board_task => (
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
        BoardContent = BoardGetList(board_paging_content);

        return (
            <ListBg>
                <Container>
                    <ListWrap>
                        {BoardContent}
                    </ListWrap>
                    {
                        // 게시글이 존재하면 페이징 표시
                        (board_paging.content !== undefined && !board_paging.empty) &&
                        <Paging
                            first={board_paging.first}
                            last={board_paging.last}
                            totalPages={board_paging.totalPages}
                            pageable={board_paging.pageable}
                            getPaging={this.props.getPaging}
                        />
                    }
                </Container>
            </ListBg>
        )
    }
}

BoardList.propTypes = {
    getPaging: PropTypes.func.isRequired,
    board_paging: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    board_paging: state.board_task.board_paging
});

export default connect(
    mapStateToProps,
    { getPaging }
)(BoardList);