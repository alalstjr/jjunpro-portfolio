import React, { Component, Fragment } from "react"
import UniversityItem from "./item/UniversityItem"
import UniversitySearch from "./search/UniversitySearch"
import LoginBox from "../../myPage/login/LoginBox"

import { 
    ListWrap,
    SearchNotice,
    UserBox,
    SearchSet
 } from "../style"

class UniversityList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            // 검색 키워드
            keyword: "",
            // 검색 반경, 음식종류
            radius: 300,
            category: "",
            // 검색 상태
            searchState: true,
            // 직접 음식점 목록 표시상태
            // 1:대학교 검색, 2:음식점 검색, 3:사용자설정 검색
            storeState: 1,
            // 대학교 목록
            university: [
                {
                    id: 0,
                    uniName:"덕성여자대학교",
                    uniPujjig: 0,
                    x: 127.01639455013195,
                    y: 37.64950880707469
                },
                {
                    id: 1,
                    uniName:"서일대학교",
                    uniPujjig: 0,
                    x: 127.097976337279,
                    y: 37.5864866143728
                },
                {
                    id: 2,
                    uniName:"건국대학교",
                    uniPujjig: 0,
                    x: 127.076302318843,
                    y: 37.5429556751421
                }
            ]
        }
    }

    // Input Setup
    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    onSearch = (x, y) => {
        this.props.categorySearch(x, y);

        this.setState({
            searchState: false
        });
    }

    onState = () => {
        this.setState({
            searchState: true
        });

        this.removeAllChildNods();
    }

    removeAllChildNods = () => {
        // 좌측 검색된 음식점요소 초기화
        const cell = document.getElementById('universityList');
        while(cell.hasChildNodes()) {
            cell.removeChild(cell.firstChild);
        }
    }

    // 대학교 검색 상태
    onSearchState = (state) => {
        this.setState({
            keyword: "",
            searchState: true,
            storeState: state
        });

        this.removeAllChildNods();
    }

    // 직접 음식점 검색 상태
    onSearchStore = () => {
        this.setState({
            keyword: "",
            searchState: false
        });

        this.props.searchPlaces();
    }
    
    // 푹 사용자 설정 검색
    onSearchSetting = (x, y, university) => {
        const { radius } = this.state;
        
        // 사용자 검색설정 키워드 변수
        let keywordSetting = `${university} ${this.state.category}`;

        this.setState({
            keyword: "",
            searchState: false
        });
        // 카카오 API 콜백
        this.props.searchPlacesSetting(x, y, radius, keywordSetting);
    }

    render() {
        // state Init
        const { university, keyword, searchState, storeState, radius, category } = this.state;

        // Variables Init
        let universityContent;
        let universityList = [];

        // SearchFilte Init
        const filteredList = university.filter(
            info => info.uniName.indexOf(keyword) !== -1
        );

        // universityList
        const universityGet = (university) => {
            const data = university.map(university => (
                <UniversityItem
                    key = {university.id}
                    uniName = {university.uniName}
                    uniPujjig = {university.uniPujjig}
                    categorySearch = {
                        storeState === 1 ?
                        () => this.onSearch(university.x, university.y)
                        :
                        storeState === 3 ?
                        () => this.onSearchSetting(university.x, university.y, university.uniName)
                        :
                        null
                    }
                />
            ));

            for(let i = 0; i < data.length; i++) {
                universityList.push(data[i]);
            }

            return (
                <Fragment>
                    {universityList}
                </Fragment>
            );
        }

        // universityList Get List View
        universityContent = universityGet(filteredList);

        return (
            <Fragment>
                <UserBox>
                    <LoginBox/>
                    <UniversitySearch
                        keyword={keyword}
                        onChange={this.onChange}
                        onState = {this.onState}
                        onSearchState={this.onSearchState}
                        onSearchStore={this.onSearchStore}
                        searchState={searchState}
                        storeState={storeState}
                    />
                </UserBox>
                <ListWrap>
                    {
                        searchState !== false && storeState === 3 ? 
                        <SearchSet>
                            <div>
                                <span>
                                    도보기준 :
                                </span> 
                                <label htmlFor="radius-1">5분</label>
                                <input type="radio" name="radius" id="radius-1"
                                    value={300}
                                    checked={radius == "300"}
                                    onChange={this.onChange}
                                />
                                <label htmlFor="radius-2">10분</label>
                                <input type="radio" name="radius" id="radius-2"
                                    value={600}
                                    checked={radius === "600"}
                                    onChange={this.onChange}
                                />
                                <label htmlFor="radius-3">20분</label>
                                <input type="radio" name="radius" id="radius-3"
                                    value={1200}
                                    checked={radius === "1200"}
                                    onChange={this.onChange}
                                />
                            </div> 
                            <div>
                                <span>
                                    음식종류 :
                                </span> 
                                <input type="radio" name="category" id="category-1"
                                    value={""}
                                    checked={category === ""}
                                    onChange={this.onChange}
                                />
                                <label htmlFor="category-1">전부</label>
                                <input type="radio" name="category" id="category-2"
                                    value={"일식"}
                                    checked={category === "일식"}
                                    onChange={this.onChange}
                                />
                                <label htmlFor="category-2">일식</label>
                                <input type="radio" name="category" id="category-3"
                                    value={"중식"}
                                    checked={category === "중식"}
                                    onChange={this.onChange}
                                />
                                <label htmlFor="category-3">중식</label>
                                <input type="radio" name="category" id="category-4"
                                    value={"한식"}
                                    checked={category === "한식"}
                                    onChange={this.onChange}
                                />
                                <label htmlFor="category-4">한식</label>
                                <input type="radio" name="category" id="category-5"
                                    value={"카페"}
                                    checked={category === "카페"}
                                    onChange={this.onChange}
                                />
                                <label htmlFor="category-5">카페</label>
                            </div> 
                        </SearchSet>
                        :
                        null
                    }
                    {
                        searchState !== false && (storeState === 1 || storeState === 3) ?
                        universityContent
                        :
                        null
                    }
                    {
                        searchState !== false && storeState === 2 ? 
                        <SearchNotice>찾으시는 음식점을 검색해주세요.</SearchNotice>
                        :
                        null
                    }
                    <div id="universityList"></div>
                </ListWrap>
            </Fragment>
        )
    }
}

export default UniversityList;