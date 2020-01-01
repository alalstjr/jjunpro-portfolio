import React, { Component } from 'react';
import { Link } from "react-router-dom";
import SVG from "../../static/svg/SVG";

import { AlarmItemWrap, AlarmHead, AlarmTitle, Item, ItemHead, ItemContent, ItemNone, ItemWrite, ItemDate, ItemWrap } from "./style";

class AlarmItem extends Component {

    render() {
        const { item, modalState, handleModalState, deleteAlarmId } = this.props;
        let alarmContent;
        let alarmList = [];

        const alarmType = (dataType) => {
            switch(dataType) {
                case "Comment" :
                    return "댓글";

                default:
                    return "알림";
            }
        }

        // alarmList
        const alarmGet = (item) => {
            const data = item.map((item, index) => {
                return (
                    <Item key={index}>
                        <ItemHead>
                            <div>{alarmType(item.dataType)} 알림</div>
                            <ItemDate>
                                {item.modifiedDate.split("T")[0]}
                                <button 
                                    type    = {"button"}
                                    onClick = {() => deleteAlarmId(item.id)}
                                ><SVG name={"close"} width="10px" height="10px" color={"#8a8a8a"} /></button>
                            </ItemDate>
                        </ItemHead>
                        <Link to={`/pugjjig/${item.dataId}`} target="_blank">
                            <ItemContent>
                                <ItemWrite>{item.writeId}</ItemWrite>
                                <div>{item.dataContent}</div>
                            </ItemContent>
                        </Link>
                    </Item>
                );
            });

            for(let i = 0; i < data.length; i++) {
                alarmList.push(data[i]);
            }

            if(item.length > 0) {
                return (
                    <ItemWrap>
                        {alarmList}
                    </ItemWrap>
                );
            } else {
                return (
                    <ItemNone>
                        새로운 알림 없음
                    </ItemNone>
                );
            }
        }
        
        // alarmList Get List View
        alarmContent = alarmGet(item);

        return (
            <AlarmItemWrap
                modalState = {modalState}
            >
                <AlarmHead>
                    <AlarmTitle>알림톡</AlarmTitle>
                    <div onClick={handleModalState}>
                        <button type={"button"}><SVG name={"close"} width="20px" height="20px" color={"#8a8a8a"} /></button>
                    </div>
                </AlarmHead>
                {alarmContent}
            </AlarmItemWrap>
        )
    }
}

export default AlarmItem;