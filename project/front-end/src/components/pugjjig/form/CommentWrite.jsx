import React, { Component, Fragment } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import ReactTransitionGroup from "react-addons-css-transition-group";

import { modalAccount } from "../../../actions/accountActions";
import { insertComment } from "../../../actions/PugjjigActions";
import { USER_AUTH } from "../../../routes";

import { 
    WaringWrap
} from "../../../style/globalStyles";
import {
    Form,
    InputComment,
    CommentBtn,
    InputCommentWrap,
    NoneComment
} from "../style";

class CommentWrite extends Component {

    constructor(props){
        super(props);
    
        this.state = {
            // update id
            uniId: null,
            content: "",
            // Input 경고문
            warning: false,
            warningText: ""
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

    componentWillReceiveProps(nextProps) {

        // Props Init
        const { 
            pugjjig_comment
        } = this.props;

        // 댓글 {INSERT DATA} 배열 생성
        if(nextProps.pugjjig_comment !== pugjjig_comment) {
            this.setState({
                content: ""
            });
        }
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
            uniId,
            content
        } = this.state;

        const commentData = {
            uniId,
            content
        };

        // {Client} 유효성 검사 출력 코드입니다.
        if(!commentData.uniId) {
            console.log("uniId 값이 존재하지 않습니다.");
            this.warningSet(true, "잘못된 접근입니다.");
            return false;
        }
    
        if(!commentData.content || commentData.content === "") {
            this.warningSet(true, "댓글을 작성해 주세요.");
            return false;
        }
        
        this.props.insertComment(commentData);
    }

    /*
     *  target {String name}, state {boolean}, message {String}
     *  target, state 는 필수 값입니다.
     *  경고문의 상태와 경고문을 설정하는 메소드입니다.
     */
    warningSet = (warning, warningText) => {
        // message undefined 값 체크
        warningText = (warningText === undefined) ? "" : warningText;

        this.setState({
            warning,
            warningText
        });
        this.initWarning();
    }


    /*
     *  경고문 상태 초기화 메소드입니다.
     */
    initWarning = () => {
        setTimeout(() => {
            this.setState({
                warning: false,
                warningText: ""
            });
        }, 2000);
    }

    // Modal State
    openModal = () => {
        const { modalAccount } = this.props;
        
        // 로그인 사용자가 아닐경우
        if(!USER_AUTH()) {
            modalAccount("login", true); 
            return false;
        }
    }

    render() {

        // State Init
        const { 
            content,
            warning, 
            warningText
        } = this.state;

        return (
            <Fragment>
            {
                USER_AUTH() ?
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
                        warning ? 
                        <WaringWrap>{warningText}</WaringWrap>
                        :
                        null
                    }
                    </ReactTransitionGroup>
                </Form>
                :
                <InputCommentWrap>
                    <NoneComment onClick={this.openModal}>
                        푹찍 유저만 댓글을 작성할 수 있습니다.
                    </NoneComment>
                </InputCommentWrap>
            }
            </Fragment>
        )
    }
}

CommentWrite.propTypes = {
    insertComment: PropTypes.func.isRequired,
    modalAccount: PropTypes.func.isRequired,
    pugjjig_comment: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired
  }
  
  
  const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_comment: state.pugjjig.pugjjig_comment
  });
  
  export default connect(
    mapStateToProps, 
    { 
        insertComment,
        modalAccount
    }
  )(CommentWrite);