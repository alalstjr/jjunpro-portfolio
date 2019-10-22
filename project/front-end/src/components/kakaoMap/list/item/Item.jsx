import React from 'react'
import { Link } from "react-router-dom"

const Item = ({ university, pugjjigLike }) => (
    <Link to={`/pugjjig/${university.id}`} target="_blank">
        <div>근처 대학교 : {university.uniName}</div>
        <div>작성자 : {university.account_nickname}</div>
        <div>제목 : {university.uniSubject}</div>
        <div>내용 : {university.uniContent}</div>
        <div>상점평점 : {university.uniStar}</div>
        <div>리뷰의 좋아요 갯수 : {university.uniLike}</div>
        <div>리뷰 태그 : {university.uniTag[0]}</div>
        <div>리뷰 최종 수정 날짜 : {university.modifiedDate}</div>
        <div>좋아요를 누른 상태 : {university.uniLikeState == true ? "푹찍~!" : "X"}</div>
        <button type="button" onClick={() => pugjjigLike(university.id)}>푹찍</button>
    </Link>
);

export default Item;