import React from "react"

import SVG from "../../../../static/svg/SVG"

import {
    SearchWrap,
    SearchTitle,
    SearchInputText,
    SearchInputBtn,
    SearchInputWrap,
    RefreshBtn,
    SwitchWrap,
    SwitchBtn,
    NoticeText
} from "../../style"

const UniversitySearch = ({keyword, onChange, onState, onSearchState, onSearchStore, searchState, storeState, initSearch}) => (
    <SearchWrap>
        <SearchTitle>
            학교 근처 맛집 탐색을 한 번에!
        </SearchTitle>
        <SwitchWrap
            storeState={storeState}
        >
            <SwitchBtn
                onClick={() => onSearchState(1)}
                initSearch={initSearch}
            >
                <SVG
                    name={"school"}
                    width="30px"
                    height="30px"
                    color={storeState === 1 ? "#ffffff" : "#333333"}
                />
                <NoticeText>대학교검색</NoticeText>
            </SwitchBtn>
            <SwitchBtn
                onClick={() => onSearchState(2)}
                initSearch={initSearch}
            >
                <SVG
                    name={"store"}
                    width="30px"
                    height="30px"
                    color={storeState === 2 ? "#ffffff" : "#333333"}
                />
                <NoticeText>음식점검색</NoticeText>
            </SwitchBtn>
            <SwitchBtn
                onClick={() => onSearchState(3)}
                initSearch={initSearch}
            >
                <SVG
                    name={"setting"}
                    width="30px"
                    height="30px"
                    color={storeState === 3 ? "#ffffff" : "#333333"}
                />
                <NoticeText>사용자설정</NoticeText>
            </SwitchBtn>
        </SwitchWrap>
        {
            searchState !== false ?
                <SearchInputWrap>
                    <SearchInputText
                        id="keyword"
                        name="keyword"
                        value={keyword}
                        onChange={onChange}
                        autoComplete="off"
                        placeholder={(storeState === 1 || storeState === 3) ? "대학교이름 검색하기" : "음식점이름 검색하기"}
                    />
                    <SearchInputBtn
                        onClick={(storeState === 2) ? onSearchStore : null}
                    >
                        대학교 검색하기
                    </SearchInputBtn>
                </SearchInputWrap>
                :
                <RefreshBtn onClick={onState}>
                    다시검색하기
                </RefreshBtn>
        }
    </SearchWrap>
);

export default UniversitySearch;