import React, {Component} from "react";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";
import ReactTransitionGroup from "react-addons-css-transition-group";

import {insertUniversity} from "../../../actions/PugjjigActions";
import FileDrop from "../../widget/fileDrop/FileDrop";
import SVG from "../../../static/svg/SVG";
import SVGLoading from "../../../static/svg/SVGLoading";
import UniversityList from "../list/universityList/UniversityList";

import {
    InputClean,
    Formlabel,
    FormGroup,
    Textarea,
    WaringWrap,
    SmallBtn,
    SelectBox
} from "../../../style/globalStyles";

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
    InsertSubmitBtn,
    OptionBox
} from "../style";

class PugjjigWrite extends Component {

    constructor(props) {
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
            uniAtmosphere: 0,
            uniPrice: 0,
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
            warning: false,
            warningText: ""
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
        if (editPugjjig !== undefined) {
            this.setState({
                uniId: editPugjjig.id,
                uniSubject: editPugjjig.uniSubject,
                uniAtmosphere: editPugjjig.uniAtmosphere,
                uniPrice: editPugjjig.uniPrice,
                uniContent: editPugjjig.uniContent,
                uniName: editPugjjig.uniName,
                uniTag: this.handleTagArray(editPugjjig.uniTag),
                uniStar: editPugjjig.uniStar,
                registerFiles: editPugjjig.files,
                stoId: editPugjjig.storePublic.stoId
            });
        }
    }

    componentWillReceiveProps(nextProps) {

        // Props Init
        const {
            pugjjig_university,
            error
        } = this.props;

        if (nextProps.pugjjig_university !== pugjjig_university) {
            this.setState({
                uniName: nextProps.pugjjig_university,
                loding: false
            });
        }

        if (nextProps.error !== error) {
            this.warningSet(true, nextProps.error.data);
            this.setState({
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
    // Tag Input Setup
    onTagChange = (e) => {
        if (e.target.value !== " ") {
            this.setState({
                [e.target.name]: e.target.value
            });
        }
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
        const {registerFiles, removeFiles} = this.state;
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
            uniAtmosphere,
            uniPrice,
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
            uniAtmosphere,
            uniPrice,
            uniContent,
            uniName,
            uniTag,
            uniStar: uniStar * 1,
            stoId,
            stoName,
            stoAddress,
            stoUrl,
            removeFiles
        };

        // {Client} 유효성 검사 출력 코드입니다.
        if (!pugjjig.uniName) {
            this.warningSet(true, "대학교 선택은 필수입니다.");
            return false;
        }
        if (!pugjjig.uniSubject) {
            this.warningSet(true, "제목 작성은 필수입니다.");
            return false;
        }
        if (!pugjjig.uniContent) {
            this.warningSet(true, "내용 작성은 필수입니다.");
            return false;
        }

        // Array to String
        pugjjig.uniTag = this.handleTagArray(pugjjig.uniTag);

        this.setState({
            loding: true
        });

        this.props.insertUniversity(pugjjig, files, this.props.history);
    }

    // 태그 메소드
    handleTagEvent = (e) => {
        const {uniTagText} = this.state;

        if ((e.keyCode === 32 || e.keyCode === 13) && uniTagText) {
            this.handleTagUpdate();
        }
    }

    handleTagUpdate = () => {
        const {uniTag, uniTagText} = this.state;
        let tagVal = uniTagText.replace(/\s/gi, "");

        if (uniTag.length < 6 && uniTagText.length < 10) {
            if (uniTag.filter(tag => tag === uniTagText).length >= 1) {
                return false;
            }

            this.setState({
                uniTag: uniTag.concat(tagVal),
                uniTagText: ""
            });
        }
    }

    handleTagRemove = (target) => {
        const {uniTag} = this.state;

        this.setState({
            uniTag: uniTag.filter(tag => tag !== target)
        });
    }

    handleTagArray = (target) => {
        if (target === "") {
            return [];
        } else if (typeof target === "string") {
            return target.split(",");
        } else {
            return target.join(",");
        }
    }

    handleUniName = (target) => {
        this.setState({
            uniName: target
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

    /*
     *  경고문 상태 초기화 메소드입니다.
     */
    initWarning = () => {
        setTimeout(() => {
            this.setState({
                warning: false,
                warningText: ""
            });
        }, 2000);
    }

    render() {

        // State Init
        const {
            uniName,
            uniSubject,
            uniAtmosphere,
            uniPrice,
            uniContent,
            uniTag,
            uniTagText,
            uniStar,
            registerFiles,
            loding,
            warning,
            warningText
        } = this.state;

        const {pugjjig_university, file_progress} = this.props;

        const tags = uniTag.map((tag, index) => (
            <TagPart key={index}>
                {tag}
                <CloseBtn onClick={() => this.handleTagRemove(tag)} key={index}>
                    <SVG name={"close"} width="8px" height="8px" color={"#ffffff"}/>
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
                    {
                        (pugjjig_university === "" || pugjjig_university === undefined || pugjjig_university === null) ?
                            <UniversityList
                                uniName={uniName}
                                handleUniName={this.handleUniName}
                            />
                            :
                            null
                    }
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
                        <Formlabel>분위기&amp;가격대</Formlabel>
                        <OptionBox>
                            <SelectBox
                                id="uniAtmosphere"
                                name="uniAtmosphere"
                                value={uniAtmosphere}
                                onChange={this.onChange}
                            >
                                <option value="0">분위기 선택하기</option>
                                <option value="1">조용한 분위기</option>
                                <option value="2">고급스런 분위기</option>
                                <option value="3">활발한 분위기</option>
                            </SelectBox>
                            <SelectBox
                                id="uniPrice"
                                name="uniPrice"
                                value={uniPrice}
                                onChange={this.onChange}
                            >
                                <option value="0">가격대 선택하기</option>
                                <option value="1">많이 비싸다</option>
                                <option value="2">조금 비싸다</option>
                                <option value="3">보통 가격대</option>
                                <option value="4">저렴하다</option>
                                <option value="5">많이 저렴하다</option>
                            </SelectBox>
                        </OptionBox>
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
                                id="uniTagText"
                                name="uniTagText"
                                type="text"
                                value={uniTagText}
                                onChange={this.onTagChange}
                                onKeyDown={this.handleTagEvent}
                                placeholder="태그 작성 후 Space 키를 눌러주세요. (10자 제한)"
                            />
                        </TagWrap>
                        <SmallBtn
                            onClick={this.handleTagUpdate}
                            type="button"
                        >태그추가</SmallBtn>
                    </FormGroup>
                    <FormGroup>
                        <Formlabel>사진</Formlabel>
                        <FileDrop
                            fileState={this.fileState}
                            registerFileState={this.registerFileState}
                            registerFiles={registerFiles}
                            multiple={true}
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
                        <div>
                            <SVGLoading
                                loadText={file_progress}
                            />
                        </div>
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
                        warning ?
                            <WaringWrap>{warningText}</WaringWrap>
                            :
                            null
                    }
                </ReactTransitionGroup>
            </Form>
        )
    }
}

PugjjigWrite.propTypes = {
    insertUniversity: PropTypes.func.isRequired,
    pugjjig_university: PropTypes.string.isRequired,
    file_progress: PropTypes.string.isRequired,
    error: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    error: state.errors,
    pugjjig_university: state.pugjjig.pugjjig_university,
    file_progress: state.pugjjig.file_progress
});

export default withRouter(connect(
    mapStateToProps,
    {insertUniversity}
)(PugjjigWrite));