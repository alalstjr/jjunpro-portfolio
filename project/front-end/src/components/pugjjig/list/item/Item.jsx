import React from "react"
import { Link } from "react-router-dom"
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
    ItemState
} from "../../style"

const Item = ({pugjjig, pugjjigLike}) => (
    <PugjjigItem>
        <ItemHead>
            <ItemUserPhoto></ItemUserPhoto>
            <ItemRight>
                <ItemUsername>{pugjjig.account_nickname}</ItemUsername>
                <ItemUserInfo>유저정보</ItemUserInfo>
            </ItemRight>
            <ItemStarWrap
                uniStar = {pugjjig.uniStar}
            />
        </ItemHead>
        <ItemSubject>{pugjjig.uniSubject}</ItemSubject>
        <ItemContent>{pugjjig.uniContent}</ItemContent>
        <ItemImgBox>
            <Link to={`/pugjjig/${pugjjig.id}`} target="_blank">
            {
                pugjjig.files.map((file) => (
                    <div key={file.id}>
                        <img 
                            src={require(`../../../../../../data/file/thumbnail/pugjjig/${file.fileThumbnail}`)}
                            alt="음식점 사진"
                        />
                    </div>
                ))
            }
            </Link>
        </ItemImgBox>
        <ItemTagWrap>
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

export default Item;