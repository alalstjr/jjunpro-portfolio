import React, { Component, Fragment } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import { Link } from "react-router-dom"
import ReactTransitionGroup from "react-addons-css-transition-group"

import LoginModal from "./modal/LoginModal"
import SingUpModal from "./modal/SignUpModal"

import { logoutAccount } from "../../../actions/accountActions"

import { SuccessWrap } from "../../../style/globalStyles"
import {
    LoginBtn,
    SingUpBtn
} from "../style"

class AccountProvider extends Component {
    constructor(props) {
        super(props);

        this.state = {
            loginModal: false,
            signUpModal: false,
            warning: {
                userId: false,
                nickname: false,
                password: false,
                passwordRe: false,
                email: false,
                success: false
            },
            warningText: {
                userId: "",
                nickname: "",
                password: "",
                passwordRe: "",
                email: "",
                success: ""
            },
        }
    }

    componentDidMount() {
        // esc keyDown addEvent
        document.addEventListener("keydown", this.escFunction, false);
    }

    componentWillUnmount() {
        // esc keyDown removeEvent
        document.removeEventListener("keydown", this.escFunction, false);
    }

    openModal = (target) => {
        this.initWarning();
        this.setState({
            [target]: true
        });
    }
    
    closeModal = () => {
        this.initWarning();
        this.setState({
            loginModal: false,
            signUpModal: false
        });
    }

    /*
     *  경고문 상태 초기화 메소드입니다.
     */
    initWarning = () => {
        this.setState(prevState => ({
            warning: {
                ...prevState.warning,
                userId: false,
                nickname: false,
                password: false,
                passwordRe: false,
                email: false
            }
        }));
    }

    /*
     *  success 상태 초기화 메소드입니다.
     */
    successWarning = () => {
        setTimeout(() => {
            this.setState(prevState => ({
                warning: {
                    ...prevState.warning,
                    success: false
                }
            }));
        }, 2000);
    }

    /*
     *  모달창 ESC 클릭 반응 메소드입니다.
     */
    escFunction = (event) => {
        if(event.keyCode === 27) {
            this.closeModal();
        }
    }

    /*
     *  로그아웃 메소드입니다.
     */
    logoutHandler = () => {
        this.props.logoutAccount();
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
            case "userId" : 
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        userId: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        userId: message
                    }
                }));
                break;
                
            case "nickname" : 
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        nickname: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        nickname: message
                    }
                }));
                break;

            case "password" : 
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        password: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        password: message
                    }
                }));
                break;

            case "passwordRe" :
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        passwordRe: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        passwordRe: message
                    }
                }));
                break;

            case "email" :
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        email: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        email: message
                    }
                }));
                break;

            case "success" :
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        success: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        success: message
                    }
                }));
                this.successWarning();
                break;

            default :
                return false;
        }
    }

    render() {

        // Props Init
        const { 
            text, 
            req 
        } = this.props;

        // State Init
        const { 
            warning, 
            warningText,
            loginModal,
            signUpModal
        } = this.state; 

        let modalContainer;

        const modalComponent = () => {
            switch(req) {
                case "login" :
                    return(
                        <Fragment>
                            <LoginBtn onClick = {() => this.openModal("loginModal")}>{text}</LoginBtn>
                        </Fragment>
                    );
                case "singUp" : 
                    return(
                        <Fragment>
                            <SingUpBtn onClick = {() => this.openModal("signUpModal")}>{text}</SingUpBtn>
                        </Fragment>
                    );
                case "myPage" : 
                    return(
                        <SingUpBtn>
                            <Link to="/mypage">마이페이지</Link>
                        </SingUpBtn>
                    );
                case "logout" : 
                    return(
                        <LoginBtn onClick={this.logoutHandler}>로그아웃</LoginBtn>
                    );
            }
        }
        
        modalContainer = modalComponent();

        return (
            <Fragment>
                {modalContainer}

                {/* Form Modal */}
                <LoginModal 
                    loginModal = {loginModal}
                    closeModal = {this.closeModal}
                    warning = {warning}
                    warningText = {warningText}
                    warningSet = {this.warningSet}
                    initWarning = {this.initWarning}
                />
                <SingUpModal 
                    signUpModal = {signUpModal}
                    closeModal = {this.closeModal}
                    warning = {warning}
                    warningText = {warningText}
                    warningSet={this.warningSet}
                    initWarning={this.initWarning}
                    openModal = {this.openModal}
                />

                <ReactTransitionGroup
                    transitionName={'Waring-anim'}
                    transitionEnterTimeout={200}
                    transitionLeaveTimeout={200}
                >
                {
                    // 회원가입 안내문
                    warning.success ?
                    <SuccessWrap>{warningText.success}</SuccessWrap>
                    :
                    null
                }
                </ReactTransitionGroup>
            </Fragment>
        )
    }
}

AccountProvider.propTypes = {
    user_info: PropTypes.object.isRequired,
}

const mapStateToProps = state => ({
    user_info: state.account.user_info
});

export default connect(
    mapStateToProps,
    { logoutAccount }
)(AccountProvider);