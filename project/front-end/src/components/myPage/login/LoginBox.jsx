import React, { Component } from "react"
import LoginBtn from "./LoginBtn"


import { 
    LoginWrap,
    LoginLogo
} from "../style"

class LoginBox extends Component {
    render() {
        return (
            <LoginWrap>
                <LoginLogo>푹찍</LoginLogo>
                <LoginBtn
                    text = "로그인"
                    req = "login"
                />
                <LoginBtn
                    text = "푹찍 회원가입"
                    req = "singUp"
                />
            </LoginWrap>
        )
    }
}

export default LoginBox;