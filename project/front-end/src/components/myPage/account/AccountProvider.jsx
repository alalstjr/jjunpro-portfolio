import React, { Component, Fragment } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"

import LoginModal from "./modal/LoginModal"
import SingUpModal from "./modal/SignUpModal"

import { accountLogout } from "../../../actions/accountActions"

import {
    LoginBtn,
    SingUpBtn
} from "../style"

class AccountProvider extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isModalOpen: false,
            warning: {
                userId: false,
                nickname: false,
                password: false,
                passwordRe: false,
                email: false
            },
            warningText: {
                userId: "",
                nickname: "",
                password: "",
                passwordRe: "",
                email: ""
            },
            // 서버 전송상태 확인 (회원가입 완료 체크)
            singUp: false
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

    openModal = () => {
        this.initWarning();
        this.setState({
            isModalOpen: true
        });
    }
    
    closeModal = () => {
        this.initWarning();
        this.setState({
            isModalOpen: false
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
        this.props.accountLogout();
    }

    /*
     *  로그아웃 메소드입니다.
     */
    SingUpHandler = () => {
        this.setState({
            singUp: true
        });
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

            default :
                return false;
        }
    }

    render() {

        const { text, req } = this.props;
        const { warning, warningText, singUp } = this.state;

        let modalContainer;

        const modalComponent = () => {
            switch(req) {
                case "login" :
                    return(
                        <Fragment>
                            <LoginBtn onClick = {this.openModal}>{text}</LoginBtn>
                            <LoginModal 
                                isOpen = {this.state.isModalOpen} 
                                close = {this.closeModal}
                                warning = {warning}
                                warningText = {warningText}
                                warningSet={this.warningSet}
                                initWarning={this.initWarning}
                            />
                        </Fragment>
                    );
                case "singUp" : 
                    return(
                        <Fragment>
                            <SingUpBtn onClick = {this.openModal}>{text}</SingUpBtn>
                            <SingUpModal 
                                isOpen = {this.state.isModalOpen} 
                                close = {this.closeModal}
                                warning = {warning}
                                warningText = {warningText}
                                warningSet={this.warningSet}
                                initWarning={this.initWarning}
                                singUp={singUp}
                                SingUpHandler={this.SingUpHandler}
                            />
                        </Fragment>
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
    { accountLogout }
)(AccountProvider);