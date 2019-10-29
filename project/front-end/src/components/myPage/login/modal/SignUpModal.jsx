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
            nickname: "",
            password: "",
            passwordRe: "",
            email: ""
        }
    }

    // Lifecycle Methods
    componentWillReceiveProps(nextProps) {

        // {Server} 유효성 검사 출력 코드입니다.
        if(nextProps.error.data !== this.props.error.data) {
            if(nextProps.error.data.userId) {
                this.props.warningSet("userId", true, nextProps.error.data.userId);
            }
            if(nextProps.error.data.nickname) {
                this.props.warningSet("nickname", true, nextProps.error.data.nickname);
            }
            if(nextProps.error.data.password) {
                this.props.warningSet("password", true, nextProps.error.data.password);
            }
            if(nextProps.error.data.email) {
                this.props.warningSet("email", true, nextProps.error.data.email);
            }
        }
        
        // 회원가입이 최종 완료된후 실행되는 이벤트 코드입니다.
        if(nextProps.account_create.data !== this.props.account_create.data) {
            console.log(nextProps.account_create);
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

        // state Init
        const { 
            userId,
            nickname,
            password,
            passwordRe,
            myUniversity,
            urlList,
            email
        } = this.state;

        // value Init
        const account = {
            userId,
            nickname,
            password,
            passwordRe,
            myUniversity,
            urlList,
            email
        };

        // {Client} 유효성 검사 출력 코드입니다.
        if(!account.userId) {
            this.props.warningSet("userId", true, "아이디는 필수로 작성해야 합니다.");
            return false; 
        }
        if(!account.nickname) {
            this.props.warningSet("nickname", true, "닉네임은 필수로 작성해야 합니다.");
            return false;
        }
        if(!account.password) {
            this.props.warningSet("password", true, "비밀번호는 필수로 작성해야 합니다.");
            return false;
        }
        if(password !== passwordRe) {
            this.props.warningSet("passwordRe", true, "비밀번호가 동일하지 않습니다.");
            return false;
        }
        if(!account.email) {
            this.props.warningSet("email", true, "이메일은 필수로 작성해야 합니다.");
            return false;
        }

        this.props.accountInsert(account, this.props.history);
    }

    render(){
        const { isOpen, close, warning, warningText, initWarning } = this.props;
        const { 
            userId,
            nickname,
            password,
            passwordRe,
            email
        } = this.state;

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
                                    <Formlabel>닉네임</Formlabel>
                                    <InputClean
                                        id="nickname"
                                        name="nickname"
                                        type="text"
                                        value={nickname}
                                        onChange={this.onChange}
                                        onKeyDown={initWarning}
                                    />
                                    {
                                        warning.nickname ? 
                                        <InputWarning
                                            active={warningText.nickname}
                                        >
                                            {warningText.nickname}
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
                                        onKeyDown={initWarning}
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
                                <FormGroup>
                                    <Formlabel>이메일</Formlabel>
                                    <InputClean
                                        id="email"
                                        name="email"
                                        type="text"
                                        value={email}
                                        onChange={this.onChange}
                                        onKeyDown={initWarning}
                                    />
                                    {
                                        warning.email ? 
                                        <InputWarning
                                            active={warningText.email}
                                        >
                                            {warningText.email}
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
    error: PropTypes.object.isRequired,
    account_create: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    error: state.errors,
    account_create: state.account.account_create
});

export default connect(
    mapStateToProps, 
    { accountInsert }
)(SingUpModal);