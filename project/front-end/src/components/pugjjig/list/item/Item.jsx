import React, { Fragment } from "react"
import { Link } from "react-router-dom"
import ImageSlide from "../../../widget/mainTitleSlide"

import {
    ProfileIamge,
    FlexBottom
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
    ItemEditBtn,
    ItemLocalWrap
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
                        image = {require(`../../../../../../data/file/thumbnail/${pugjjig.photo.fileThumbnail}`)}
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
            <Link to={`/pugjjig/${pugjjig.id}`} target="_blank">
                <ItemSubject>{pugjjig.uniSubject}</ItemSubject>
                <ItemContent>
                        {pugjjig.uniContent}
                </ItemContent>
            </Link>
        <ItemImgBox>
            {
                pugjjig.files.length < 4 ?
                pugjjig.files.map((file) => (
                    <ImgList key={file.id}>
                        <img 
                            src={require(`../../../../../../data/file/thumbnail/${file.fileThumbnail}`)}
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
        <FlexBottom>
            <ItemLocalWrap>
                {
                    // SERVER 에서 불러오는 속도와 React 에서 storePublic 랜더링하는 속도와 맞춰주는 코드
                    pugjjig.storePublic !== null ?
                        <Fragment>
                            <Link to={`/pugjjig/uniSearch/${pugjjig.uniName}`}>
                                <span>{pugjjig.uniName}</span> 
                            </Link>
                            <Link to={`/pugjjig/stoSearch/${pugjjig.storePublic.stoId}`}>
                                <span>{pugjjig.storePublic.stoName}</span> 
                            </Link>
                            <a href = {`${pugjjig.storePublic.stoUrl}`} target="_blank">
                                {pugjjig.storePublic.stoAddress}
                            </a>
                        </Fragment>
                    :
                    null
                }
            </ItemLocalWrap>
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
                        댓글 {pugjjig.uniComment}개
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
                                <Link to={`/pugjjig/${pugjjig.id}`} target="_blank">
                                    댓글
                                </Link>
                            </ItemLikeText>
                    </ItemLikeBtn>

                    <ItemEditBtn onClick = {() => openModal("selectModalState", pugjjig)} >
                        <SVGEdit width="16px" height="16px" color={"#333333"} />
                    </ItemEditBtn>
                </ItemState>
            </ItemBottom>
        </FlexBottom>
    </PugjjigItem>
);

export default Item;