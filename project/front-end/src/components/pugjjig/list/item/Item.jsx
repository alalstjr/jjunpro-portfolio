import React from "react"
import { Link } from "react-router-dom"
<<<<<<< HEAD
import ImageSlide from "../../../widget/mainTitleSlide"
=======
>>>>>>> d119cadf0eabc66e758c090063508688a18bf004
import { 
    PugjjigItem,
    ItemHead,
    ItemRight,
    ItemUserPhoto,
    ItemUsername,
    ItemUserInfo,
    ItemStar,
    ItemSubject,
    ItemContent,
    ItemImgBox,
    ItemTagWrap,
    ItemStateWrap,
<<<<<<< HEAD
    ItemState,
    ImgList,
    ItemDetailWrap,
    ItemDetail,
    ItemDate,
    ItemBottom,
    ItemTag,
    ItemLikeBtn,
    ItemLikeText
} from "../../style"

import SVG from "../../../../static/svg/SVG"

=======
    ItemState
} from "../../style"

>>>>>>> d119cadf0eabc66e758c090063508688a18bf004
const Item = ({pugjjig, pugjjigLike}) => (
    <PugjjigItem>
        <ItemHead>
            <ItemUserPhoto></ItemUserPhoto>
            <ItemRight>
                <ItemUsername>{pugjjig.account_nickname}</ItemUsername>
                <ItemUserInfo>유저정보</ItemUserInfo>
            </ItemRight>
<<<<<<< HEAD
            {
                pugjjig.uniStar === 1 ?
                <ItemStar>
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                </ItemStar>
                :
                pugjjig.uniStar === 2 ?
                <ItemStar>
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                </ItemStar>
                :
                pugjjig.uniStar === 3 ?
                <ItemStar>
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                </ItemStar>
                :
                pugjjig.uniStar === 4 ?
                <ItemStar>
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                </ItemStar>
                :
                <ItemStar>
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                <SVG name={"star"} width="16px" height="16px" color={"#ffd700"} />
                </ItemStar>
            }
=======
            <ItemStarWrap
                uniStar = {pugjjig.uniStar}
            />
>>>>>>> d119cadf0eabc66e758c090063508688a18bf004
        </ItemHead>
        <ItemSubject>{pugjjig.uniSubject}</ItemSubject>
        <ItemContent>{pugjjig.uniContent}</ItemContent>
        <ItemImgBox>
            <Link to={`/pugjjig/${pugjjig.id}`} target="_blank">
            {
<<<<<<< HEAD
                pugjjig.files.length < 4 ?
                pugjjig.files.map((file) => (
                    <ImgList key={file.id}>
=======
                pugjjig.files.map((file) => (
                    <div key={file.id}>
>>>>>>> d119cadf0eabc66e758c090063508688a18bf004
                        <img 
                            src={require(`../../../../../../data/file/thumbnail/pugjjig/${file.fileThumbnail}`)}
                            alt="음식점 사진"
                        />
<<<<<<< HEAD
                    </ImgList>
                ))
                :
                <ImageSlide
                    images = {pugjjig.files}
                />
=======
                    </div>
                ))
>>>>>>> d119cadf0eabc66e758c090063508688a18bf004
            }
            </Link>
        </ItemImgBox>
        <ItemTagWrap>
<<<<<<< HEAD
            {
                pugjjig.uniTag.map((tag, index) => (
                    <ItemTag key={index}>#{tag}</ItemTag>
                ))
            }
        </ItemTagWrap>
        <ItemDetailWrap>
            <ItemStateWrap>
                <ItemDetail>
                    푹찍 {pugjjig.uniLike}개
                    댓글 0개
                </ItemDetail>
                <ItemDate>
                    {pugjjig.modifiedDate.split("T")[0]}
                </ItemDate>
            </ItemStateWrap>
        </ItemDetailWrap>
        <ItemBottom>
            <ItemState>
                <ItemLikeBtn onClick={() => pugjjigLike(pugjjig.id)}>
                    {
                        pugjjig.uniLikeState == true ?  
                        <SVG name={"heartCk"} width="16px" height="16px" color={"#d11d33"} />
                        :
                        <SVG name={"heart"} width="16px" height="16px" color={"#dddddd"} />
                    }
                    <ItemLikeText>
                        푹찍
                    </ItemLikeText>
                </ItemLikeBtn>

                <ItemLikeBtn>
                    <SVG name={"comment"} width="16px" height="16px" color={"#dddddd"} />
                    <ItemLikeText>
                        댓글
                    </ItemLikeText>
                </ItemLikeBtn>
            </ItemState>
        </ItemBottom>
    </PugjjigItem>
);

=======
            {pugjjig.uniName}
            {
                pugjjig.uniTag.map((tag) => (
                    <div key={tag}>{tag}</div>
                ))
            }
        </ItemTagWrap>
        <ItemStateWrap>
            푹찍 {pugjjig.uniLike}개
            {pugjjig.modifiedDate}
        </ItemStateWrap>
        <ItemState>
            {pugjjig.uniLikeState == true ? "푹찍~!" : "X"}
            <button type="button" onClick={() => pugjjigLike(pugjjig.id)}>푹찍</button>
        </ItemState>
    </PugjjigItem>
);

const ItemStarWrap = ({uniStar}) => {
    let starList = [];

    for(let i = 0; i <= uniStar; i++) {
        let data = <div>gg</div>;
        starList.push(data);
    }

    return(
        <ItemStar>{starList}</ItemStar>
    );
};

>>>>>>> d119cadf0eabc66e758c090063508688a18bf004
export default Item;