import React, { Component, Fragment } from "react"
import List from "../../../pugjjig/list/List"
import { ActivityRecordWrap } from "../../style"

class ActivityRecord extends Component {

    render() {
        return (
            <ActivityRecordWrap>
                <List classification = {"userId"} />
            </ActivityRecordWrap>
        );
    }
}

export default ActivityRecord;