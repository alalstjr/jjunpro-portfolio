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
            password: "",
            warning : {
                userId: false,
                password: false
            },
            warningText : {
                userId: "아이디는 필수로 작성해야 합니다.",
                password: "비밀번호는 필수로 작성해야 합니다."
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

        const { 
            userId,
            password
        } = this.state;

        const account = {
            userId,
            password
        };

        if(!account.userId) {
            this.setState({
                warning: {
                    userId: true
                }
            });
            return false;
        }
        if(!account.password) {
            this.setState({
                warning: {
                    password: true
                }
            });
            return false;
        }

        this.props.accountLogin(account, this.props.history);
    }

    render(){
        const { isOpen, close } = this.props;
        const { userId, password, warning, warningText } = this.state;

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
                                    />
                                    {
                                        warning.userId ? 
                                        <InputWarning
                                            transitionName={'Warning-anim'}
                                            transitionEnterTimeout={200}
                                            transitionLeaveTimeout={200}
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
                                    />
                                    {
                                        warning.password ? 
                                        <InputWarning>{warningText.password}</InputWarning>
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
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors
});

export default connect(
    mapStateToProps, 
    { accountLogin }
)(LoginModal);