import React, { Component } from 'react';
import BoardHead from "./header";
import CateGory from "./category";
import BoardList from "./list";
import ListBtn from "./bottom/listBtn";

class Board extends Component {
    render() {
        return (
            <div>
                <BoardHead/>
                <CateGory/>
                <BoardList/>
                <ListBtn
                    option={"list"}
                />
            </div>
        )
    }
}

export default Board;