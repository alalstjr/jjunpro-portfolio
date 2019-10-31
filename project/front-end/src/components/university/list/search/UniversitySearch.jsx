import React from "react"

import { 
    SearchWrap,
    SearchTitle,
    SearchInputText,
    SearchInputBtn,
    SearchInputWrap,
    RefreshBtn,
    SwitchWrap,
    SwitchBtn
} from "../../style"

const UniversitySearch = ({keyword, onChange, onState, onSearchState, onSearchStore, searchState, storeState}) => (
    <SearchWrap>
        <SearchTitle>
            학교 주변 푹찍 둘러보기
        </SearchTitle>
        <SwitchWrap
            storeState={storeState}
        >
            <SwitchBtn 
                onClick={() => onSearchState(1)}
            >
                대학교
            </SwitchBtn>
            <SwitchBtn 
                onClick={() => onSearchState(2)}
            >
                음식점
            </SwitchBtn>
            <SwitchBtn 
                onClick={() => onSearchState(3)}
            >
                검색설정
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