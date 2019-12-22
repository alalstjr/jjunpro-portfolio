import React, { Fragment } from "react";
import ReactTransitionGroup from "react-addons-css-transition-group";
import {
    FacebookShareButton,
    TwitterShareButton,
    TelegramShareButton 
  } from 'react-share';

import { 
    ModalWrap
} from "../../../../style/globalStyles";
import {
    ItemEditModalBox,
    ItemEditModalBtn
} from "../../style";

const ItemEditModal = ({ modalState, closeModal, openModal, handleDelete, edit }) => (
    <Fragment>
        {
            modalState ? 
            <ReactTransitionGroup
                transitionName={'Modal-anim'}
                transitionEnterTimeout={200}
                transitionLeaveTimeout={200}
            >
            <ModalWrap>
                <ItemEditModalBox>
                    {/* <ItemEditModalBtn warring = {true} >게시물 신고</ItemEditModalBtn> */}
                    {
                        edit ?
                        <Fragment>
                            <ItemEditModalBtn 
                                onClick = {handleDelete}
                                warring = {true} 
                            >게시물 삭제</ItemEditModalBtn>
                            <ItemEditModalBtn onClick={() => openModal("insertModalState")}>게시물 수정</ItemEditModalBtn>
                        </Fragment>
                        :
                        null
                    }
                    <ItemEditModalBtn>
                        <FacebookShareButton url={window.location.href}>
                            FaceBook 공유
                        </FacebookShareButton>
                    </ItemEditModalBtn>
                    <ItemEditModalBtn>
                        <TwitterShareButton url={window.location.href}>
                            Twitter 공유
                        </TwitterShareButton>
                    </ItemEditModalBtn>
                    <ItemEditModalBtn>
                        <TelegramShareButton url={window.location.href}>
                            Telegram 공유
                        </TelegramShareButton>
                    </ItemEditModalBtn>
                    <ItemEditModalBtn onClick={() => closeModal("selectModalState")}>취소</ItemEditModalBtn>
                </ItemEditModalBox>
            </ModalWrap>
            </ReactTransitionGroup>
            :
            <ReactTransitionGroup transitionName={'Modal-anim'} transitionEnterTimeout={200} transitionLeaveTimeout={200} />
        }
    </Fragment>
);

export default ItemEditModal;