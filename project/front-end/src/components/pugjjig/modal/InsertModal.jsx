import React, { Fragment, Component } from "react"
import ReactTransitionGroup from "react-addons-css-transition-group"
import { 
    Form, 
    InputClean, 
    Formlabel, 
    FormGroup, 
    SubmitBtn, 
    ModalCloseBtn,  
    ModalOverlay, 
    Modal,
    Title,
    Content
} from "../../../style/globalStyles";

class InsertModal extends Component {
    render() {

        const { 
            modalState, 
            closeModal,
            onSubmit,
            onChange,
            uniName,
            uniTag,
            uniStar,
            uniSubject,
            uniContent,
        } = this.props;

        return (
            <Fragment>
                {
                    modalState ?
                    <ReactTransitionGroup
                        transitionName={'Modal-anim'}
                        transitionEnterTimeout={200}
                        transitionLeaveTimeout={200}
                    >
                    <ModalOverlay/>
                    <Modal>
                        <ModalCloseBtn onClick={() => closeModal("insertModalState")}/>
                        <Form
                            onSubmit={onSubmit}
                        >
                            <Title>
                                리뷰작성
                            </Title>
                            <Content>
                                <FormGroup>
                                    <Formlabel>대학교 이름</Formlabel>
                                    <InputClean                                    
                                        id="uniName"
                                        name="uniName"
                                        type="text"
                                        value={uniName}
                                        onChange={onChange}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Formlabel>태그</Formlabel>
                                    <InputClean                                    
                                        id="uniTag"
                                        name="uniTag"
                                        type="text"
                                        value={uniTag}
                                        onChange={onChange}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Formlabel>평가</Formlabel>
                                    <InputClean                                    
                                        id="uniStar"
                                        name="uniStar"
                                        type="text"
                                        value={uniStar}
                                        onChange={onChange}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Formlabel>제목</Formlabel>
                                    <InputClean                                    
                                        id="uniSubject"
                                        name="uniSubject"
                                        type="text"
                                        value={uniSubject}
                                        onChange={onChange}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Formlabel>내용</Formlabel>
                                    <InputClean                                    
                                        id="uniContent"
                                        name="uniContent"
                                        type="text"
                                        value={uniContent}
                                        onChange={onChange}
                                    />
                                </FormGroup>
                            </Content>
                            <SubmitBtn
                                type="submit"
                            >
                                    작성 완료
                            </SubmitBtn>
                        </Form>
                    </Modal>
                    </ReactTransitionGroup>
                    :
                    <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200} transitionLeaveTimeout={200} />
                }
            </Fragment>
        )
    }
}

export default InsertModal;