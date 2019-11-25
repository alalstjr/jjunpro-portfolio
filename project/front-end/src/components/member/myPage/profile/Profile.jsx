import React, { Component, Fragment } from "react"
import FileDrop from "../../../widget/fileDrop/FileDrop"
import SVG from "../../../../static/svg/SVG"

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
    SaveBtn,
    ProfileIamge,
    ProfileId
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
            urlList: [],
            // FileDrop 필수 state
            files: [],
            registerFiles: [],
            fileCount: 0
        }
    }

    componentDidMount() {

        // Props Init
        const {
            accountGet
        } = this.props;

        // 유저의 정보를 가져옵니다.
        accountGet();
    }

    componentWillReceiveProps(nextProps) {
        if(
            nextProps.account_get !== this.props.account_get &&
            (nextProps.account_get.data !== undefined) 
        ){

            let account = nextProps.account_get.data;
            // url 값이 존재하지 않으면 빈 배열 기본값 
            account.urlList = (account.urlList === null || account.urlList.length <= 0) ? [] : account.urlList;
            
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
            email,
            files
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
        
        this.props.accountUpdate(account, files, this.props.history);
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

    // File Setup
    fileState = (target) => {
        this.setState({
            files: target.map(fileItem => fileItem),
            fileCount: target.length
        });
    }
    
    // registerFiles Setup
    registerFileState = (file) => {
        const { registerFiles, removeFiles } = this.state;
        registerFiles.splice(registerFiles.indexOf(file), 1);

        removeFiles.push(file.id)

        this.setState({
            registerFiles,
            removeFiles
        });
    }

    render() {
        
        // Props Init
        const { 
            initWarning, 
            account_get 
        } = this.props;

        // State Init
        const { 
            nickname, 
            email, 
            urlListInput, 
            urlList, 
            registerFiles 
        } = this.state;

        return (
            <Form onSubmit={this.onSubmit}>
                <ProfileWrap>
                    <GroupBox>
                        <ProfileLabel>
                            <div>
                                {
                                    account_get.data !== undefined ?
                                    <Fragment>
                                        <ProfileId>{account_get.data.userId}</ProfileId>
                                        {
                                            account_get.data.photo === null ?
                                            <SVG name={"user"} width="38px" height="38px" color={"#E71D36"} />
                                            : 
                                            <ProfileIamge
                                                image = {require(`../../../../../../data/file/thumbnail/pugjjig/${account_get.data.photo.fileThumbnail}`)}
                                            />
                                        }
                                    </Fragment>
                                    : 
                                    null
                                }
                            </div>
                        </ProfileLabel>
                        <ProfileInput>
                            <FileDrop
                                fileState = {this.fileState}
                                registerFileState = {this.registerFileState}
                                registerFiles = {registerFiles}
                                multiple = {false}
                            />
                        </ProfileInput>
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