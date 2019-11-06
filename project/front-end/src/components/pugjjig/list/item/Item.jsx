import React from "react"
import { Link } from "react-router-dom"

const Item = ({pugjjig, pugjjigLike}) => (
    <div>
        <Link to={`/pugjjig/${pugjjig.id}`} target="_blank">
            <div>근처 대학교 : {pugjjig.uniName}</div>
            <div>작성자 : {pugjjig.account_nickname}</div>
            <div>제목 : {pugjjig.uniSubject}</div>
            <div>내용 : {pugjjig.uniContent}</div>
            <div>상점평점 : {pugjjig.uniStar}</div>
            <div>리뷰의 푹찍 갯수 : {pugjjig.uniLike}</div>
            <div>
                리뷰 태그 : 
                {
                    pugjjig.uniTag.map((tag) => (
                        <div key={tag}>{tag}</div>
                    ))
                }
            </div>
            <div>리뷰 최종 수정 날짜 : {pugjjig.modifiedDate}</div>
            <div>좋아요를 누른 상태 : {pugjjig.uniLikeState == true ? "푹찍~!" : "X"}</div>
            <div>
                리뷰 사진 : 
                {
                    pugjjig.files.map((file) => (
                        <div key={file.id}>
                            <img 
                                src={require(`../../../../../../data/file/pugjjig/${file.id+file.fileType}`)}
                                alt="음식점 사진"
                            />
                        </div>
                    ))
                }
            </div>
        </Link>
        <button type="button" onClick={() => pugjjigLike(pugjjig.id)}>푹찍</button>
    </div>
);

export default Item;