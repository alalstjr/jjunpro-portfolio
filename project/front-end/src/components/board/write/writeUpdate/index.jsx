import React, { Component, Fragment } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { boardTaskInsert, getBoardTask } from "../../../../actions/boardTaskActions";

import BoardWrite from "../boardWrite/index";

class BoardWriteUpdate extends Component {
    constructor(props) {
        super(props);
        this.state = {
            num: 0,
            writer: "작성자",
            password: "비밀번호",
            category: "",
            subject: "",
            content: "",
            files: [],
            registerFiles: [],
            removeFiles:[],
            errors: {}
        };
    }

    // 게시글 쓰기가 수정일 경우
    componentWillReceiveProps(nextProps) {
        // 파라미터로 수정할 게시판의 bo_num 값을 전달받습니다.
        const { bo_num } = this.props.match.params;
        
        // 전달받은 props 객체 존재여부
        let board_task_check = Object.keys(nextProps.board_task).length > 0;

        if(bo_num && board_task_check) {
            const {
                num,
                writer,
                password,
                category,
                subject,
                content,
                files
            } = nextProps.board_task;

            // 수정할 게시판의 파일 리스트를 받아와서 state에 등록합니다.
            let registerFiles = nextProps.board_task_files;

            this.setState({
                num,
                writer,
                password,
                category,
                subject,
                content,
                registerFiles
            });
        }
    }

    componentDidMount() {
        const { bo_num } = this.props.match.params;
        if(bo_num) {
            this.props.getBoardTask(bo_num, this.props.history);
        }
    }
    
    // Input Setup
    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    // File Setup
    fileState = (target) => {
        this.setState({
            files: target.map(fileItem => fileItem)
        });
    }

    // registerFiles Setup
    registerFileState = (file) => {
        const { registerFiles, removeFiles } = this.state;
        registerFiles.splice(registerFiles.indexOf(file), 1);

        removeFiles.push(file.id)

        this.setState({
            registerFiles,
            removeFiles
        });
    }

    // Form Submit
    onSubmit = (e) => {
        e.preventDefault();

        const { 
            num,
            writer, 
            password, 
            category, 
            subject, 
            content,
            files,
            removeFiles,
            registerFiles
        } = this.state;

        const newBoardTask = {
            num: num,
            writer: writer,
            password: password,
            category: category,
            subject: subject,
            content: content,
            files: (files.length + registerFiles.length)
        };

        this.props.boardTaskInsert(newBoardTask, this.props.history, files, removeFiles);
    }

    render() {

        const {
            num,
            writer,
            password,
            category,
            subject,
            content,
            files,
            registerFiles,
            errors
        } = this.state;

        return (
            <Fragment>
                <BoardWrite
                    num={num}
                    writer={writer}
                    password={password}
                    category={category}
                    subject={subject}
                    content={content}
                    files={files}
                    registerFiles={registerFiles}
                    errors={errors}
                    onSubmit={this.onSubmit}
                    onChange={this.onChange}
                    fileState={this.fileState}
                    registerFileState={this.registerFileState}
                />
            </Fragment>
        )
    }
}

BoardWriteUpdate.propTypes = {
    boardTaskInsert: PropTypes.func.isRequired,
    getBoardTask: PropTypes.func.isRequired,
    board_task: PropTypes.object.isRequired,
    board_task_files: PropTypes.array.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    board_task: state.board_task.board_task,
    board_task_files: state.board_task.board_task_files,
    errors: state.errors
});

export default connect(
    mapStateToProps, 
    { 
        boardTaskInsert,
        getBoardTask
    }
)(BoardWriteUpdate);