import React, { Component, Fragment } from "react"

import {
    InputClean
} from "../../../../style/globalStyles"
import {
    ProfileWrap,
    GroupBox,
    ProfileLabel,
    ProfileInput
} from "../../style"

class Profile extends Component {

    constructor() {
        super();

        this.state = {
            nickname: "",
            email: "",
            sns: ""
        }
    }

    render() {

        const { onChange } = this.props;
        const { nickname, email, sns } = this.state;

        return (
            <ProfileWrap>
                <GroupBox>
                    <ProfileLabel>사진</ProfileLabel>
                    <ProfileInput>사진</ProfileInput>
                </GroupBox>
                <GroupBox>
                    <ProfileLabel>닉네임</ProfileLabel>
                    <ProfileInput>
                        <InputClean                                    
                            id="nickname"
                            name="nickname"
                            type="text"
                            value={nickname}
                            onChange={onChange}
                        />
                    </ProfileInput>
                </GroupBox>
                <GroupBox>
                    <ProfileLabel>E-MAIL</ProfileLabel>
                    <ProfileInput>
                        <InputClean                                    
                            id="email"
                            name="email"
                            type="text"
                            value={email}
                            onChange={onChange}
                        />
                    </ProfileInput>
                </GroupBox>
                <GroupBox>
                    <ProfileLabel>SNS,블로그</ProfileLabel>
                    <ProfileInput>
                        <InputClean                                    
                            id="sns"
                            name="sns"
                            type="text"
                            value={sns}
                            onChange={onChange}
                        />
                    </ProfileInput>
                </GroupBox>
            </ProfileWrap>
        )
    }
}

export default Profile;