import React, { Fragment } from "react";
import { Link } from "react-router-dom";
import ImageSlide from "../../../widget/mainTitleSlide";
import { SERVER_FILE_URL } from "../../../../routes";

import {
    ProfileIamge,
    FlexBottom
} from "../../../../style/globalStyles";
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
} from "../../style";

import SVG from "../../../../static/svg/SVG";
import SVGEdit from "../../../../static/svg/SVGEdit";
import SVGYoutube from "../../../../static/svg/SVGYoutube";
import SVGFacebook from "../../../../static/svg/SVGFacebook";
import SVGInstar from "../../../../static/svg/SVGInstar";
import SVGLink from "../../../../static/svg/SVGLink";

const Item = ({pugjjig, UpdateUniLikeUniId, openModal}) => (
    <PugjjigItem>
        <ItemHead>
            <ItemUserPhoto>
                {
                    pugjjig.photo === null ?
                    <SVG name={"user"} width="38px" height="38px" color={"#E71D36"} />
                    : 
                    <ProfileIamge
                        image = {`${SERVER_FILE_URL}${pugjjig.photo.fileOriginal}`}
                    />
                }
            </ItemUserPhoto>
            <ItemRight>
                <ItemUsername>
                    <Link to={`/pugjjig/userSearch/${pugjjig.account_nickname}`}>
                        {pugjjig.account_nickname}
                    </Link>
                </ItemUsername>
                <ItemUserInfo>
                    {
                        (pugjjig.account_urlList !== null && pugjjig.account_urlList.length > 0) ?
                        pugjjig.account_urlList.map((url, index) => (
                            <a key={index} href={url} target="_blank">
                                {
                                    url.indexOf("instagram") !== -1 ? 
                                    <SVGInstar width="14px" height="14px"/>
                                    :
                                    url.indexOf("facebook") !== -1 ? 
                                    <SVGFacebook width="14px" height="14px"/>
                                    :
                                    url.indexOf("youtube") !== -1 ? 
                                    <SVGYoutube width="14px" height="14px"/>
                                    :
                                    <SVGLink width="14px" height="14px"/>
                                }
                            </a>
                        ))
                        :
                        "회원"
                    }
                </ItemUserInfo>
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
                            src={`${SERVER_FILE_URL}${file.fileThumbnail}`}
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
                            {
                                pugjjig.uniName !== "" ?
                                <Link to={`/pugjjig/uniSearch/${pugjjig.uniName}`}>
                                    <span>{pugjjig.uniName}</span> 
                                </Link>
                                :
                                null
                            }
                            <Link to={`/pugjjig/stoSearch/${pugjjig.storePublic.stoName}`}>
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
                    pugjjig.uniTag !== "" ?
                        pugjjig.uniTag.split(",").map((tag, index) => (
                            <ItemTag key={index}>
                                <Link to={`/pugjjig/tagSearch/${tag}`}>#{tag}</Link>
                            </ItemTag>
                        ))
                    :
                    null
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
                    <ItemLikeBtn onClick={() => UpdateUniLikeUniId(pugjjig.id)}>
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