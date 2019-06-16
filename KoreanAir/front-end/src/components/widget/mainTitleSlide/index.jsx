import React, { Component } from "react";
import { injectIntl } from "react-intl";

import Slider from "react-slick";

import slick from "../../../details/css/slickSlider/slick.css";
import slickTheme from "../../../details/css/slickSlider/slick-theme.css";

class MainTitleSlide extends Component {
    render() {

        const settings = {
            dots: true,
            infinite: true,
            speed: 500,
            slidesToShow: 1,
            slidesToScroll: 1
        };

        return(
            <Slider {...settings}>
                <div>slide 위젯1</div>
                <div>slide 위젯2</div>
                <div>slide 위젯3</div>
                <div>slide 위젯4</div>
                <div>slide 위젯5</div>
            </Slider>
        );
  }
}

export default injectIntl(MainTitleSlide);