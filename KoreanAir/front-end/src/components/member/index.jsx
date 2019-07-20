import React, { Component, Fragment } from 'react';
import LoginModal from "./loginModal";
import SingUpModal from "./signUpModal";

class Member extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isModalOpen: false, 
        }
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

    render() {

        const { 
            txt,
            req
        } = this.props;

        const modalComponent = () => {
            // 오류 있습니다.
            switch(req) {
                case req === "login" :
                    return(
                        <LoginModal 
                            isOpen = {this.state.isModalOpen} 
                            close = {this.closeModal}
                        />
                    );
                case req === "singUp" : 
                    return(
                        <SingUpModal 
                            isOpen = {this.state.isModalOpen} 
                            close = {this.closeModal}
                        />
                    );
            }
        }

        return (
            <Fragment>
                <button onClick = {this.openModal}>{txt}</button>
                {
                    req === "login" ?
                    <LoginModal 
                        isOpen = {this.state.isModalOpen} 
                        close = {this.closeModal}
                    />
                    :
                    <SingUpModal 
                        isOpen = {this.state.isModalOpen} 
                        close = {this.closeModal}
                    />
                }
            </Fragment>
        )
    }
}

export default Member;