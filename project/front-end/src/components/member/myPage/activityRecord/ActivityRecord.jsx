import React, {Component, Fragment} from "react"
import List from "../../../pugjjig/list/List"
import {ActivityRecordWrap} from "../../style"

class ActivityRecord extends Component {

    render() {
        return (
            <ActivityRecordWrap>
                <List category={"username"}/>
            </ActivityRecordWrap>
        );
    }
}

export default ActivityRecord;