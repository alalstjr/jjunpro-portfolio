import React from "react"
import { Link } from "react-router-dom"
import ImageSlide from "../../../widget/mainTitleSlide"

import {
    ProfileIamge
} from "../../../../style/globalStyles"
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
    ItemState,
    ImgList,
    ItemDetailWrap,
    ItemDetail,
    ItemDate,
    ItemBottom,
    ItemTag,
    ItemLikeBtn,
    ItemLikeText,
    ItemEditBtn
} from "../../style"

import SVG from "../../../../static/svg/SVG"
import SVGEdit from "../../../../static/svg/SVGEdit"

const Item = ({pugjjig, pugjjigLike, openModal}) => (
    <PugjjigItem>
        <ItemHead>
            <ItemUserPhoto>
                {
                    pugjjig.photo === null ?
                    <SVG name={"user"} width="38px" height="38px" color={"#E71D36"} />
                    : 
                    <ProfileIamge
                        image = {require(`../../../../../../data/file/thumbnail/pugjjig/${pugjjig.photo.fileThumbnail}`)}
                    />
                }
            </ItemUserPhoto>
            <ItemRight>
                <ItemUsername>{pugjjig.account_nickname}</ItemUsername>
                <ItemUserInfo>유저정보</ItemUserInfo>
            </ItemRight>
            {
                pugjjig.uniStar === 1 ?
                <ItemStar>
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                </ItemStar>
                :
                pugjjig.uniStar === 2 ?
                <ItemStar>
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                </ItemStar>
                :
                pugjjig.uniStar === 3 ?
                <ItemStar>
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                </ItemStar>
                :
                pugjjig.uniStar === 4 ?
                <ItemStar>
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                </ItemStar>
                :
                <ItemStar>
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                <SVG name={"star"} width="14px" height="14px" color={"#ffd700"} />
                </ItemStar>
            }
        </ItemHead>
        <ItemSubject>{pugjjig.uniSubject}</ItemSubject>
        <ItemContent>
            <Link to={`/pugjjig/${pugjjig.id}`} target="_blank">
                {pugjjig.uniContent}
            </Link>
        </ItemContent>
        <ItemImgBox>
            {
                pugjjig.files.length < 4 ?
                pugjjig.files.map((file) => (
                    <ImgList key={file.id}>
                        <img 
                            src={require(`../../../../../../data/file/thumbnail/pugjjig/${file.fileThumbnail}`)}
                            alt="음식점 사진"
                        />
                    </ImgList>
                ))
                :
                <ImageSlide
                    slideShow = {3}
                    images = {pugjjig.files}
                    thumbnail = {true}
                />
            }
        </ItemImgBox>
        <ItemTagWrap>
            {
                pugjjig.uniTag.map((tag, index) => (
                    <ItemTag key={index}>#{tag}</ItemTag>
                ))
            }
        </ItemTagWrap>
        <ItemDetailWrap>
            <ItemStateWrap>
                <ItemDetail>
                    좋아요 {pugjjig.uniLike}개
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
                        <SVG name={"heartCk"} width="14px" height="14px" color={"#d11d33"} />
                        :
                        <SVG name={"heart"} width="14px" height="14px" color={"#dddddd"} />
                    }
                    <ItemLikeText>
                        좋아요
                    </ItemLikeText>
                </ItemLikeBtn>

                <ItemLikeBtn>
                    <SVG name={"comment"} width="14px" height="14px" color={"#dddddd"} />
                    <ItemLikeText>
                        댓글
                    </ItemLikeText>
                </ItemLikeBtn>

                <ItemEditBtn onClick = {() => openModal("selectModalState", pugjjig)} >
                    <SVGEdit width="16px" height="16px" color={"#333333"} />
                </ItemEditBtn>
            </ItemState>
        </ItemBottom>
    </PugjjigItem>
);

export default Item;