import React, {Component} from "react"

import {
    Form,
    InputClean
} from "../../../../style/globalStyles"
import {
    ProfileWrap,
    GroupBox,
    ProfileLabel,
    ProfileInput,
    SaveBtn
} from "../../style"

class PwdChange extends Component {

    constructor() {
        super();

        this.state = {
            // Input Value
            id: null,
            oldPassword: "",
            password: "",
            passwordRe: ""
        }
    }

    componentDidMount() {
        // Props Init
        const {account_get} = this.props;

        this.setState({
            id: account_get.id
        });
    }

    componentWillReceiveProps(nextProps) {
        // 로그인한 유저의 정보를 DTO에 담습니다.
        if (nextProps.account_get.data !== undefined) {
            let account = nextProps.account_get.data;

            this.setState({
                id: account.id
            });
        }

        // 비밀번호 변경에 성공하면 초기화
        if (nextProps.account_create.data === true) {
            this.setState({
                oldPassword: "",
                password: "",
                passwordRe: ""
            });
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

        // Props Init
        const {warningSet} = this.props;

        // State Init
        const {
            id,
            oldPassword,
            password,
            passwordRe,
        } = this.state;

        // Value Init
        const account = {
            id,
            oldPassword,
            password,
            passwordRe
        };

        if (account.id === undefined) {
            account.id = this.props.account_get.data.id;
        }

        // {Client} 유효성 검사 출력 코드입니다.
        if (!account.id) {
            warningSet(true, "잘못된 접근입니다.");
            console.log("UUID 값이 존재하지 않습니다.");
            return false;
        }
        if (!account.oldPassword) {
            warningSet(true, "이전 비밀번호는 필수로 작성해야 합니다.");
            return false;
        }
        if (!account.password) {
            warningSet(true, "새로운 비밀번호는 필수로 작성해야 합니다.");
            return false;
        }
        if (!account.passwordRe) {
            warningSet(true, "새로운 비밀번호를 다시한번 작성해 주세요.");
            return false;
        }

        this.props.updateAccountPwdId(account, this.props.history);
    }

    render() {

        // State Init
        const {
            oldPassword,
            password,
            passwordRe
        } = this.state;

        return (
            <Form onSubmit={this.onSubmit}>
                <ProfileWrap>
                    <GroupBox>
                        <ProfileLabel>이전 비밀번호</ProfileLabel>
                        <ProfileInput>
                            <InputClean
                                id="oldPassword"
                                name="oldPassword"
                                type="password"
                                value={oldPassword}
                                onChange={this.onChange}
                            />
                        </ProfileInput>
                    </GroupBox>
                    <GroupBox>
                        <ProfileLabel>새로운 비밀번호</ProfileLabel>
                        <ProfileInput>
                            <InputClean
                                id="password"
                                name="password"
                                type="password"
                                value={password}
                                onChange={this.onChange}
                            />
                        </ProfileInput>
                    </GroupBox>
                    <GroupBox>
                        <ProfileLabel>새로운 비밀번호 확인</ProfileLabel>
                        <ProfileInput>
                            <InputClean
                                id="passwordRe"
                                name="passwordRe"
                                type="password"
                                value={passwordRe}
                                onChange={this.onChange}
                            />
                        </ProfileInput>
                    </GroupBox>
                    <GroupBox>
                        <ProfileLabel></ProfileLabel>
                        <ProfileInput>
                            <SaveBtn>비밀번호 변경</SaveBtn>
                        </ProfileInput>
                    </GroupBox>
                </ProfileWrap>
            </Form>
        );
    }
}

export default PwdChange;