import React, { Component } from 'react';
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

import { ImgBox } from "../../../../style/globalStyles";
import { SliderBox, SlideTitle, SlideContent, SliderWrap } from "../style";

class Item extends Component {
    render() {

        const { board_task } = this.props;

        return (
            <SliderWrap>
                <Link to={`/boardEvent/view/${board_task.num}`}>
                    <ImgBox 
                        bgImg={board_task.thumb}
                        bgSize={"90%"}
                    />
                    <SliderBox>
                        <SlideTitle>{board_task.writer}</SlideTitle>
                        <SlideContent>{board_task.content}</SlideContent>
                    </SliderBox>
                </Link>
            </SliderWrap>
        )
    }
}

Item.propTypes = {
    board_task: PropTypes.object.isRequired
}

export default Item;