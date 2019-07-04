import React, { Component, Fragment } from 'react';
import PropTypes from "prop-types";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import { getBoardTask, boardTaskDelete } from "../../../actions/boardTaskActions";

import WithContentReplace from "../../hoc/withContentReplace";
import WithFilePrint from "../../hoc/withFilePrint";

import { EditBtn, RemoveBtn } from "../../../style/globalStyles";
import { 
    ContainerView, 
    ViewWrap, 
    ListLink, 
    TitleWrap, 
    ViewTitle, 
    ViewContent, 
    ViewWriter,  
    ViewDate,
    EditWrap
} from "../style";

class BoardView extends Component {
    componentDidMount() {
        const { bo_num } = this.props.match.params;
        this.props.getBoardTask(bo_num, this.props.history);
    }

    hendleRemove = (bo_num) => {
        this.props.boardTaskDelete(bo_num, this.props.history)
    }

    render() {
        // props Init
        const { 
            board_task, 
            board_task_img_files 
        } = this.props;
        const { bo_num } = this.props.match.params;

        return (
            <ViewWrap>
                <ContainerView>
                    <ListLink>
                        <Link to="../">목록으로</Link>
                        <EditWrap>
                            <EditBtn>
                                <Link to={`/boardEvent/write/${bo_num}`}>수정</Link>
                            </EditBtn>
                            <RemoveBtn
                                onClick={this.hendleRemove.bind(this, bo_num)}
                            >삭제</RemoveBtn>
                        </EditWrap>
                    </ListLink>
                    <TitleWrap>
                        <ViewTitle>
                            {board_task.subject}
                        </ViewTitle>
                        <ViewWriter>{board_task.writer}</ViewWriter>
                        <ViewDate>{board_task.reg_date}</ViewDate>
                    </TitleWrap>    
                    <WithFilePrint
                        imgFileBundle={board_task_img_files}
                    />
                    <ViewContent>
                        <WithContentReplace 
                            content={board_task.content}
                            produce={true}
                        />
                    </ViewContent>
                </ContainerView>
            </ViewWrap>
        )
    }
}

BoardView.propTypes = {
    getBoardTask: PropTypes.func.isRequired,
    boardTaskDelete: PropTypes.func.isRequired,
    board_task: PropTypes.object.isRequired,
    board_task_files: PropTypes.array.isRequired,
    board_task_img_files: PropTypes.array.isRequired
}

const mapStateToProps = state => ({
    board_task: state.board_task.board_task,
    board_task_files: state.board_task.board_task_files,
    board_task_img_files: state.board_task.board_task_img_files
});

export default connect(
    mapStateToProps, 
    { 
        getBoardTask,
        boardTaskDelete
    }
)(BoardView);