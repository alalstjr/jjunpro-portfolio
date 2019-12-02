import React, { Component } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { withRouter } from "react-router-dom"
import ReactTransitionGroup from "react-addons-css-transition-group"

import { pugjjigInsert } from "../../../actions/KakaoMapActions"
import FileDrop from "../../widget/fileDrop/FileDrop"
import SVG from "../../../static/svg/SVG"
import SVGLoading from "../../../static/svg/SVGLoading"

import { 
    InputClean, 
    Formlabel, 
    FormGroup, 
    Textarea,
    WaringWrap
} from "../../../style/globalStyles"

import {
    TitleWrap,
    Title,
    Form,
    Content,
    RatingWrap,
    Rating,
    RatingMessage,
    RatingPointInput,
    RatingPointLabel,
    TagWrap,
    TagPart,
    CloseBtn,
    InsertSubmitBtn
} from "../style"

class PugjjigWrite extends Component {

    constructor(props){
        super(props);
    
        this.state = {
            // update id
            uniId: null,
            stoId: null,
            stoName: null,
            stoAddress: null,
            stoUrl: null,
            // input value
            uniSubject: "",
            uniContent: "",
            uniName: "",
            uniTag: [],
            uniTagText: "",
            uniStar: 0,
            // FileDrop 필수 state
            files: [],         // INSERT file
            registerFiles: [], // UPDATE file (사용 X)
            removeFiles: [],   // UPDATE REMOVE file 기존 파일 삭제목록
            fileCount: 0,
            // Form State
            loding: false,
            // Input 경고문
            warning: {
                subejct: false,
                content: false
            },
            warningText: {
                subejct: "",
                content: ""
            }
        }
    }

    componentDidMount() {
        // Props Init
        const {
            stoId,
            stoName,
            stoAddress,
            stoUrl,
            pugjjig_university,
            editPugjjig
        } = this.props;

        this.setState({
            uniName: pugjjig_university,
            stoId,
            stoName,
            stoAddress,
            stoUrl
        });

        // editPugjjig 값을 props 전달받은 경우 (Edit 상태)
        if(editPugjjig !== undefined) {
            this.setState({
                uniId: editPugjjig.id,
                uniSubject: editPugjjig.uniSubject,
                uniContent: editPugjjig.uniContent,
                uniName: editPugjjig.uniName,
                uniTag: editPugjjig.uniTag,
                uniStar: editPugjjig.uniStar,
                registerFiles: editPugjjig.files,
                stoId: editPugjjig.storePublic.stoId
            }); 
        }
    }

    componentWillReceiveProps(nextProps) {

        // Props Init
        const {
            pugjjig_university
        } = this.props;

        if (nextProps.pugjjig_university !== pugjjig_university) {
            this.setState({
                uniName: nextProps.pugjjig_university,
                loding: false
            });
        }
    }

