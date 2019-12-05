import React, { Component, Fragment } from "react"
import NormalHeader from "../../layout/header/normal/NormalHeader"
import List from "../../pugjjig/list/List"

import {
    SearchWrap
} from "../style"

class PugjjigSearchProvider extends Component {
    constructor(props) {
        super(props);

        this.state = {
            keyword: null,
            classification: null,
            offsetCount: 0
        }
    }

    componentDidMount() {
        // Props Init
        const { 
            match
        } = this.props;

        this.setState({
            keyword: match.params.id,
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
                classification: this.handleClassification(nextProps.match.path)
            });
        }
    }

    // match path 를 조회하여 검색 대상의 분류를 구분합니다.
    handleClassification = (target) => {
        if(target.indexOf("uniSearch") != -1) {
            return "uniName";
        }
        if(target.indexOf("stoSearch") != -1) {
            return "stoId";
        }
        if(target.indexOf("tagSearch") != -1) {
            return "uniTag";
        }
        if(target.indexOf("userSearch") != -1) {
            return "userId";
        }
    }

    render() {

        // State Init
        const { 
            keyword,
            classification,
            offsetCount
        } = this.state;

        return (
            <Fragment>
                <NormalHeader/>
                <SearchWrap>
                    {
                        (keyword !== null && classification !== null) ?
                        <List
                            keyword        = {keyword}
                            classification = {classification}
                            offsetCount    = {offsetCount}
                        />
                        :
                        null
                    }
                </SearchWrap>
            </Fragment>
        )
    }
}

export default PugjjigSearchProvider;