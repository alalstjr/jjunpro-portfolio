import React from "react";
import { Link } from "react-router-dom";
import BoardPostSlider from "../../widget/boardPostSlider";

import { MainSection, Container } from "../../../style/globalStyles";
import { PostTitle, PostTitleWrap } from "../mainStyle";

const SecondSection = ({ intl }) => (
    <MainSection>
        <Container>
            <PostTitleWrap>
                <PostTitle>진행중인 이벤트</PostTitle>
                <Link to="/boardEvent">이벤트 전체보기 +</Link>
            </PostTitleWrap>
            <BoardPostSlider/>
        </Container>
    </MainSection>
);

export default SecondSection;
