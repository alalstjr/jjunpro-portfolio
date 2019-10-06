import React, { Component, Fragment } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import LoginModal from "./loginModal";
import SingUpModal from "./signUpModal";

import { accountLogout } from "../../actions/accountActions";

class User extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isModalOpen: false,
            warning : {
                userId: false,
                password: false,
                passwordRe: false,
                username: false,
                authentication: false
            },
            warningText : {
                userId: "아이디는 필수로 작성해야 합니다.",
                password: "비밀번호는 필수로 작성해야 합니다.",
                passwordRe: "비밀번호가 동일하지 않습니다.",
                username: "이름은 필수로 작성해야 합니다.",
                authenticationFail: "올바르지 않은 비밀번호를 입력했습니다.",
                singUpIdFail: "이미 존재하는 아이디 입니다."
            }
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
        // 모달창을 열고 닫을때 설정 초기화
        this.setState(prevState => ({
            warning: {
                ...prevState.warning,
                userId: false,
                password: false,
                username: false,
                passwordRe: false,
                authentication: false
            },
            isModalOpen: true
        }));
    }
    
    closeModal = () => {
        // 모달창을 열고 닫을때 설정 초기화
        this.setState(prevState => ({
            warning: {
                ...prevState.warning,
                userId: false,
                password: false,
                username: false,
                passwordRe: false,
                authentication: false
            },
            isModalOpen: false
        }));
    }

    warningSetUserId = (set) => {
        const userId = set ? true : false;

        this.setState(prevState => ({
            warning: {
                ...prevState.warning,
                userId
            }
        }));
    }
    warningSetPassword = (set) => {
        const password = set ? true : false;

        this.setState(prevState => ({
            warning: {
                ...prevState.warning,
                password
            }
        }));
    }
    warningSetPasswordRe = (set) => {
        const passwordRe = set ? true : false;

        this.setState(prevState => ({
            warning: {
                ...prevState.warning,
                passwordRe
            }
        }));
    }
    warningSetUsername = (set) => {
        const username = set ? true : false;

        this.setState(prevState => ({
            warning: {
                ...prevState.warning,
                username
            }
        }));
    }
    warningSetAuthentication = (set) => {
        const authentication = set ? true : false;
        
        this.setState(prevState => ({
            warning: {
                ...prevState.warning,
                authentication
            }
        }));
    }

    escFunction = (event) => {
        if(event.keyCode === 27) {
            this.closeModal();
        }
    }

    logoutHandler = () => {
        this.props.accountLogout();
    }

    render() {

        const { text, req } = this.props;
        const { warning, warningText } = this.state;

        let modalContainer;

        const modalComponent = () => {
            switch(req) {
                case "login" :
                    return(
                        <Fragment>
                            <button onClick = {this.openModal}>{text}</button>
                            <LoginModal 
                                isOpen = {this.state.isModalOpen} 
                                close = {this.closeModal}
                                warning = {warning}
                                warningText = {warningText}
                                warningSetUserId = {this.warningSetUserId}
                                warningSetPassword = {this.warningSetPassword}
                                warningSetAuthentication = {this.warningSetAuthentication}
                            />
                        </Fragment>
                    );
                case "singUp" : 
                    return(
                        <Fragment>
                        <button onClick = {this.openModal}>{text}</button>
                            <SingUpModal 
                                isOpen = {this.state.isModalOpen} 
                                close = {this.closeModal}
                                warning = {warning}
                                warningText = {warningText}
                                warningSetUserId = {this.warningSetUserId}
                                warningSetPassword = {this.warningSetPassword}
                                warningSetPasswordRe = {this.warningSetPasswordRe}
                                warningSetUsername = {this.warningSetUsername}
                                warningSetAuthentication = {this.warningSetAuthentication}
                            />
                        </Fragment>
                    );
                case "logout" : 
                    return(
                        <button onClick={this.logoutHandler}>로그아웃</button>
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

User.propTypes = {
    account: PropTypes.object.isRequired,
}

const mapStateToProps = state => ({
    account: state.account.userInfo
});

export default connect(
    mapStateToProps,
    { accountLogout }
)(User);