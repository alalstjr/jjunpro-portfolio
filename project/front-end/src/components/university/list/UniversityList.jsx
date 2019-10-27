import React, { Component, Fragment } from "react"
import UniversityItem from "./item/UniversityItem"
import UniversitySearch from "./search/UniversitySearch"

import { 
    ListWrap
 } from "../style"

class UniversityList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            // 검색 키워드
            keyword: "",
            // 목록 제어
            universityState: true,
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
            universityState: false
        });
    }

    render() {
        // state Init
        const { university, keyword, universityState } = this.state;

        // props Init
        const { searchPlaces } = this.props;

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
                    categorySearch = {() => this.onSearch(university.x, university.y)}
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
                <UniversitySearch
                    keyword={keyword}
                    onChange={this.onChange}
                    searchPlaces={searchPlaces}
                />
                <ListWrap>
                    {
                        universityState !== false ?
                        universityContent
                        :
                        null
                    }
                </ListWrap>
            </Fragment>
        )
    }
}

export default UniversityList;