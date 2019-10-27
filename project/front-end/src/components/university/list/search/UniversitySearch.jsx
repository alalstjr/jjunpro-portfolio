import React from "react"

import { 
    SearchWrap,
    SearchTitle,
    SearchInputText,
    SearchInputBtn,
    SearchInputWrap
} from "../../style"

const UniversitySearch = ({keyword, onChange, searchPlaces}) => (
    <SearchWrap>
        <SearchTitle>
            학교 주변 푹찍 둘러보기
        </SearchTitle>
        <SearchInputWrap>
            <SearchInputText
                id="keyword"
                name="keyword"
                value={keyword}
                onChange={onChange}
            />
            <SearchInputBtn
                onClick={searchPlaces}
            >
                푹찍 검색하기
            </SearchInputBtn>
        </SearchInputWrap>
    </SearchWrap> 
);

export default UniversitySearch;