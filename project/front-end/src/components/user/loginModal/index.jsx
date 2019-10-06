import React, { Fragment, Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { accountLogin } from "../../../actions/accountActions";

import ReactTransitionGroup from 'react-addons-css-transition-group';

import { 
    ModalOverlay, 
    Modal,
    Title,
    Content
} from "../style";

import {
    Form,
    InputClean,
    Formlabel,
    FormGroup,
    SubmitBtn,
    InputWarning
} from "../../../style/globalStyles";

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
            password
        } = this.state;

        const account = {
            userId,
            password
        };

        if(!account.userId) {
            this.props.warningSetUserId(true);
            return false;
        }
        if(!account.password) {
            this.props.warningSetPassword(true);
            return false;
        }

        this.props.accountLogin(account, this.props.history);
    }

    // Input값 존재여부 체크 후 warning 상태 관리
    valueCheck = (e) => {
        if(this.props.warning.authentication === true) {
            this.props.warningSetAuthentication(false);
        }

        if(!e.target.value) {
            switch(e.target.name) {    
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
        const { userId, password } = this.state;

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
                                        onKeyDown={this.valueCheck}
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
                                        onKeyDown={this.valueCheck}
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
  
accountLogin.propTypes = {
    accountLogin: PropTypes.func.isRequired,
    error: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    error: state.errors
});

export default connect(
    mapStateToProps, 
    { accountLogin }
)(LoginModal);