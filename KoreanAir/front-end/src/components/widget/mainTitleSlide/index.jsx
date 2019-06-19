import React, { Component } from "react";
import { injectIntl } from "react-intl";

import { Link } from "react-router-dom";

import Slider from "react-slick";

import slick from "../../../details/css/slickSlider/slick.css";
import slickTheme from "../../../details/css/slickSlider/slick-theme.css";

import { SliderImg, SliderWrap, SliderContent, SlideTitle, SlideContent, SlideLink } from "./styleWidget";

import exImg from "../../../details/images/main-slide-ex.jpg";

class MainTitleSlide extends Component {
    render() {

        const settings = {
            dots: true,
            infinite: true,
            speed: 500,
            auto: false,
            slidesToShow: 1,
            slidesToScroll: 1
        };

        return(
            <Slider {...settings}>
                <SliderWrap>
                    <SliderImg 
                        bgImg={exImg}
                        bgSize={"38%"}
                    />
                    <SliderContent>
                        <SlideTitle
                            titleFontSize={"45px"}
                        >
                            시원한 여름을 선사할 특별한 소식
                        </SlideTitle>
                        <SlideContent
                            contentFontSize={"20px"}
                        >
                            2019년 6월 1일 부터 10월 26일까지 인천-아사히카와 직항으로 모십니다.
                        </SlideContent>
                        <SlideLink>
                            <Link to="/">
                                자세히 보기
                            </Link>
                        </SlideLink>
                    </SliderContent>
                </SliderWrap>
                <div>slide 위젯2</div>
                <div>slide 위젯3</div>
                <div>slide 위젯4</div>
                <div>slide 위젯5</div>
            </Slider>
        );
  }
}

export default injectIntl(MainTitleSlide);