import React, { Component, Fragment } from 'react'

import BoardHead from "../header";
import ListBtn from "../bottom/listBtn"; 

import { Form, FormGroup, Formlabel, Container, Input, Textarea } from "../../../style/globalStyles";

class BoardWrite extends Component {
    render() {
        return (
            <Fragment>
                <BoardHead/>
                <Container>
                    <Form>
                        <FormGroup>
                            <Formlabel>제목</Formlabel>
                            <Input type="text"/>
                        </FormGroup>
                        <FormGroup>
                            <Formlabel>내용</Formlabel>
                            <Textarea/>
                        </FormGroup>
                        <FormGroup>
                            <Formlabel>이미지</Formlabel>
                            <Input type="file"/>
                        </FormGroup>
                    </Form>
                    <ListBtn
                        option={"write"}
                    />
                </Container>
            </Fragment>
        )
    }
}

export default BoardWrite;