import React, { Component } from "react";

import Slider from "react-slick";

import "../../../details/css/slickSlider/slick.css";
import "../../../details/css/slickSlider/slick-theme.css";

import { SliderImg, SliderWrap, SliderContent, SlideTitle, SlideContent, SlideLink } from "./styleWidget";

class MainTitleSlide extends Component {
    render() {

        const settings = {
            dots: false,
            infinite: true,
            speed: 500,
            auto: false,
            slidesToShow: 3,
            slidesToScroll: 1
        };

        const { images } = this.props;

        return(
            <Slider {...settings}>
                {
                    images.map((image, index) => (
                        <SliderImg 
                            key={index}
                            bgImg={require(`../../../../../data/file/thumbnail/pugjjig/${image.fileThumbnail}`)}
                            bgSize={"66%"}
                        />
                    ))
                }
            </Slider>
        );
  }
}

export default MainTitleSlide;