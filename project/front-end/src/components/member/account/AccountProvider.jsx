import React, {Component, Fragment} from "react";
import {connect} from "react-redux";
import PropTypes from "prop-types";
import ReactTransitionGroup from "react-addons-css-transition-group";

import LoginModal from "./modal/LoginModal";
import SignUpModal from "./modal/SignUpModal";

import {modalAccount} from "../../../actions/accountActions";

import {SuccessWrap} from "../../../style/globalStyles";
import {
    LoginBtn,
    SignUpBtn
} from "../style";

class AccountProvider extends Component {
    constructor(props) {
        super(props);

        this.state = {
            warning: {
                username: false,
                nickname: false,
                password: false,
                passwordRe: false,
                email: false,
                success: false
            },
            warningText: {
                username: "",
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
        const {modalAccount} = this.props;

        this.initWarning();
        modalAccount(target, true);
    }

    closeModal = () => {
        const {modalAccount} = this.props

        this.initWarning();
        modalAccount(false);
    }

    /*
     *  경고문 상태 초기화 메소드입니다.
     */
    initWarning = () => {
        this.setState(prevState => ({
            warning: {
                ...prevState.warning,
                username: false,
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
        if (event.keyCode === 27) {
            this.closeModal();
        }
    }

    /*
     *  target {String name}, state {boolean}, message {String}
     *  target, state 는 필수 값입니다.
     *  경고문의 상태와 경고문을 설정하는 메소드입니다.
     */
    warningSet = (target, state, message) => {
        // message undefined 값 체크
        message = (message === undefined) ? "" : message;

        switch (target) {
            case "username" :
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        username: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        username: message
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
            modal_account
        } = this.props;

        // State Init
        const {
            warning,
            warningText
        } = this.state;

        return (
            <Fragment>
                {/* Form Modal */}
                <LoginModal
                    loginModal={modal_account.login}
                    closeModal={this.closeModal}
                    warning={warning}
                    warningText={warningText}
                    warningSet={this.warningSet}
                    initWarning={this.initWarning}
                />
                <SignUpModal
                    signUpModal={modal_account.sign_up}
                    closeModal={this.closeModal}
                    warning={warning}
                    warningText={warningText}
                    warningSet={this.warningSet}
                    initWarning={this.initWarning}
                    openModal={this.openModal}
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
    modalAccount: PropTypes.func.isRequired,
    modal_account: PropTypes.object.isRequired,
    user_info: PropTypes.object.isRequired,
}

const mapStateToProps = state => ({
    modal_account: state.account.modal_account,
    user_info: state.account.user_info
});

export default connect(
    mapStateToProps,
    {modalAccount}
)(AccountProvider);