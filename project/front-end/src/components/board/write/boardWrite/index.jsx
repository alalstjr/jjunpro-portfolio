import React, { Fragment } from 'react';

import BoardHead from "../../header";
import ListBtn from "../../bottom/listBtn"; 
import FileDrop from "../../../widget/fileDrop/FileDrop";

import { 
    Form, 
    FormGroup, 
    Formlabel, 
    Container, 
    Input, 
    Textarea, 
    SelectBox 
} from "../../../../style/globalStyles";

const BoardWrite = ({
    uniSubject,
    uniContent,
    uniName,
    uniTag,
    uniLocal,
    uniStar,
    fileCount,
    files,
    registerFiles,
    errors,
    onSubmit,
    onChange,
    fileState,
    registerFileState
}) => {
    return (
        <Fragment>
            <BoardHead/>
            <Container>
                <Form
                    onSubmit={onSubmit}
                    method="post" 
                    enctype="multipart/form-data"
                >
                    <FormGroup>
                        <Formlabel htmlFor="uniName">대학교 이름</Formlabel>
                        <Input 
                            id="uniName"
                            type="text"
                            name="uniName"
                            value={uniName}
                            onChange={onChange}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Formlabel htmlFor="uniTag">태그</Formlabel>
                        <Input 
                            id="uniTag"
                            type="text"
                            name="uniTag"
                            value={uniTag}
                            onChange={onChange}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Formlabel htmlFor="uniStar">평가</Formlabel>
                        <Input 
                            id="uniStar"
                            type="text"
                            name="uniStar"
                            value={uniStar}
                            onChange={onChange}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Formlabel htmlFor="uniSubject">제목</Formlabel>
                        <Input 
                            id="uniSubject"
                            type="text"
                            name="uniSubject"
                            value={uniSubject}
                            onChange={onChange}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Formlabel htmlFor="uniContent">내용</Formlabel>
                        <Textarea
                            id="uniContent"
                            name="uniContent"
                            value={uniContent}
                            onChange={onChange}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Formlabel htmlFor="file">이미지</Formlabel>
                        <FileDrop 
                            fileState={fileState}
                            registerFiles={registerFiles}
                            registerFileState={registerFileState}
                        />
                    </FormGroup>
                    <ListBtn
                        option={"write"}
                    />
                </Form>
            </Container>
        </Fragment>
    )
}

export default BoardWrite;