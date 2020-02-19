import React, {Component, Fragment} from "react";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {Link} from "react-router-dom";
import {accountLoginCheck, logoutAccount, modalAccount} from "../../../../actions/accountActions";
import {USER_AUTH} from "../../../../routes";

import {
    LoginWrap,
    LoginLogo,
    SignUpBtn,
    LoginBtn
} from "../../style"

class LoginBox extends Component {

    componentDidMount() {
        this.props.accountLoginCheck();
    }

    /*
     *  로그아웃 메소드입니다.
     */
    logoutHandler = () => {
        this.props.logoutAccount();
    }

    // Modal State
    openModal = (target) => {
        const {modalAccount} = this.props;

        // 로그인 사용자가 아닐경우
        if (!USER_AUTH()) {
            modalAccount(target, true);
            return false;
        }
    }

    render() {

        const {initSearch} = this.props;

        return (
            <LoginWrap>
                <LoginLogo initSearch={initSearch}>푹찍</LoginLogo>
                {
                    !USER_AUTH() ?
                        <Fragment>
                            <LoginBtn onClick={() => this.openModal("login")}>로그인</LoginBtn>
                            <SignUpBtn onClick={() => this.openModal("sign_up")}>회원가입</SignUpBtn>
                        </Fragment>
                        :
                        <Fragment>
                            <LoginBtn onClick={this.logoutHandler}>로그아웃</LoginBtn>
                            <SignUpBtn>
                                <Link to="/mypage">마이페이지</Link>
                            </SignUpBtn>
                        </Fragment>
                }
                <SignUpBtn>
                    <Link to="/square">리뷰검색</Link>
                </SignUpBtn>
            </LoginWrap>
        )
    }
}

LoginBox.propTypes = {
    logoutAccount: PropTypes.func.isRequired,
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
    {
        accountLoginCheck,
        logoutAccount,
        modalAccount
    }
)(LoginBox);