import React, { Component } from "react";
<<<<<<< HEAD
=======
import { injectIntl } from "react-intl";

import { Link } from "react-router-dom";
>>>>>>> d119cadf0eabc66e758c090063508688a18bf004

import Slider from "react-slick";

import "../../../details/css/slickSlider/slick.css";
import "../../../details/css/slickSlider/slick-theme.css";

import { SliderImg, SliderWrap, SliderContent, SlideTitle, SlideContent, SlideLink } from "./styleWidget";

<<<<<<< HEAD
=======
import exImg from "../../../details/images/main-slide-ex.jpg";

>>>>>>> d119cadf0eabc66e758c090063508688a18bf004
class MainTitleSlide extends Component {
    render() {

        const settings = {
<<<<<<< HEAD
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
=======
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
>>>>>>> d119cadf0eabc66e758c090063508688a18bf004
            </Slider>
        );
  }
}

<<<<<<< HEAD
export default MainTitleSlide;
=======
export default injectIntl(MainTitleSlide);
>>>>>>> d119cadf0eabc66e758c090063508688a18bf004
