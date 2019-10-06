import React, { Component, Fragment } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { boardTaskInsert } from "../../../../actions/boardTaskActions";

import BoardWrite from "../boardWrite/index";

class BoardWriteInsert extends Component {
    constructor(props) {
        super(props);
        this.state = {
            num: 0,
            writer: "작성자",
            password: "비밀번호",
            category: "",
            subject: "",
            content: "",
            fileCount: 0,
            files: [],
            registerFiles: [],
            removeFiles: [],
            errors: {}
        };
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
            files: target.map(fileItem => fileItem),
            fileCount: target.length
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
            fileCount,
            removeFiles
        } = this.state;

        const newBoardTask = {
            num: num,
            writer: writer,
            password: password,
            category: category,
            subject: subject,
            content: content,
            files: fileCount
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
            fileCount,
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
                    fileCount={fileCount}
                    files={files}
                    registerFiles={registerFiles}
                    errors={errors}
                    onSubmit={this.onSubmit}
                    onChange={this.onChange}
                    fileState={this.fileState}
                />
            </Fragment>
        )
    }
}

boardTaskInsert.propTypes = {
    boardTaskInsert: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors
});

export default connect(
    mapStateToProps, 
    { boardTaskInsert }
)(BoardWriteInsert);