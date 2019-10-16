import React from "react"
import { MainList, Input } from "../../../style/globalStyles"
import { Link } from "react-router-dom"
import { searchPlaces } from "../../../service/KakaoMapService"
import UserModal from "../../user"

const FirstSection = ({onChange, keyword}) => (
    <MainList>
      대학교 리스트
      <Link to={`/pugjjig/write`}>등록</Link>
      대학교 근처 맛집 둘러보기
      <Input
        id="keyword"
        type="text"
        name="keyword"
        value={keyword}
        onChange={onChange}
      />
      <button type="button" onClick={searchPlaces}>검색하기</button>
      <div id="placesList"></div>
      <div id="menu_wrap"></div>
      <UserModal
          text = "로그인"
          req = "login"
      />
    </MainList>
);

export default FirstSection;