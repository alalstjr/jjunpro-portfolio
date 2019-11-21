import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { accountLoginCheck, accountGet, accountUpdate } from "../../../actions/accountActions"
import NormalHeader from "../../layout/header/normal/NormalHeader"
import Profile from "./profile/Profile"

import { WaringWrap, SuccessWrap } from "../../../style/globalStyles"
import {
    MyPageWrap,
    MyPageLeft,
    MyPageRight,
    MyPageList
} from "../style"

class MyPageProvider extends Component {

    constructor() {
        super();

        this.state = {
            // 마이페이지 페이지상태
            page: "profile",
            // Input 경고문
            warning: {
                nickname: false,
                password: false,
                passwordRe: false,
                email: false,
                success: false
            },
            warningText: {
                nickname: "",
                password: "",
                passwordRe: "",
                email: "",
                success: ""
            }
        }
    }

    componentDidMount() {
        // 유저의 로그인상태를 체크합니다.
        this.props.accountLoginCheck();
    }

    componentWillReceiveProps(nextProps) {
        // 유저가 비로그인 상태로 접근할 경우 메인으로 경로 이동
        if(nextProps.user_info.data === undefined) {
            alert("회원만 이용 가능합니다.");
            this.props.history.push("/");
        }

        // {Server} 유효성 검사 출력 코드입니다.
        if(nextProps.error.data !== this.props.error.data) {
            if(nextProps.error.data.nickname) {
                this.warningSet("nickname", true, nextProps.error.data.nickname);
            }
            if(nextProps.error.data.email) {
                this.warningSet("email", true, nextProps.error.data.email);
            }
        }

        // 회원가입이 최종 완료된후 실행되는 이벤트 코드입니다.
        if(nextProps.account_create.data === true) {
            this.warningSet("success", true, "프로필 수정이 완료되었습니다.");
        }
    }

    handlePageChange = (page) => {
        this.setState({
            page
        });
    }

    /*
     *  target {String name}, state {boolean}, message {String}
     *  target, state 는 필수 값입니다.
     *  경고문의 상태와 경고문을 설정하는 메소드입니다.
     */
    warningSet = (target, state, message) => {

        // message undefined 값 체크
        message = (message === undefined) ? "" : message;
        
        switch(target) {
            case "nickname" : 
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        nickname: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        nickname: message
                    }
                }));
                break;

            case "password" : 
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        password: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        password: message
                    }
                }));
                break;

            case "passwordRe" :
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        passwordRe: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        passwordRe: message
                    }
                }));
                break;

            case "email" :
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        email: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        email: message
                    }
                }));
                break;

            case "success" :
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        success: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        success: message
                    }
                }));
                break;

            default :
                return false;
        }
    }

    /*
     *  경고문 상태 초기화 메소드입니다.
     */
    initWarning = () => {
        this.setState(prevState => ({
            warning: {
                ...prevState.warning,
                nickname: false,
                password: false,
                passwordRe: false,
                email: false,
                success: false
            }
        }));
    }

    render() {

        const { accountGet, account_get, accountUpdate } = this.props;
        const { page, warning, warningText } = this.state;

        return (
            <Fragment>
                <NormalHeader/>
                <MyPageWrap>
                    <MyPageLeft>
                        <ul>
                            <MyPageList active={page} onClick={() => this.handlePageChange("profile")}>프로필 편집</MyPageList>
                            <MyPageList active={page} onClick={() => this.handlePageChange("password")}>비밀번호 변경</MyPageList>
                            <MyPageList active={page} onClick={() => this.handlePageChange("repository")}>활동기록보기</MyPageList>
                        </ul>
                    </MyPageLeft>
                    <MyPageRight>
                        {
                            page === "password" ?
                            "asd"
                            :
                            page === "repository" ?
                            "asd"
                            :
                            <Profile
                                accountGet = {accountGet}
                                account_get = {account_get}
                                warningSet = {this.warningSet}
                                initWarning = {this.initWarning}
                                accountUpdate = {accountUpdate}
                            />
                        }
                    </MyPageRight>
                </MyPageWrap>
                {
                    warning.nickname ? 
                    <WaringWrap>{warningText.nickname}</WaringWrap>
                    :
                    warning.email ?
                    <WaringWrap>{warningText.email}</WaringWrap>
                    :
                    warning.success ?
                    <SuccessWrap>{warningText.success}</SuccessWrap>
                    :
                    null
                }
            </Fragment>
        )
    }
}

MyPageProvider.propTypes = {
    accountLoginCheck: PropTypes.func.isRequired,
    accountGet: PropTypes.func.isRequired,
    accountUpdate: PropTypes.func.isRequired,
    error: PropTypes.object.isRequired,
    user_info: PropTypes.object.isRequired,
    account_get: PropTypes.object.isRequired,
    account_create: PropTypes.object.isRequired
}
  
const mapStateToProps = state => ({
    error: state.errors,
    user_info: state.account.user_info,
    account_get: state.account.account_get,
    account_create: state.account.account_create
});

export default connect(
    mapStateToProps,
    { 
        accountLoginCheck,
        accountGet,
        accountUpdate
    }
)(MyPageProvider);