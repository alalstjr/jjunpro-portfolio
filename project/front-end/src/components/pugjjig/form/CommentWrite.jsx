import React, { Component } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import ReactTransitionGroup from "react-addons-css-transition-group"

import { pugjjigCommentInsert } from "../../../actions/PugjjigActions"

import { 
    WaringWrap
} from "../../../style/globalStyles"
import {
    Form,
    InputComment,
    CommentBtn,
    InputCommentWrap
} from "../style"

class CommentWrite extends Component {

    constructor(props){
        super(props);
    
        this.state = {
            // update id
            id: null,
            uniId: null,
            content: "",
            // Input 경고문
            warning: {
                content: false
            },
            warningText: {
                content: ""
            }
        }
    }

    componentDidMount() {

        // Props Init
        const { 
            uniId
        } = this.props;

        this.setState({
            uniId
        });
    }


    // Input Setup
    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    // Form Submit
    onSubmit = (e) => {
        e.preventDefault();

        const {
            id,
            uniId,
            content
        } = this.state;

        const commentData = {
            id,
            uniId,
            content
        };

        // {Client} 유효성 검사 출력 코드입니다.
        if(!commentData.uniId) {
            console.log("uniId 값이 존재하지 않습니다.");
            this.warningSet("content", true, "잘못된 접근입니다.");
            return false;
        }
        // {Client} 유효성 검사 출력 코드입니다.
        if(!commentData.content) {
            this.warningSet("content", true, "댓글을 작성해 주세요.");
            return false;
        }
        
        this.props.pugjjigCommentInsert(commentData);
    }

    /*
     *  target {String name}, state {boolean}, message {String}
     *  target, state 는 필수 값입니다.
     *  경고문의 상태와 경고문을 설정하는 메소드입니다.
     */
    warningSet = (target, state, message) => {

        // message undefined 값 체크
        message = (message === undefined) ? "" : message;
        
        switch(target) {
            case "content" : 
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        content: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        content: message
                    }
                }));
                this.initWarning();
                break;

            default :
                return false;
        }
    }

    /*
     *  경고문 상태 초기화 메소드입니다.
     */
    initWarning = () => {
        setTimeout(() => {
            this.setState(prevState => ({
                warning: {
                    ...prevState.warning,
                    content: false
                }
            }));
        }, 2000);
    }

    render() {

        // State Init
        const { 
            content,
            warning, 
            warningText
        } = this.state;

        return (
            <Form
                onSubmit={this.onSubmit}
            >
                <InputCommentWrap>
                    <InputComment
                        id = "content"
                        name = "content"
                        type = "text"
                        value = {content}
                        onChange = {this.onChange}
                        placeholder = "댓글 쓰기..."
                    />
                    <CommentBtn>
                        완료
                    </CommentBtn>
                </InputCommentWrap>

                {/* Write 안내문 */}
                <ReactTransitionGroup
                    transitionName={'Waring-anim'}
                    transitionEnterTimeout={200}
                    transitionLeaveTimeout={200}
                >
                {
                    warning.content ? 
                    <WaringWrap>{warningText.content}</WaringWrap>
                    :
                    null
                }
                </ReactTransitionGroup>
            </Form>
        )
    }
}

CommentWrite.propTypes = {
    pugjjigCommentInsert: PropTypes.func.isRequired,
    error: PropTypes.object.isRequired
  }
  
  
  const mapStateToProps = state => ({
    error: state.errors
  });
  
  export default connect(
    mapStateToProps, 
    { 
        pugjjigCommentInsert
    }
  )(CommentWrite);