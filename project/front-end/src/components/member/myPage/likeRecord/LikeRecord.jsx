import React, {Component, Fragment} from "react"
import List from "../../../pugjjig/list/List"
import {ActivityRecordWrap} from "../../style"

class LikeRecord extends Component {

    render() {
        return (
            <ActivityRecordWrap>
                <List category={"uniLike"}/>
            </ActivityRecordWrap>
        );
    }
}

export default LikeRecord;