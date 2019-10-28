import React, { Fragment, Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { accountInsert } from "../../../../actions/accountActions";

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

class SingUpModal extends Component {
    constructor(props) {
        super(props);

        this.state = {
            userId: "",
            password: "",
            passwordRe: "",
            username: ""
        }
    }

    // Lifecycle Methods
    componentWillReceiveProps(nextProps) {
        if(nextProps.error.AuthenticationError && !this.props.warning.authentication) {
            this.warningSetAuthentication();
        }
    }
    // componentWillReceiveProps 무한루프 방지 함수에 담아서 사용
    warningSetAuthentication = () => {
        this.props.warningSetAuthentication(true);
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
            userId,
            password,
            passwordRe,
            username
        } = this.state;

        const account = {
            userId,
            password,
            username
        };

        if(!account.username) {
            this.props.warningSetUsername(true);
            return false;
        }
        if(!account.userId) {
            this.props.warningSetUserId(true);
            return false;
        }
        if(!account.password) {
            this.props.warningSetPassword(true);
            return false;
        }
        if(password !== passwordRe) {
            this.props.warningSetPasswordRe(true);
            return false;
        }

        this.props.accountInsert(account, this.props.history);
    }

    // Input값 존재여부 체크 후 warning 종합 상태 관리
    valueCheck = (e) => {
        // 유저 인증 경고문 warning 상태 관리
        if(this.props.warning.authentication === true) {
            this.props.warningSetAuthentication(false);
        }

        // passwordRe 경고문 warning 상태 관리
        if(this.state.password === this.state.passwordRe) {
            this.props.warningSetPasswordRe(false);
        }

        // account 유효성 검사 경고문 warning 상태 관리
        if(!e.target.value) {
            switch(e.target.name) {
                case "username" :
                    this.props.warningSetUsername(false);    
                
                case "userId" :
                    this.props.warningSetUserId(false);

                case "password" :
                    this.props.warningSetPassword(false);

                default :
                    return false;
            }
        }
    }

    render(){
        const { isOpen, close, warning, warningText } = this.props;
        const { userId, password, passwordRe, username } = this.state;

        return (
            <Fragment>
                {
                    isOpen ?
                    <ReactTransitionGroup
                        transitionName={'Modal-anim'}
                        transitionEnterTimeout={200}
                        transitionLeaveTimeout={200}
                    >
                    <ModalOverlay onClick={close} />
                    <Modal>
                        <Form
                            onSubmit={this.onSubmit}
                        >
                            <Title>
                                회원 가입
                            </Title>
                            <Content>
                                <FormGroup>
                                    <Formlabel>이름</Formlabel>
                                    <InputClean
                                        id="username"
                                        name="username"
                                        type="text"
                                        value={username}
                                        onChange={this.onChange}
                                        onKeyDown={this.valueCheck}
                                    />
                                    {
                                        warning.username ? 
                                        <InputWarning
                                            active={warningText.username}
                                        >
                                            {warningText.username}
                                        </InputWarning>
                                        : 
                                        null
                                    }
                                </FormGroup>
                                <FormGroup>
                                    <Formlabel>아이디</Formlabel>
                                    <InputClean                                    
                                        id="userId"
                                        name="userId"
                                        type="text"
                                        value={userId}
                                        onChange={this.onChange}
                                        onKeyDown={this.valueCheck}
                                    />
                                    {
                                        warning.userId ? 
                                        <InputWarning
                                            active={warningText.userId}
                                        >
                                            {warningText.userId}
                                        </InputWarning>
                                        : 
                                        null
                                    }
                                   {
                                        // 이미 존재하는 아이디 경고문
                                        warning.authentication ? 
                                        <InputWarning
                                            active={warningText.singUpIdFail}
                                        >
                                            {warningText.singUpIdFail}
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
                                        onKeyDown={this.valueCheck}
                                    />
                                    {
                                        warning.password ? 
                                        <InputWarning
                                            active={warningText.password}
                                        >
                                            {warningText.password}
                                        </InputWarning>
                                        : 
                                        null
                                    }
                                </FormGroup>
                                <FormGroup>
                                    <Formlabel>비밀번호 확인</Formlabel>
                                    <InputClean
                                        id="passwordRe"
                                        name="passwordRe"
                                        type="password"
                                        value={passwordRe}
                                        onChange={this.onChange}
                                        onKeyDown={this.valueCheck}
                                    />
                                    {
                                        warning.passwordRe ? 
                                        <InputWarning
                                            active={warningText.passwordRe}
                                        >
                                            {warningText.passwordRe}
                                        </InputWarning>
                                        : 
                                        null
                                    }
                                </FormGroup>
                            </Content>
                            <SubmitBtn
                                type="submit"
                            >
                                    회원가입
                            </SubmitBtn>
                        </Form>
                    </Modal>
                    </ReactTransitionGroup>
                    :
                    <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200} transitionLeaveTimeout={200} />
                }
            </Fragment>
        )
    }
}
  
accountInsert.propTypes = {
    accountInsert: PropTypes.func.isRequired,
    error: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    error: state.errors
});

export default connect(
    mapStateToProps, 
    { accountInsert }
)(SingUpModal);