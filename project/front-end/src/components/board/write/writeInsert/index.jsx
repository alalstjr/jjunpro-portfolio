import React, { Component, Fragment } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { boardTaskInsert } from "../../../../actions/boardTaskActions";

import BoardWrite from "../boardWrite/index";

class BoardWriteInsert extends Component {
    constructor(props) {
        super(props);
        this.state = {
            uniSubject: "",
            uniContent: "",
            uniName: "",
            uniTag: [],
            uniLocal: "",
            uniStar: 1,
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
            uniSubject,
            uniContent,
            uniName,
            uniTag,
            uniLocal,
            uniStar,
            files,
            fileCount,
            removeFiles
        } = this.state;

        const newPugjjig = {
            uniSubject,
            uniContent,
            uniName,
            uniTag,
            uniLocal,
            uniStar,
            files: fileCount
        };

        this.props.boardTaskInsert(newPugjjig, this.props.history, files, removeFiles);
    }

    render() {

        const {
            uniSubject,
            uniContent,
            uniName,
            uniTag,
            uniLocal,
            uniStar,
            fileCount,
            files,
            registerFiles,
            errors
        } = this.state;

        return (
            <Fragment>
                <BoardWrite
                    uniSubject={uniSubject}
                    uniContent={uniContent}
                    uniName={uniName}
                    uniTag={uniTag}
                    uniLocal={uniLocal}
                    uniStar={uniStar}
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