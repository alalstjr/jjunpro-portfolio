import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { accountLoginCheck } from "../../../../actions/accountActions"
import AccountBtn from "../AccountProvider"

import { 
    LoginWrap,
    LoginLogo
} from "../../style"

class LoginBox extends Component {

    componentDidMount() {
        this.props.accountLoginCheck();
    }

    render() {

        const { initSearch } = this.props;

        return (
            <LoginWrap>
                <LoginLogo initSearch = {initSearch} >푹찍</LoginLogo>
                {
                    this.props.user_info.data === undefined ?
                    <Fragment>
                        <AccountBtn
                            text = "로그인"
                            req = "login"
                        />
                        <AccountBtn
                            text = "푹찍 회원가입"
                            req = "singUp"
                        />
                    </Fragment>
                    :
                    <Fragment>
                        <AccountBtn
                            text = "로그아웃"
                            req = "logout"
                        />
                        <AccountBtn
                            text = "마이페이지"
                            req = "myPage"
                        />
                    </Fragment>
                }
            </LoginWrap>
        )
    }
}

LoginBox.propTypes = {
    error: PropTypes.object.isRequired,
    user_info: PropTypes.object.isRequired
}
  
  
const mapStateToProps = state => ({
    error: state.errors,
    user_info: state.account.user_info
});

export default connect(
    mapStateToProps,
    { accountLoginCheck }
)(LoginBox);