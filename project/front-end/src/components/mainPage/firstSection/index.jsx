import React from "react"
import { MainList, Input } from "../../../style/globalStyles"
import { Link } from "react-router-dom"
import UserModal from "../../user"
import UniversityList from "../../university/list/UniversityList"

const FirstSection = ({searchPlaces, categorySearch}) => (
    <MainList>
      대학교 리스트
      <div><Link to={`/pugjjigs`}>내가 쓴 글보기</Link></div>
      <div><Link to={`/pugjjigs/kkw10`}>kkw10 유저의 게시글보기</Link></div>
      <div><Link to={`/pugjjigLikes`}>푹찍한 게시글보기</Link></div>
      <div><Link to={`/pugjjigLikes/kkw10`}>kkw10 유저의 푹찍한 게시글보기</Link></div>
      <UserModal
        text = "로그인"
        req = "login"
      />
      <UniversityList
        searchPlaces={searchPlaces}
        categorySearch={categorySearch}
      />

      <div id="universityList"></div>
    </MainList>
);

export default FirstSection;