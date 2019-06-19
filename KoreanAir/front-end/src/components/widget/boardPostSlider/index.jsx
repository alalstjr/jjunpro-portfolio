import React, { Component, Fragment } from "react";
import { injectIntl } from "react-intl";
import { connect } from "react-redux";
import PropTypes from "prop-types";

import Slider from "react-slick";

import slick from "../../../details/css/slickSlider/slick.css";
import slickTheme from "../../../details/css/slickSlider/slick-theme.css";

import { NoSlider } from "./style";

import { getBoardTasks } from "../../../actions/boardTaskActions";
import Item from "./item";
import { DataNone } from "../../../style/globalStyles";

class BoardPostSlider extends Component {

    componentDidMount(){
        this.props.getBoardTasks();
    }

    render() {
        // props Init
        const { board_tasks } = this.props.board_tasks;

        // Variables Init
        let BoardContent;
        let items = [];

        // Slick Slider option
        let settings = {
            dots: true,
            infinite: true,
            speed: 500,
            auto: false,
            slidesToShow: 5,
            slidesToScroll: 1
        };

        // Board Get List
        const BoardGetList = board_tasks => {
            if(board_tasks.length > 0) {
                const tasks = board_tasks.map(board_task => (
                    <Item
                        key={board_task.num}
                        board_task={board_task}
                    />
                ));

                for(let i = 0; i < tasks.length; i++) {
                    items.push(tasks[i]);
                }

                const SliderItem = (
                    <Slider {...settings}>
                        {items}
                    </Slider>
                );
                const noSliderItem = (
                    <NoSlider>
                        {items}
                    </NoSlider>
                );
                
                return (
                    <Fragment>
                        {board_tasks.length > 5 ? SliderItem : noSliderItem}
                    </Fragment>
                );
            } else {
                return(
                    <DataNone>
                        게시글이 존재하지 않습니다.
                    </DataNone>
                );
            }
        };

        // Board Get List View
        BoardContent = BoardGetList(board_tasks);

        return(
            <Fragment>
                {BoardContent}
            </Fragment>
        );
  }
}

BoardPostSlider.propTypes = {
    getBoardTasks: PropTypes.func.isRequired,
    board_tasks: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    board_tasks: state.board_task
});

export default connect(
    mapStateToProps,
    { getBoardTasks }  
)(injectIntl(BoardPostSlider));