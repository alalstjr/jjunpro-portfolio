import React, { Component, Fragment } from "react"
import UserIdList from "../../../pugjjig/list/userIdList/UserIdList"
import { ActivityRecordWrap } from "../../style"

class ActivityRecord extends Component {

    render() {
        return (
            <ActivityRecordWrap>
                <UserIdList/>
            </ActivityRecordWrap>
        );
    }
}

export default ActivityRecord;