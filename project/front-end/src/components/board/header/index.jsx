import React, { Component } from 'react';
import { BoardHeader, BoardTitle, BoardIntro } from "../style";
import { Container } from "../../../style/globalStyles";

class BoardHead extends Component {
    render() {
        return (
            <BoardHeader>
                <Container>
                    <BoardTitle>제목</BoardTitle>
                    <BoardIntro>게시판의 설명글</BoardIntro>
                </Container>
            </BoardHeader>
        )
    }
}

export default BoardHead;