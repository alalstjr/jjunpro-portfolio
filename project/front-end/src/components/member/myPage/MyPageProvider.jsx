import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import ReactTransitionGroup from "react-addons-css-transition-group"

import { accountLoginCheck, getAccountUserId, updateAccount, updateAccountPwdId } from "../../../actions/accountActions"
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
            success: false,
            warning: false,
            warningText: ""
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
                this.warningSet(true, nextProps.error.data.nickname);
            }
            if(nextProps.error.data.email) {
                this.warningSet(true, nextProps.error.data.email);
            }
            if(nextProps.error.data.oldPassword) {
                this.warningSet(true, nextProps.error.data.oldPassword);
            }
            if(nextProps.error.data.password) {
                this.warningSet(true, nextProps.error.data.password);
            }
        }

        // DB 전송 최종 완료된후 실행되는 이벤트 코드입니다.
        if(nextProps.account_create.data === true) {
            this.successSet(true, "수정이 완료되었습니다.");
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
    warningSet = (warning, warningText) => {
        // message undefined 값 체크
        warningText = (warningText === undefined) ? "" : warningText;

        this.setState({
            warning,
            warningText
        });
        this.initWarning();
    }

    successSet = (success, warningText) => {
        // message undefined 값 체크
        warningText = (warningText === undefined) ? "" : warningText;

        this.setState({
            success,
            warningText
        });
        this.initWarning();
    }

    /*
     *  경고문 상태 초기화 메소드입니다.
     */
    initWarning = () => {
        setTimeout(() => {
            this.setState({
                success: false,
                warning: false,
                warningText: ""
            });
        }, 2000);
    }

    render() {

        // Props Init
        const { 
            getAccountUserId,         // {id}의 유저의 정보를 가져오는 메소드
            account_get,        // {id}의 유저의 정보 변수
            updateAccount,      // {id}의 유저의 기본정보를 수정하는 메소드
            updateAccountPwdId,   // {id}의 유저의 비밀번호를 수정하는 메소드
            account_create      // {id}의 유저의 수정 상태 정보 변수
        } = this.props;

        // State Init
        const { 
            page, 
            success,
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
                                updateAccountPwdId = {updateAccountPwdId}
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
                                getAccountUserId = {getAccountUserId}
                                account_get = {account_get}
                                warningSet = {this.warningSet}
                                updateAccount = {updateAccount}
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
                    warning ? 
                    <WaringWrap>{warningText}</WaringWrap>
                    :
                    success ?
                    <SuccessWrap>{warningText}</SuccessWrap>
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
    getAccountUserId: PropTypes.func.isRequired,
    updateAccount: PropTypes.func.isRequired,
    updateAccountPwdId: PropTypes.func.isRequired,
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
        getAccountUserId,
        updateAccount,
        updateAccountPwdId
    }
)(MyPageProvider);