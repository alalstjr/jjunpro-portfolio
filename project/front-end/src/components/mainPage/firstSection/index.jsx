import React from "react"
import { MainList, Input } from "../../../style/globalStyles"
import { Link } from "react-router-dom"
// import { searchPlaces } from "../../../service/KakaoMapService"
import UserModal from "../../user"

const FirstSection = ({onChange, keyword, searchPlaces}) => (
    <MainList>
      대학교 리스트
      <div><Link to={`/pugjjigs`}>내가 쓴 글보기</Link></div>
      <div><Link to={`/pugjjigs/kkw10`}>kkw10 유저의 게시글보기</Link></div>
      <div><Link to={`/pugjjigLikes`}>푹찍한 게시글보기</Link></div>
      <div><Link to={`/pugjjigLikes/kkw10`}>kkw10 유저의 푹찍한 게시글보기</Link></div>
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