    // Input Setup
    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }
    // Input Value값의 경우 String 형으로 받기 때문에
    // 점수와 같은 값은 *1 을 해주어서 값을 Integer 로 변경하여 저장합니다.
    onChangeInt = (e) => {
        this.setState({
            [e.target.name]: e.target.value * 1
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

    // Form Submit
    onSubmit = (e) => {
        e.preventDefault();

        const {
            stoId,
            stoName,
            stoAddress,
            stoUrl,
            uniId,
            uniSubject, 
            uniContent,
            uniName,
            uniTag,
            uniStar,
            files,
            removeFiles
        } = this.state;

        const pugjjig = {
            uniId,
            uniSubject, 
            uniContent,
            uniName,
            uniTag,
            uniStar: uniStar*1,
            stoId,
            stoName,
            stoAddress,
            stoUrl,
            removeFiles
        };

        // {Client} 유효성 검사 출력 코드입니다.
        if(!pugjjig.uniSubject) {
            this.warningSet("subject", true, "제목 작성은 필수입니다.");
            return false;
        }
        if(!pugjjig.uniContent) {
            this.warningSet("content", true, "내용 작성은 필수입니다.");
            return false;
        }

        this.setState({
            loding: true
        });
        
        this.props.pugjjigInsert(pugjjig, files, this.props.history);
    }

    // 태그 메소드
    handleTagEvent = (e) => {
        const { uniTag, uniTagText } = this.state;
        
        if(uniTag.length < 6) {
            if( (e.keyCode === 32 || e.keyCode === 13) && uniTagText ) {
                let tagVal = uniTagText;

                if( uniTag.filter( tag => tag === uniTagText ).length >= 1 ) {
                    return false;
                }

                this.setState({
                    uniTag: uniTag.concat(tagVal),
                    uniTagText: ""
                });
            }
        }
    }

    handleTagRemove = (arg) => {
        const { uniTag } = this.state;
        
        this.setState({
            uniTag: uniTag.filter( tag => tag !== arg)
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
            case "subject" : 
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        subject: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        subject: message
                    }
                }));
                this.initWarning();
                break;

            case "content" : 
                this.setState(prevState => ({
                    warning: {
                        ...prevState.warning,
                        content: state
                    },
                    warningText: {
                        ...prevState.warningText,
                        content: message
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
                    subject: false,
                    content: false
                }
            }));
        }, 2000);
    }

    render() {

        // State Init
        const { 
            uniSubject, 
            uniContent,
            uniTag,
            uniTagText,
            uniStar,
            registerFiles,
            loding,
            warning, 
            warningText
        } = this.state;

        const tags = uniTag.map((tag, index) => (
            <TagPart key={index}>
                {tag}
                <CloseBtn onClick={ () => this.handleTagRemove(tag)} key={index} >
                    <SVG name={"close"} width="8px" height="8px" color={"#ffffff"} />
                </CloseBtn>
            </TagPart>
        ));

        return (
            <Form
                onSubmit={this.onSubmit}
            >
                <Content>
                    <TitleWrap>
                        <Title>
                            리뷰작성
                        </Title>
                    </TitleWrap>
                    <RatingWrap>
                        <Rating>
                            <RatingPointInput id="star5" name="uniStar" value="5"
                                checked={uniStar === 5} 
                                onChange={this.onChangeInt}
                            />
                            <RatingPointLabel htmlFor="star5"/>
                            <RatingPointInput id="star4" name="uniStar" value="4"
                                checked={uniStar === 4} 
                                onChange={this.onChangeInt}
                            />
                            <RatingPointLabel htmlFor="star4"/>
                            <RatingPointInput id="star3" name="uniStar" value="3"
                                checked={uniStar === 3} 
                                onChange={this.onChangeInt}
                            />
                            <RatingPointLabel htmlFor="star3"/>
                            <RatingPointInput id="star2" name="uniStar" value="2"
                                checked={uniStar === 2} 
                                onChange={this.onChangeInt}
                            />
                            <RatingPointLabel htmlFor="star2"/>
                            <RatingPointInput id="star1" name="uniStar" value="1"
                                checked={uniStar === 1} 
                                onChange={this.onChangeInt}
                            />
                            <RatingPointLabel htmlFor="star1"/>
                        </Rating>
                        <RatingMessage>5점 만점에 몇 점인가요?</RatingMessage>
                    </RatingWrap>
                    <FormGroup>
                        <Formlabel>제목</Formlabel>
                        <InputClean                                    
                            id="uniSubject"
                            name="uniSubject"
                            type="text"
                            value={uniSubject}
                            onChange={this.onChange}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Formlabel>내용</Formlabel>
                        <Textarea                                    
                            id="uniContent"
                            name="uniContent"
                            value={uniContent}
                            onChange={this.onChange}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Formlabel>태그</Formlabel>
                        <TagWrap>
                            {tags}
                            <InputClean
                                id = "uniTagText"
                                name = "uniTagText"
                                type = "text"
                                value = {uniTagText}
                                onChange = {this.onChange}
                                onKeyDown = {this.handleTagEvent}
                            />
                        </TagWrap>
                    </FormGroup>
                    <FormGroup>
                        <Formlabel>사진</Formlabel>
                        <FileDrop
                            fileState = {this.fileState}
                            registerFileState = {this.registerFileState}
                            registerFiles = {registerFiles}
                            multiple = {true}
                        />
                    </FormGroup>
                </Content>
                <InsertSubmitBtn
                    type="submit"
                >
                        작성 완료
                </InsertSubmitBtn>

                {/* 작성 완료 클릭시 Loding 대기화면 */}
                {
                    loding ? 
                    <SVGLoading/>
                    :
                    null
                }

                {/* Write 안내문 */}
                <ReactTransitionGroup
                    transitionName={'Waring-anim'}
                    transitionEnterTimeout={200}
                    transitionLeaveTimeout={200}
                >
                {
                    warning.subject ? 
                    <WaringWrap>{warningText.subject}</WaringWrap>
                    :
                    warning.content ?
                    <WaringWrap>{warningText.content}</WaringWrap>
                    :
                    null
                }
                </ReactTransitionGroup>
            </Form>
        )
    }
}

PugjjigWrite.propTypes = {
    pugjjigInsert: PropTypes.func.isRequired,
    pugjjig_university: PropTypes.string.isRequired,
    error: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_university: state.pugjjig.pugjjig_university
});
  
export default withRouter(connect(
    mapStateToProps, 
    { pugjjigInsert }
)(PugjjigWrite));