import React, { Component } from 'react';

import { CategoryWrap } from "../style";
import { Input } from "../../../style/globalStyles";

class Category extends Component {
    render() {

        const { onChange, onSearch, handleReSearch, inputKeyword } = this.props;

        return (
            <CategoryWrap>
                <select name="ifCateA" id="ifCateA" onChange = {onChange}>
                    <option value="all">최신 등록된 순</option>
                    <option value="like">좋아요가 많은 순</option>
                    <option value="comment">댓글이 많은 순</option>
                </select>
                <select name="ifCateB" id="ifCateB" onChange = {onChange}>
                    <option value="all">전체</option>
                    <option value="photo">사진이 있는 리뷰</option>
                    <option value="posts">글만 있는 리뷰</option>
                </select>
                <Input
                    onChange   = {onChange}
                    onKeyPress = {onSearch}
                    value      = {inputKeyword}
                    name       = "inputKeyword"
                />
                <button onClick = {handleReSearch}>검색</button>
            </CategoryWrap>
        )
    }
}

export default Category;