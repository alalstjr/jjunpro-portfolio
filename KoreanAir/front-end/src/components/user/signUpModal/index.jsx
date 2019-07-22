import React, { Fragment, Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { accountInsert } from "../../../actions/accountActions";

import ReactTransitionGroup from 'react-addons-css-transition-group';

import { 
    ModalOverlay, 
    Modal,
    Title,
    Content
} from "../style";

import {
    Form,
    Input,
    Formlabel,
    FormGroup,
    SubmitBtn
} from "../../../style/globalStyles";

class SingUpModal extends Component {
    constructor(props) {
        super(props);

        this.state = {
            userId: "",
            password: "",
            username: ""
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
            password,
            username
        } = this.state;

        const account = {
            userId,
            password,
            username
        };

        this.props.accountInsert(account, this.props.history);
    }

    render(){
        const { isOpen, close } = this.props;
        const { userId, password, username } = this.state;

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
                                    <Input
                                        id="username"
                                        name="username"
                                        type="text"
                                        value={username}
                                        onChange={this.onChange}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Formlabel>아이디</Formlabel>
                                    <Input                                    
                                        id="userId"
                                        name="userId"
                                        type="text"
                                        value={userId}
                                        onChange={this.onChange}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Formlabel>비밀번호</Formlabel>
                                    <Input
                                        id="password"
                                        name="password"
                                        type="password"
                                        value={password}
                                        onChange={this.onChange}
                                    />
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
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors
});

export default connect(
    mapStateToProps, 
    { accountInsert }
)(SingUpModal);