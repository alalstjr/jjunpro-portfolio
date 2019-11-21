import React, { Component, Fragment } from "react"

import {
    Form,
    InputClean
} from "../../../../style/globalStyles"
import {
    ProfileWrap,
    GroupBox,
    ProfileLabel,
    ProfileInput,
    UrlList,
    Url,
    CloseBtn,
    UrlSaveBtn,
    SaveBtn
} from "../../style"

class Profile extends Component {

    constructor() {
        super();

        this.state = {
            // Input Value
            id: null,
            nickname: "",
            email: "",
            urlListInput: "",
            urlList: []
        }
    }

    componentDidMount() {
        // 유저의 정보를 가져옵니다.
        this.props.accountGet();
    }

    componentWillReceiveProps(nextProps) {
        if(
            nextProps.account_get !== this.props.account_get &&
            (nextProps.account_get.data !== undefined) 
        ){

            let account = nextProps.account_get.data;
            // url 값이 존재하지 않으면 빈 배열 기본값 
            account.urlList = (account.urlList === undefined || account.urlList.length <= 0) ? [] : account.urlList;
            
            this.setState({
                id: account.id,
                nickname: account.nickname,
                email: account.email,
                urlList: account.urlList
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
        const { warningSet } = this.props;

        // State Init
        const { 
            id,
            nickname,
            urlList,
            email
        } = this.state;

        // Value Init
        const account = {
            id,
            nickname,
            urlList,
            email
        };

        // {Client} 유효성 검사 출력 코드입니다.
        if(!account.id) {
            warningSet("nickname", true, "잘못된 접근입니다.");
            console.log("UUID 값이 존재하지 않습니다.");
            return false;
        }
        if(!account.nickname) {
            warningSet("nickname", true, "닉네임은 필수로 작성해야 합니다.");
            return false;
        }
        if(!account.email) {
            warningSet("email", true, "이메일은 필수로 작성해야 합니다.");
            return false;
        }
        
        this.props.accountUpdate(account, this.props.history);
    }

    /*
     *  String target 
     *  클릭한 대상을 state에서 제거하는 메소드
     */
    handleurlLinkRemove = (target) => {
        const { urlList } = this.state;
        this.setState({
            urlList: urlList.filter(url => url !== target)
        });
    }

    /*
     *  String target 
     *  클릭한 대상을 state에서 추가하는 메소드
     */
    handleurlLinkAdd = (target) => {
        const { urlList } = this.state;

        if(target.replace(/^\s+|\s+$/g,"") === "") {
            alert("URL 주소를 입력해 주세요.");
            return false;
        }

        if(urlList.filter(url => url === target).length > 0) {
            alert("이미 등록된 URL 주소입니다.");
            return false;
        }

        if(urlList.length > 2) {
            alert("URL 주소는 3개만 입력 가능합니다.");
            return false;
        }

        this.setState({
            urlList: urlList.concat(target),
            urlListInput: ""
        });
    }

    render() {
        const { initWarning } = this.props;
        const { nickname, email, urlListInput, urlList } = this.state;

        return (
            <Form onSubmit={this.onSubmit}>
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
                                onChange={this.onChange}
                                onKeyDown={initWarning}
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
                                onChange={this.onChange}
                                onKeyDown={initWarning}
                            />
                        </ProfileInput>
                    </GroupBox>
                    <GroupBox>
                        <ProfileLabel>SNS,블로그</ProfileLabel>
                        <ProfileInput>
                            <InputClean                                    
                                id="urlListInput"
                                name="urlListInput"
                                type="text"
                                value={urlListInput}
                                onChange={this.onChange}
                            />
                            <UrlSaveBtn onClick={() => this.handleurlLinkAdd(urlListInput)} >등록</UrlSaveBtn>
                            {
                                urlList.length > 0 ?
                                <UrlList>
                                    {
                                        urlList.map((url, index) => (
                                            <Url key={index}>
                                                {url}
                                                <CloseBtn onClick={() => this.handleurlLinkRemove(url)}/>
                                            </Url>
                                        ))
                                    }
                                </UrlList>
                                :
                                null
                            }
                        </ProfileInput>
                    </GroupBox>
                    <GroupBox>
                        <ProfileLabel></ProfileLabel>
                        <ProfileInput>
                            <SaveBtn>프로필 저장</SaveBtn>
                        </ProfileInput>
                    </GroupBox>
                </ProfileWrap>
            </Form>
        )
    }
}

export default Profile;