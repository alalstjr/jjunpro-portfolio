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
                    uniName:"덕성여자대학교 후문",
                    uniPujjig: 0,
                    x: 127.017288184806,
                    y: 37.6525503981283
                },
                {
                    id: 2,
                    uniName:"서일대학교",
                    uniPujjig: 0,
                    x: 127.097976337279,
                    y: 37.5864866143728
                },
                {
                    id: 3,
                    uniName:"건국대학교",
                    uniPujjig: 0,
                    x: 127.076302318843,
                    y: 37.5429556751421
                },
                {
                    id: 4,
                    uniName:"홍익대학교 서울캠퍼스",
                    uniPujjig: 0,
                    x: 126.925554591431,
                    y: 37.550874837441
                },
                {
                    id: 5,
                    uniName:"홍익대학교 세종캠퍼스",
                    uniPujjig: 0,
                    x: 127.287933647532,
                    y: 36.6213894816246
                },
                {
                    id: 6,
                    uniName:"명지대학교",
                    uniPujjig: 0,
                    x: 126.923891021078,
                    y: 37.5798245779138
                },
                {
                    id: 7,
                    uniName:"명지전문대학",
                    uniPujjig: 0,
                    x: 126.925693019531,
                    y: 37.5846910900534
                },
                {
                    id: 8,
                    uniName:"서울대학교",
                    uniPujjig: 0,
                    x: 126.948400031913,
                    y: 37.4663007082505
                },
                {
                    id: 9,
                    uniName:"이화여자대학교 정문",
                    uniPujjig: 0,
                    x: 126.94566937595,
                    y: 37.5594236592153
                },
                {
                    id: 10,
                    uniName:"이화여자대학교 후문",
                    uniPujjig: 0,
                    x: 126.944692996803,
                    y: 37.5635317504439
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

    /*
     *  검색된 음식점 모두를 초기화하는 메소드입니다.
     */
    removeAllChildNods = () => {
        const cell = document.getElementById('universityList');
        while(cell.hasChildNodes()) {
            cell.removeChild(cell.firstChild);
        }
    }

    /*
     *  state {Integer num}
     *  state 값은 필수 값입니다.
     *  전달받는 값은 {1: 대학교, 2: 음식점, 3: 사용자설정} 입니다.
     *  대학교 검색 상태 메소드입니다.
     */
    onSearchState = (state) => {
        this.setState({
            keyword: "",
            searchState: true,
            storeState: state
        });

        this.removeAllChildNods();
    }

    /*
     *  사용자가 다시 검색할 수 있도록 설정하는 메소드입니다.
     */
    onState = () => {
        this.setState({
            searchState: true
        });

        this.removeAllChildNods();
    }

    /*
     *  x {String x}, y {String y}
     *  x, y 는 필수 값입니다.
     *  기본 대학교 검색 메소드입니다. 
     */
    onSearch = (x, y) => {
        this.props.categorySearch(x, y);

        this.setState({
            searchState: false
        });

        this.props.hendleInitSearch();
    }

    /*
     *  직접 음식점 검색 메소드입니다.
     */
    onSearchStore = () => {
        this.setState({
            keyword: "",
            searchState: false
        });

        this.props.hendleInitSearch();
        this.props.searchPlaces();
    }
    
    /*
     *  x {String x}, y {String y}, university {String 대학교이름}
     *  x, y, university 는 필수 값입니다.
     *  대학교 검색 상세설정 메소드입니다.
     */
    onSearchSetting = (x, y, university) => {
        
        const { radius, category } = this.state;
        
        // 사용자 검색설정 키워드 변수
        // 음식 종류 선택이 없으면 모두 검색
        let keywordSetting = (category === "") ? "음식점" : `${university} ${this.state.category}`;

        this.setState({
            keyword: "",
            searchState: false
        });
        
        // 카카오 API 콜백
        this.props.searchPlacesSetting(x, y, radius, keywordSetting);

        this.props.hendleInitSearch();
    }

    render() {
        // state Init
        const { university, keyword, searchState, storeState, radius, category } = this.state;

        // props Init
        const { initSearch } = this.props;

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
                <ListWrap
                    initSearch={initSearch}
                >
                    {
                        searchState !== false && storeState === 3 ? 
                        <SearchSet>
                            <div>
                                <span>
                                    도보기준 :
                                </span> 
                                <label htmlFor="radius-1">5~10분</label>
                                <input type="radio" name="radius" id="radius-1"
                                    value={300}
                                    checked={radius == "300"}
                                    onChange={this.onChange}
                                />
                                <label htmlFor="radius-2">10~20분</label>
                                <input type="radio" name="radius" id="radius-2"
                                    value={600}
                                    checked={radius === "600"}
                                    onChange={this.onChange}
                                />
                                <label htmlFor="radius-3">20분 이상</label>
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