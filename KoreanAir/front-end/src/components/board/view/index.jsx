import React, { Component, Fragment } from 'react';
import PropTypes from "prop-types";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import { getBoardTask } from "../../../actions/boardTaskActions";

import ImageItem from "./item";

import { ContainerView, ViewWrap, ListLink, TitleWrap, ViewTitle, ViewContent, ViewWriter,  ViewDate } from "../style";

class BoardView extends Component {

    componentDidMount() {
        const { bo_num } = this.props.match.params;
        this.props.getBoardTask(bo_num, this.props.history);
    }

    render() {
        // props Init
        const { 
            board_task, 
            board_task_files 
        } = this.props;

        // Variables Init
        let items = [];

        // 줄바꿈 치환
        if(board_task.content != undefined) {
            const br = /(\n|\r\n)/g;
            board_task.content = board_task.content.replace(br, "<br/>");
        }

        const imageView = (board_task_files) => {
            const files = board_task_files.map(file => (
                <ImageItem
                    key={file.fileNo}
                    file={file}
                />
            ));

            for(let i = 0; i < files.length; i++) {
                items.push(files[i]);
            }
    
            return(
                <Fragment>{items}</Fragment>
            );
        }

        return (
            <ViewWrap>
                <ContainerView>
                    <ListLink>
                        <Link to="../">목록으로</Link>
                    </ListLink>
                    <TitleWrap>
                        <ViewTitle>
                            {board_task.subject}
                        </ViewTitle>
                        <ViewWriter>{board_task.writer}</ViewWriter>
                        <ViewDate>{board_task.reg_date}</ViewDate>
                    </TitleWrap>    
                    {imageView(board_task_files)}
                    <ViewContent>
                        {board_task.content}
                    </ViewContent>
                </ContainerView>
            </ViewWrap>
        )
    }
}

BoardView.propTypes = {
    getBoardTask: PropTypes.func.isRequired,
    board_task: PropTypes.object.isRequired,
    board_task_files: PropTypes.array.isRequired
}

const mapStateToProps = state => ({
    board_task: state.board_task.board_task,
    board_task_files: state.board_task.board_task_files,
    errors: state.errors
});

export default connect(
    mapStateToProps, 
    { getBoardTask }
)(BoardView);