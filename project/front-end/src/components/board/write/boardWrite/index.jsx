import React, { Fragment } from 'react';

import BoardHead from "../../header";
import ListBtn from "../../bottom/listBtn"; 
import FileDrop from "../../../widget/fileDrop";

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
    num,
    writer,
    password,
    category,
    subject,
    content,
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
                    <Input 
                        id="writer"
                        type="hidden"
                        name="writer"
                        value={subject}
                    />
                    <Input 
                        id="password"
                        type="hidden"
                        name="password"
                        value={password}
                    />
                    <FormGroup>
                        <Formlabel htmlFor="category">분류</Formlabel>
                        <SelectBox
                            id="category"
                            name="category"
                            value={category}
                            onChange={onChange}
                        >
                            <option value="none">분류를 선택해주세요.</option>
                            <option value="1">진행중인 이벤트</option>
                            <option value="2">마감된 이벤트</option>
                        </SelectBox>
                    </FormGroup>
                    <FormGroup>
                        <Formlabel htmlFor="subject">제목</Formlabel>
                        <Input 
                            id="subject"
                            type="text"
                            name="subject"
                            value={subject}
                            onChange={onChange}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Formlabel htmlFor="content">내용</Formlabel>
                        <Textarea
                            id="content"
                            name="content"
                            value={content}
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