import React, { Fragment, Component } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { memberTaskInsert } from "../../../actions/memberTaskActions";

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
    FormGroup
} from "../../../style/globalStyles";

class SingUpModal extends Component {
    constructor(props) {
        super(props);

        this.state = {
            userId: "",
            password: ""
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

        this.props.memberTaskInsert(account, this.props.history);
    }

    render(){
        const { isOpen, close } = this.props;
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
                                회원 가입
                            </Title>
                            <Content>
                                <FormGroup>
                                    <Formlabel>아이디</Formlabel>
                                    <Input                                    
                                        id="userId"
                                        name="userId"
                                        value={userId}
                                        onChange={this.onChange}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Formlabel>비밀번호</Formlabel>
                                    <Input
                                        id="password"
                                        name="password"
                                        value={password}
                                        onChange={this.onChange}
                                    />
                                </FormGroup>
                            </Content>
                            <div className="button-wrap">
                                <button 
                                    // onClick={close}
                                    type="submit"
                                >
                                    Confirm
                                </button>
                            </div>
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
  
memberTaskInsert.propTypes = {
    memberTaskInsert: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors
});

export default connect(
    mapStateToProps, 
    { memberTaskInsert }
)(SingUpModal);