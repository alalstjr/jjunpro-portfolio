import React, { Component, Fragment } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { insertBoardTask } from "../../../actions/boardTaskActions";

import BoardHead from "../header";
import ListBtn from "../bottom/listBtn"; 
import FileDrop from "../../widget/fileDrop";
import FilePondBox from "../../widget/filePond";

import { Form, FormGroup, Formlabel, Container, Input, Textarea, SelectBox } from "../../../style/globalStyles";

class BoardWrite extends Component {
    constructor(props) {
        super(props);
        this.state = {
            writer: "작성자",
            password: "비밀번호",
            category: "",
            subject: "",
            content: "",
            fileCount: 0,
            files: [],
            errors: {}
        };
    }

    // Submit Error Check
    componentWillReceiveProps(nextProps) {
        if(nextProps.errors) {
            this.setState({
                errors: nextProps.errors
            });
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
            files: target.map(fileItem => fileItem),
            fileCount: target.length
        });
    }

    // Form Submit
    onSubmit = (e) => {
        e.preventDefault();

        const { 
            writer, 
            password, 
            category, 
            subject, 
            content,
            files,
            fileCount
        } = this.state;

        const newBoardTask = {
            writer: writer,
            password: password,
            category: category,
            subject: subject,
            content: content,
            files: fileCount
        };

        this.props.insertBoardTask(newBoardTask, this.props.history, files);
    }

    render() {
        return (
            <Fragment>
                <BoardHead/>
                <Container>
                    <form
                        onSubmit={this.onSubmit}
                        method="post" 
		                enctype="multipart/form-data"
                    >
                        <Input 
                            id="writer"
                            type="hidden"
                            name="writer"
                            value={this.state.subject}
                        />
                        <Input 
                            id="password"
                            type="hidden"
                            name="password"
                            value={this.state.password}
                        />
                        <FormGroup>
                            <Formlabel htmlFor="category">분류</Formlabel>
                            <SelectBox
                                id="category"
                                name="category"
                                value={this.state.category}
                                onChange={this.onChange}
                            >
                                <option value="none">분류를 선택해주세요.</option>
                                <option value="진행중인 이벤트">진행중인 이벤트</option>
                                <option value="마감된 이벤트">마감된 이벤트</option>
                            </SelectBox>
                        </FormGroup>
                        <FormGroup>
                            <Formlabel htmlFor="subject">제목</Formlabel>
                            <Input 
                                id="subject"
                                type="text"
                                name="subject"
                                value={this.state.subject}
                                onChange={this.onChange}
                            />
                        </FormGroup>
                        <FormGroup>
                            <Formlabel htmlFor="content">내용</Formlabel>
                            <Textarea
                                id="content"
                                name="content"
                                value={this.state.content}
                                onChange={this.onChange}
                            />
                        </FormGroup>
                        <FormGroup>
                            <Formlabel htmlFor="file">이미지</Formlabel>
                            <FileDrop
                                fileState={this.fileState}
                            />
                        </FormGroup>
                        <ListBtn
                            option={"write"}
                        />
                    </form>
                </Container>
            </Fragment>
        )
    }
}

BoardWrite.propTypes = {
    insertBoardTask: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors
});

export default connect(
    mapStateToProps, 
    { insertBoardTask }
)(BoardWrite);