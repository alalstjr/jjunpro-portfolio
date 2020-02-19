import React, {Component, Fragment} from "react";
import {Link} from "react-router-dom";
import NormalHeader from "../layout/header/normal/NormalHeader";
import List from "../pugjjig/list/List";

import {Container} from "../../style/globalStyles";
import {BestWrap, Title, Content, Group, ContentTitle, ContentP} from "./style";

class SquareProvider extends Component {
    render() {
        return (
            <Fragment>
                <NormalHeader/>
                <BestWrap>
                    <Container>
                        <Group>
                            <Title>리뷰 찾아보기</Title>
                            <Content>
                                <Link to="/pugjjig/uniSearch/all">
                                    <ContentTitle>대학교 맛집 리뷰 모아보기</ContentTitle>
                                    <ContentP>내가 원하는 대학교 근처 맛집 리뷰를 찾을 때!</ContentP>
                                </Link>
                                <Link to="/pugjjig/tagSearch/all">
                                    <ContentTitle>태그를 검색해서 직접 찾아보기</ContentTitle>
                                    <ContentP>특정 태그를 검색해서 리뷰를 찾고 싶을 때!</ContentP>
                                </Link>
                                <Link to="/pugjjig/stoSearch/all">
                                    <ContentTitle>궁금한 음식점의 리뷰를 찾아보기</ContentTitle>
                                    <ContentP>음식점의 평가가 궁금할 때!</ContentP>
                                </Link>
                            </Content>
                        </Group>
                        <Group>
                            <Title>최근 작성된 리뷰</Title>
                            <Content>
                                <List
                                    keyword={"null"}
                                    classification={"createdDate"}
                                    offsetCount={null}
                                    ifCateA={null}
                                    ifCateB={null}
                                    reSearch={null}
                                    handleReSearch={null}
                                />
                            </Content>
                        </Group>
                    </Container>
                </BestWrap>
            </Fragment>
        )
    }
}

export default SquareProvider