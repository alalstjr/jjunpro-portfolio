import React, { Component } from 'react';
import BoardHead from "./header";
import CateGory from "./category";
import BoardList from "./list";
import ListBtn from "./bottom/listBtn";

class Board extends Component {
    render() {
        const { page_num } = this.props.match.params;

        return (
            <div>
                <BoardHead/>
                <CateGory
                    pageNum={page_num}
                />
                <BoardList
                    pageNum={page_num}
                />
                <ListBtn
                    option={"list"}
                />
            </div>
        )
    }
}

export default Board;