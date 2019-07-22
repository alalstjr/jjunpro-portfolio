import React, { Component, Fragment } from 'react'
import { connect } from 'react-redux'

import LoginModal from "./loginModal"
import SingUpModal from "./signUpModal"

import { accountLogout } from "../../actions/accountActions"

class User extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isModalOpen: false, 
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
        this.setState({ 
            isModalOpen: true 
        });
    }
    
    closeModal = () => {
        this.setState({ 
            isModalOpen: false 
        }); 
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

        const { 
            text,
            req
        } = this.props;

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

const mapStateToProps = state => ({
    account: state.account.userInfo
});


export default connect(
    mapStateToProps,
    { accountLogout }
)(User);