import React, { Component } from 'react'
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { insertAccount } from "../../../../actions/accountActions";
import SVGLoading from "../../../../static/svg/SVGLoading"

import { 
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

class SignUp extends Component {
    constructor(props) {
        super(props);

        this.state = {
            // Input Value
            userId: "",
            nickname: "",
            password: "",
            passwordRe: "",
            // Form State
            loding: false
        }
    }

    // Lifecycle Methods
    componentWillReceiveProps(nextProps) {

        // Props Init
        const {
            error,
            warningSet,
            closeModal,
            account_create 
        } = this.props;

        // {Server} 유효성 검사 출력 코드입니다.
        if(nextProps.error.data !== error.data) {
            if(nextProps.error.data.userId) {
                warningSet("userId", true, nextProps.error.data.userId);
            }
            if(nextProps.error.data.nickname) {
                warningSet("nickname", true, nextProps.error.data.nickname);
            }
            if(nextProps.error.data.password) {
                warningSet("password", true, nextProps.error.data.password);
            }

            this.setState({ loding: false });
        }
        
        // DB 전송 최종 완료된후 실행되는 이벤트 코드입니다.
        if(nextProps.account_create.data !== account_create.data) {
            if(nextProps.account_create.data === true) {
                this.setState({ loding: false });
                closeModal();
                warningSet("success", true, "회원가입이 완료되었습니다.");
                this.props.openModal("loginModal");
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

        // Props Init
        const { warningSet } = this.props;

        // State Init
        const { 
            userId,
            nickname,
            password,
            passwordRe,
            myUniversity,
            urlList
        } = this.state;

        // Value Init
        const account = {
            userId,
            nickname,
            password,
            passwordRe,
            myUniversity,
            urlList
        };

        // {Client} 유효성 검사 출력 코드입니다.
        if(!account.userId) {
            warningSet("userId", true, "아이디는 필수로 작성해야 합니다.");
            return false; 
        }
        if(!account.nickname) {
            warningSet("nickname", true, "닉네임은 필수로 작성해야 합니다.");
            return false;
        }
        if(!account.password) {
            warningSet("password", true, "비밀번호는 필수로 작성해야 합니다.");
            return false;
        }
        if(password !== passwordRe) {
            warningSet("passwordRe", true, "비밀번호가 동일하지 않습니다.");
            return false;
        }

        this.setState({
            loding: true
        });

        this.props.insertAccount(account, this.props.history);
    }

    render() {

        // Props Init
        const { 
            warning, 
            warningText, 
            initWarning
        } = this.props;

        // State Init
        const { 
            userId,
            nickname,
            password,
            passwordRe,
            loding
        } = this.state;

        return (
            <Form onSubmit={this.onSubmit}>
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
                            placeholder="영문 4글자이상 입력가능합니다."
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
                            autocomplete="current-password"
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
                            autocomplete="current-password"
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
                
                {/* 작성 완료 클릭시 Loding 대기화면 */}
                {
                    loding ? 
                    <SVGLoading/>
                    :
                    null
                }
            </Form>
        )
    }
}

SignUp.propTypes = {
    insertAccount: PropTypes.func.isRequired,
    error: PropTypes.object.isRequired,
    account_create: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    error: state.errors,
    account_create: state.account.account_create
});

export default connect(
    mapStateToProps, 
    { insertAccount }
)(SignUp);