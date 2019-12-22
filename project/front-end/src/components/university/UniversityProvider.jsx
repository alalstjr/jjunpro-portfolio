import React, { Component, Fragment } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"

import UniversityList from "./list/universityList/UniversityList"
import UniversitySearch from "./list/search/UniversitySearch"
import AccountBox from "../member/account/container/AccountBox"
import { getUniversity } from "../../actions/PugjjigActions"

import { 
    ListWrap,
    SearchNotice,
    UserBox,
    SearchSet,
    PagingBox,
    Pagination,
    SearchSetTimeWrap,
    SearchSetFoodWrap,
    Item,
    ItemUniName,
    ItemUniCount
 } from "./style"

class UniversityProvider extends Component {

    constructor(props) {
        super(props);

        this.state = {
            // 검색 키워드
            keyword: "",
            // 검색 반경, 음식종류
            radius: 200,
            category: "",
            // 검색 상태
            searchState: true,
            // 직접 음식점 목록 표시상태
            // 1:대학교 검색, 2:음식점 검색, 3:사용자설정 검색
            storeState: 1
        }
    }

    // Input Setup
    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    /*
     *  검색된 음식점,페이징 모두를 초기화하는 메소드입니다.
     */
    removeAllChildNods = () => {
        const cell = document.getElementById('universityList');
        while(cell.hasChildNodes()) {
            cell.removeChild(cell.firstChild);
        }

        const paging = document.getElementsByClassName(Pagination.componentStyle.lastClassName).item(0);        
        while(paging.hasChildNodes()) {
            paging.removeChild(paging.firstChild);
        }
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
    onSearch = (x, y, uniName) => {
        this.props.categorySearch(x, y);

        this.setState({
            searchState: false
        });

        this.props.getUniversity(uniName);
        this.props.hendleInitSearch();
    }

    /*
     *  state {Integer num}
     *  state 값은 필수 값입니다.
     *  전달받는 값은 {1: 대학교, 2: 음식점, 3: 사용자설정} 입니다.
     *  대학교 검색 상태 메소드입니다.
     */
    onSearchState = (state) => {
        this.setState({
            searchState: true,
            storeState: state
        });

        this.removeAllChildNods();
    }

    /*
     *  직접 음식점 검색 메소드입니다.
     */
    onSearchStore = () => {
        this.setState({
            keyword: "",
            searchState: false
        });

        this.props.getUniversity("");
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
            // keyword: "",
            searchState: false
        });
        
        // 카카오 API 콜백
        this.props.searchPlacesSetting(x, y, radius, keywordSetting);

        this.props.hendleInitSearch();
    }

    render() {
        // state Init
        const { keyword, searchState, storeState, radius, category } = this.state;

        // props Init
        const { initSearch, mobile } = this.props;

        return (
            <Fragment>
                <UserBox
                    initSearch={initSearch}
                >
                    <AccountBox
                        initSearch={initSearch}
                    />
                    <UniversitySearch
                        keyword={keyword}
                        onChange={this.onChange}
                        onState = {this.onState}
                        onSearchState={this.onSearchState}
                        onSearchStore={this.onSearchStore}
                        searchState={searchState}
                        storeState={storeState}
                        initSearch={initSearch}
                    />
                </UserBox>
                <ListWrap
                    initSearch = {initSearch}
                    mobile     = {mobile}
                >
                    {
                        searchState !== false && storeState === 3 ? 
                        <SearchSet>
                            <SearchSetTimeWrap
                                initSearch = {initSearch}
                            >
                                <div>도보기준</div> 
                                <div>
                                    <input type="radio" name="radius" id="radius-1"
                                        value={200}
                                        checked={radius*1 === 200}
                                        onChange={this.onChange}
                                    />
                                    <label htmlFor="radius-1">5~10분</label>
                                </div>
                                <div>
                                    <input type="radio" name="radius" id="radius-2"
                                        value={300}
                                        checked={radius*1 === 300}
                                        onChange={this.onChange}
                                    />
                                    <label htmlFor="radius-2">10~20분</label>
                                </div>
                                <div>
                                    <input type="radio" name="radius" id="radius-3"
                                        value={1000}
                                        checked={radius*1 === 1000}
                                        onChange={this.onChange}
                                    />
                                    <label htmlFor="radius-3">20분 이상</label>
                                </div>
                            </SearchSetTimeWrap> 
                            <SearchSetFoodWrap
                                initSearch = {initSearch}
                            >
                                <div>음식종류</div> 
                                <div>
                                    <input type="radio" name="category" id="category-1"
                                        value={""}
                                        checked={category === ""}
                                        onChange={this.onChange}
                                    />
                                    <label htmlFor="category-1">전부</label>
                                </div>
                                <div>
                                    <input type="radio" name="category" id="category-2"
                                        value={"일식"}
                                        checked={category === "일식"}
                                        onChange={this.onChange}
                                    />
                                    <label htmlFor="category-2">일식</label>
                                </div>
                                <div>
                                    <input type="radio" name="category" id="category-3"
                                        value={"중식"}
                                        checked={category === "중식"}
                                        onChange={this.onChange}
                                    />
                                    <label htmlFor="category-3">중식</label>
                                </div>
                                <div>
                                    <input type="radio" name="category" id="category-4"
                                        value={"한식"}
                                        checked={category === "한식"}
                                        onChange={this.onChange}
                                    />
                                    <label htmlFor="category-4">한식</label>
                                </div>
                                <div>
                                    <input type="radio" name="category" id="category-5"
                                        value={"카페"}
                                        checked={category === "카페"}
                                        onChange={this.onChange}
                                    />
                                    <label htmlFor="category-5">카페</label>
                                </div>
                            </SearchSetFoodWrap> 
                        </SearchSet>
                        :
                        null
                    }
                    {
                        searchState !== false && (storeState === 1 || storeState === 3) ?
                        <UniversityList
                            keyword         = {keyword}
                            storeState      = {storeState}
                            onSearch        = {this.onSearch}
                            onSearchSetting = {this.onSearchSetting}
                        />
                        :
                        null
                    }
                    {
                        searchState !== false && storeState === 2 ? 
                        <SearchNotice>
                            찾으시는 음식점을 검색해주세요.
                            {/* API style class name lazy loading 문제 때문에 Item 미리 호출 */}
                            <Item>
                                <ItemUniName/>
                                <ItemUniCount/>
                            </Item>
                        </SearchNotice>
                        :
                        null
                    }
                    <div id="universityList"></div>
                    <PagingBox>
                        <Pagination></Pagination>
                    </PagingBox>
                </ListWrap>
            </Fragment>
        )
    }
}

UniversityProvider.propTypes = {
    getUniversity: PropTypes.func.isRequired,
    error: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    error: state.errors
});
  
export default connect(
    mapStateToProps, 
    { getUniversity }
)(UniversityProvider);