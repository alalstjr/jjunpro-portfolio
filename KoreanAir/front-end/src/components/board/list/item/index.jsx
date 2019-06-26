import React, { Component } from 'react';
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

import { ImgBox } from "../../../../style/globalStyles";
import { ItemWrap, ItemBox, ItemTitle, ItemContent } from "../../style";

class Item extends Component {
    render() {

        const { board_task } = this.props;
        console.log(board_task);
        return (
            <ItemWrap>
                <Link to="/">
                    <ImgBox 
                        bgImg={board_task.thumb}
                        bgSize={"90%"}
                    />
                    <ItemBox>
                        <ItemTitle>{board_task.writer}</ItemTitle>
                        <ItemContent>{board_task.content}</ItemContent>
                    </ItemBox>
                </Link>
            </ItemWrap>
        )
    }
}

Item.propTypes = {
    board_task: PropTypes.object.isRequired
}

export default Item;