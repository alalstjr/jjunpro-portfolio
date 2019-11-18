import React, { Component, Fragment } from "react"
import NormalHeader from "../../layout/header/normal/NormalHeader"
import Profile from "./profile/Profile"

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
            page: "profile"
        }
    }

    handlePageChange = (page) => {
        this.setState({
            page
        });
    }

    // Input Setup
    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    render() {

        const { page } = this.state;

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
                                onChange = {this.onChange}
                            />
                        }
                    </MyPageRight>
                </MyPageWrap>
            </Fragment>
        )
    }
}

export default MyPageProvider;