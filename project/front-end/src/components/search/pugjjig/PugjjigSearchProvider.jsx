import React, { Component, Fragment } from "react"
import NormalHeader from "../../layout/header/normal/NormalHeader"
import List from "../../pugjjig/list/List"
import Category from "../category/Category"
import ScrollUp from "../scrollUp/ScrollUp"

import {
    SearchWrap,
    SearchTitle
} from "../style"

class PugjjigSearchProvider extends Component {
    constructor(props) {
        super(props);

        this.state = {
            reSearch: false,
            keyword: null,
            classification: null,
            offsetCount: 0,
            ifCateA: "all",  // like or comment 
            ifCateB: "all",   // photo or posts
            inputKeyword: "all"
        }
    }

    componentDidMount() {
        // Props Init
        const { 
            match
        } = this.props;

        this.setState({
            keyword: match.params.id,
            inputKeyword: match.params.id,
            classification: this.handleClassification(match.path)
        });
    }

    componentWillReceiveProps(nextProps) {
        // Props Init
        const { 
            match
        } = this.props;

        if(nextProps.match !== match) {
            this.setState({
                keyword: nextProps.match.params.id,
                inputKeyword: nextProps.match.params.id,
                classification: this.handleClassification(nextProps.match.path)
            });
        }
    }

    // match path 를 조회하여 검색 대상의 분류를 구분합니다.
    handleClassification = (target) => {
        if(target.indexOf("uniSearch") !== -1) {
            return "uniName";
        }
        if(target.indexOf("stoSearch") !== -1) {
            return "stoId";
        }
        if(target.indexOf("tagSearch") !== -1) {
            return "uniTag";
        }
        if(target.indexOf("userSearch") !== -1) {
            return "nickname";
        }
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value 
        });
    }

    onSearch = (e) => {
        if(e.key === "Enter") {
            this.handleReSearch();
        }
    }

    handleReSearch = () => {
        const { reSearch } = this.state;
        this.setState({
            reSearch: !reSearch
        });
    }

    render() {

        // State Init
        const { 
            keyword,
            classification,
            offsetCount,
            ifCateA,
            ifCateB,
            inputKeyword,
            reSearch
        } = this.state;

        const handleTitle = () => {
            switch(classification) {
                case "uniName" :
                    return "대학교 검색";

                case "stoId" :
                    return "음식점 검색";

                case "uniTag" :
                    return "태그 검색";

                case "nickname" :
                    return "사용자이름 검색";

                default :
                    break;
            }
        }

        return (
            <Fragment>
                <NormalHeader/>
                <SearchWrap>
                    <SearchTitle>
                        {handleTitle()}
                    </SearchTitle>
                    <Category
                        onChange           = {this.onChange}
                        onSearch           = {this.onSearch}
                        handleReSearch     = {this.handleReSearch}
                        keyword            = {keyword}
                    />
                    {
                        (keyword !== null && classification !== null) ?
                        <List
                            keyword        = {keyword}
                            inputKeyword   = {inputKeyword}
                            classification = {classification}
                            offsetCount    = {offsetCount}
                            ifCateA        = {ifCateA}
                            ifCateB        = {ifCateB}
                            reSearch       = {reSearch}
                            handleReSearch = {this.handleReSearch}
                        />
                        :
                        null
                    }
                </SearchWrap>
                <ScrollUp/>
            </Fragment>
        )
    }
}

export default PugjjigSearchProvider;