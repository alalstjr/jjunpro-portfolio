import React, { Component } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { withRouter } from "react-router-dom"

import { pugjjigInsert } from "../../../actions/KakaoMapActions"
import FileDrop from "../../widget/fileDrop/FileDrop"

import { 
    InputClean, 
    Formlabel, 
    FormGroup, 
    Textarea
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
            stoAddress: null,
            // input value
            uniSubject: "",
            uniContent: "",
            uniName: "",
            uniTag: [],
            uniTagText: "",
            uniStar: 0,
            // FileDrop 필수 state
            files: [],         // INSERT file
            registerFiles: [], // UPDATE file
            removeFiles: [],   // UPDATE REMOVE file 기존 파일 삭제목록
            fileCount: 0
        }
    }

    componentDidMount() {
        // Props Init
        const {
            stoId,
            stoAddress,
            pugjjig_university,
            editPugjjig
        } = this.props;

        this.setState({
            uniName: pugjjig_university,
            stoId,
            stoAddress
        });

        // editPugjjig 값을 props 전달받은 경우 (Edit 상태)
        if(editPugjjig !== undefined) {
            console.log(editPugjjig);
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
        if (nextProps.pugjjig_university !== this.props.pugjjig_university) {
            this.setState({
                uniName: nextProps.pugjjig_university
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
            stoAddress,
            uniId,
            uniSubject, 
            uniContent,
            uniName,
            uniTag,
            uniStar,
            files,
            registerFiles,
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
            stoAddress
        };
        
        this.props.pugjjigInsert(pugjjig, files, removeFiles, this.props.history);
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

    render() {

        const { 
            uniSubject, 
            uniContent,
            uniTag,
            uniTagText,
            uniStar,
            registerFiles
        } = this.state;

        const tags = uniTag.map((tag, index) => (
            <TagPart key={index}>
                {tag}
                <CloseBtn onClick={ () => this.handleTagRemove(tag)} key={index} >X</CloseBtn>
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