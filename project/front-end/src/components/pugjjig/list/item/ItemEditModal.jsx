import React, { Fragment } from "react"
import ReactTransitionGroup from "react-addons-css-transition-group"

import { 
    ModalWrap
} from "../../../../style/globalStyles";
import {
    ItemEditModalBox,
    ItemEditModalBtn
} from "../../style"

const ItemEditModal = ({ modalState, closeModal, openModal, handleDelete }) => (
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
                    <ItemEditModalBtn warring = {true} >게시물 신고</ItemEditModalBtn>
                    <ItemEditModalBtn 
                        onClick = {handleDelete}
                        warring = {true} 
                    >게시물 삭제</ItemEditModalBtn>
                    <ItemEditModalBtn onClick={() => openModal("editModalState")}>게시물 수정</ItemEditModalBtn>
                    <ItemEditModalBtn>공유하기</ItemEditModalBtn>
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