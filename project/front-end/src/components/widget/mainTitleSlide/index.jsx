import React, { Component } from "react";

import Slider from "react-slick";

import "../../../details/css/slickSlider/slick.css";
import "../../../details/css/slickSlider/slick-theme.css";

import { SliderImg } from "./style";

class MainTitleSlide extends Component {
    render() {

        const { slideShow, images, thumbnail } = this.props;

        let slidesToShow = (slideShow < 0) ? 0 : slideShow;

        const PrevButton = ({ onClick }) => {
            return <button onClick={onClick} className="slick-prev">Prev</button>;
        }
      
        const NextButton = ({ onClick }) => {
            return <button onClick={onClick} className="slick-next">Next</button>;
        }

        const settings = {
            arrow: true,
            dots: false,
            infinite: true,
            auto: false,
            slidesToShow,
            slidesToScroll: 1,
            prevArrow: <PrevButton />,
            nextArrow: <NextButton />,
        };

        return(
            <Slider {...settings}>
                {
                    images.map((image, index) => (
                        <SliderImg 
                            key = {index}
                            bgSize = {"66%"}
                            bgImg = {
                                thumbnail ?
                                require(`../../../../../data/file/thumbnail/pugjjig/${image.fileThumbnail}`)
                                :
                                require(`../../../../../data/file/pugjjig/${image.fileOriginal}`)
                            }
                            bgState = { thumbnail ? "cover" : "contain" }
                        />
                    ))
                }
            </Slider>
        );
  }
}

export default MainTitleSlide;
