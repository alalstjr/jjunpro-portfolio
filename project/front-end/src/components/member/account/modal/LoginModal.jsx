import React, {Fragment, Component} from 'react';
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {loginAccount, modalAccount} from "../../../../actions/accountActions";

import ReactTransitionGroup from 'react-addons-css-transition-group';

import {
    ModalOverlay,
    Modal,
    Title,
    Content
} from "../../style";

import {
    Form,
    InputClean,
    Formlabel,
    FormGroup,
    SubmitBtn,
    InputWarning
} from "../../../../style/globalStyles";

class LoginModal extends Component {
    constructor(props) {
        super(props);

        this.state = {
            userId: "",
            password: ""
        }
    }

    // Lifecycle Methods
    componentWillReceiveProps(nextProps) {

        // Props Init
        const {
            error,
            warningSet,
            user_info,
            closeModal
        } = this.props;

        // {Server} 유효성 검사 출력 코드입니다.
        if (nextProps.error.data !== error.data) {
            if (nextProps.error.data.AuthenticationError) {
                warningSet("userId", true, nextProps.error.data.AuthenticationError);
            }
        }

        if (nextProps.user_info !== user_info) {
            if (nextProps.user_info) {
                closeModal();
            }
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

        // State Init
        const {
            userId,
            password
        } = this.state;

        // Value Init
        const account = {
            userId,
            password
        };

        // {Client} 유효성 검사 출력 코드입니다.
        if (!account.userId) {
            this.props.warningSet("userId", true, "아이디는 필수로 작성해야 합니다.");
            return false;
        }
        if (!account.password) {
            this.props.warningSet("password", true, "비밀번호는 필수로 작성해야 합니다.");
            return false;
        }

        this.props.loginAccount(account, this.props.history);
    }

    render() {
        // Props Init
        const {
            loginModal,
            closeModal,
            warning,
            warningText,
            initWarning,
            modalAccount
        } = this.props;

        // State Init
        const {
            userId,
            password
        } = this.state;

        return (
            <Fragment>
                {
                    loginModal ?
                        <ReactTransitionGroup
                            transitionName={'Modal-anim'}
                            transitionEnterTimeout={200}
                            transitionLeaveTimeout={200}
                        >
                            <ModalOverlay onClick={closeModal}/>
                            <Modal>
                                <Form
                                    onSubmit={this.onSubmit}
                                >
                                    <Title>
                                        로그인
                                    </Title>
                                    <Content>
                                        <FormGroup>
                                            <Formlabel>아이디</Formlabel>
                                            <InputClean
                                                id="userId"
                                                name="userId"
                                                type="text"
                                                value={userId}
                                                onChange={this.onChange}
                                                onKeyDown={initWarning}
                                            />
                                            {
                                                // 아이디 입력 경고문
                                                warning.userId ?
                                                    <InputWarning
                                                        active={warningText.userId}
                                                    >
                                                        {warningText.userId}
                                                    </InputWarning>
                                                    :
                                                    null
                                            }
                                        </FormGroup>
                                        <FormGroup>
                                            <Formlabel>비밀번호</Formlabel>
                                            <InputClean
                                                id="password"
                                                name="password"
                                                type="password"
                                                value={password}
                                                onChange={this.onChange}
                                                onKeyDown={initWarning}
                                                autocomplete="current-password"
                                            />
                                            {
                                                // 비밀번호 입력 경고문
                                                warning.password ?
                                                    <InputWarning
                                                        active={warningText.password}
                                                    >
                                                        {warningText.password}
                                                    </InputWarning>
                                                    :
                                                    null
                                            }
                                            {
                                                // 인증 실패 경고문
                                                warning.authentication ?
                                                    <InputWarning
                                                        active={warningText.authenticationFail}
                                                    >
                                                        {warningText.authenticationFail}
                                                    </InputWarning>
                                                    :
                                                    null
                                            }
                                        </FormGroup>
                                    </Content>
                                    <SubmitBtn
                                        type="submit"
                                    >
                                        로그인
                                    </SubmitBtn>
                                    <SubmitBtn
                                        type="button"
                                        onClick={() => modalAccount("sign_up", true)}
                                    >
                                        회원가입
                                    </SubmitBtn>
                                </Form>
                            </Modal>
                        </ReactTransitionGroup>
                        :
                        <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200}
                                              transitionLeaveTimeout={200}/>
                }
            </Fragment>
        )
    }
}

loginAccount.propTypes = {
    loginAccount: PropTypes.func.isRequired,
    modalAccount: PropTypes.func.isRequired,
    error: PropTypes.object.isRequired,
    user_info: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    error: state.errors,
    user_info: state.account.user_info
});

export default connect(
    mapStateToProps,
    {loginAccount, modalAccount}
)(LoginModal);