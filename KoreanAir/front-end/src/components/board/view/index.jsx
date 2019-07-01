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
        let contentResult = [];

        // 줄바꿈 치환 함수
        const contentReplac = (content) => {
            if(content != undefined) {
                // DB값의 \n(줄바꿈) 값을 기준으로 배열을 나눠 저장합니다.
                const contentArr = content.split('\n').map( (line, index) => (
                    <Fragment key={index}>{line}<br/></Fragment>
                ));
                
                // console.log(contentArr);
                
                // 변환된 값을 최종 배열에 넣습니다.
                for(let i = 0; i<contentArr.length; i++) {
                    contentResult.push(contentArr[i]);
                }

                // console.log(contentResult);

                return(
                    <Fragment>{contentResult}</Fragment>
                );
            }
        }

        // 이미지 파일 출력 함수
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
                        {contentReplac(board_task.content)}
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