import React, { Component, Fragment } from "react"
import LikeList from "../../../pugjjig/list/likeList/LikeList"
import { ActivityRecordWrap } from "../../style"

class LikeRecord extends Component {

    render() {
        return (
            <ActivityRecordWrap>
                <LikeList/>
            </ActivityRecordWrap>
        );
    }
}

export default LikeRecord;