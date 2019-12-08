import React, { Component } from 'react'

import { CategoryWrap } from "../style"

class Category extends Component {
    render() {

        const { handleSearchCate, handleReSearch } = this.props;

        return (
            <CategoryWrap>
                <select name="ifCateA" id="ifCateA" onChange = {handleSearchCate}>
                    <option value="all">최신 등록된 게시물</option>
                    <option value="like">좋아요가 많은 게시물</option>
                    <option value="comment">댓글이 많은 게시물</option>
                </select>
                <select name="ifCateB" id="ifCateB" onChange = {handleSearchCate}>
                    <option value="all">모두 검색</option>
                    <option value="photo">사진이 있는 게시물</option>
                    <option value="posts">글만 있는 게시물</option>
                </select>
                <button onClick = {handleReSearch}>검색</button>
            </CategoryWrap>
        )
    }
}

export default Category;