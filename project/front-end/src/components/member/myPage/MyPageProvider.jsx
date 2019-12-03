import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import ReactTransitionGroup from "react-addons-css-transition-group"

import { accountLoginCheck, accountGet, accountUpdate, accountPwdUpdate } from "../../../actions/accountActions"
import NormalHeader from "../../layout/header/normal/NormalHeader"
import Profile from "./profile/Profile"
import PwdChange from "./pwdChange/PwdChange"
import ActivityRecord from "./activityRecord/ActivityRecord"
import LikeRecord from "./likeRecord/LikeRecord"

import { WaringWrap, SuccessWrap } from "../../../style/globalStyles"
import {
    MyPageWrap,
    MyPageLeft,
    MyPageRight,
    MyPageList,
    MyPageWhite
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
                oldPassword: false,
                password: false,
                passwordRe: false,
                email: false,
                success: false
            },
            warningText: {
                nickname: "",
                oldPassword: "",
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

        // Props Init
        const { 
            history, 
            error 
        } = this.props;

        // 유저가 비로그인 상태로 접근할 경우 메인으로 경로 이동
        if(nextProps.user_info.data === undefined) {
            alert("회원만 이용 가능합니다.");
            history.push("/");
        }

        // {Server} 유효성 검사 출력 코드입니다.
        if(nextProps.error.data !== error.data) {
            if(nextProps.error.data.nickname) {
                this.warningSet("nickname", true, nextProps.error.data.nickname);
            }
            if(nextProps.error.data.email) {
                this.warningSet("email", true, nextProps.error.data.email);
            }
            if(nextProps.error.data.oldPassword) {
                this.warningSet("oldPassword", true, nextProps.error.data.oldPassword);
            }
            if(nextProps.error.data.passwordRe) {
                this.warningSet("passwordRe", true, nextProps.error.data.passwordRe);
            }
        }

        // DB 전송 최종 완료된후 실행되는 이벤트 코드입니다.
        if(nextProps.account_create.data === true) {
            this.warningSet("success", true, "수정이 완료되었습니다.");
            this.initWarning();
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
                this.initWarning();
                break;

            case "oldPassword" : 
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        oldPassword: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        oldPassword: message
                    }
                }));
                this.initWarning();
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
                this.initWarning();
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
                this.initWarning();
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
                this.initWarning();
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
                this.initWarning();
                break;

            default :
                return false;
        }
    }

    /*
     *  경고문 상태 초기화 메소드입니다.
     */
    initWarning = () => {
        setTimeout(() => {
            this.setState(prevState => ({
                warning: {
                    ...prevState.warning,
                    nickname: false,
                    oldPassword: false,
                    password: false,
                    passwordRe: false,
                    email: false,
                    success: false
                }
            }));
        }, 2000);
    }

    render() {

        // Props Init
        const { 
            accountGet,         // {id}의 유저의 정보를 가져오는 메소드
            account_get,        // {id}의 유저의 정보 변수
            accountUpdate,      // {id}의 유저의 기본정보를 수정하는 메소드
            accountPwdUpdate,   // {id}의 유저의 비밀번호를 수정하는 메소드
            account_create      // {id}의 유저의 수정 상태 정보 변수
        } = this.props;

        // State Init
        const { 
            page, 
            warning, 
            warningText
        } = this.state;

        return (
            <Fragment>
                <NormalHeader/>
                <MyPageWrap>
                    <MyPageLeft>
                        <ul>
                            <MyPageList active={page} onClick={() => this.handlePageChange("profile")}>프로필 편집</MyPageList>
                            <MyPageList active={page} onClick={() => this.handlePageChange("password")}>비밀번호 변경</MyPageList>
                            <MyPageList active={page} onClick={() => this.handlePageChange("repository")}>활동기록보기</MyPageList>
                            <MyPageList active={page} onClick={() => this.handlePageChange("likeRecord")}>좋아요한 푹찍</MyPageList>
                        </ul>
                    </MyPageLeft>
                    <MyPageRight>
                        <MyPageWhite>
                        {
                            page === "password" ?
                            <PwdChange
                                account_get = {account_get}
                                warningSet = {this.warningSet}
                                accountPwdUpdate = {accountPwdUpdate}
                                account_create = {account_create}
                            />
                            :
                            page === "repository" ?
                            <ActivityRecord/>
                            :
                            page === "likeRecord" ?
                            <LikeRecord/>
                            :
                            <Profile
                                accountGet = {accountGet}
                                account_get = {account_get}
                                warningSet = {this.warningSet}
                                accountUpdate = {accountUpdate}
                            />
                        }
                        </MyPageWhite>
                    </MyPageRight>
                </MyPageWrap>
                
                <ReactTransitionGroup
                    transitionName={'Waring-anim'}
                    transitionEnterTimeout={200}
                    transitionLeaveTimeout={200}
                >
                {
                    // 프로필 변경 안내문
                    warning.nickname ? 
                    <WaringWrap>{warningText.nickname}</WaringWrap>
                    :
                    warning.email ?
                    <WaringWrap>{warningText.email}</WaringWrap>
                    :
                    warning.success ?
                    <SuccessWrap>{warningText.success}</SuccessWrap>
                    :
                    // 비밀번호 변경 안내문
                    warning.oldPassword ?
                    <WaringWrap>{warningText.oldPassword}</WaringWrap>
                    :
                    warning.password ?
                    <WaringWrap>{warningText.password}</WaringWrap>
                    :
                    warning.passwordRe ?
                    <WaringWrap>{warningText.passwordRe}</WaringWrap>
                    :
                    null
                }
                </ReactTransitionGroup>
            </Fragment>
        )
    }
}

MyPageProvider.propTypes = {
    accountLoginCheck: PropTypes.func.isRequired,
    accountGet: PropTypes.func.isRequired,
    accountUpdate: PropTypes.func.isRequired,
    accountPwdUpdate: PropTypes.func.isRequired,
    error: PropTypes.object.isRequired,
    user_info: PropTypes.object.isRequired,
    account_get: PropTypes.object.isRequired,
    account_create: PropTypes.object.isRequired,
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
        accountUpdate,
        accountPwdUpdate
    }
)(MyPageProvider);