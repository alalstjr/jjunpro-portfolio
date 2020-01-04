import React, { Component, Fragment } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { getAlarmList, deleteAlarmId, tempAlarmList, tempAlarmListUpdate } from "../../actions/AlarmActions";
import { USER_AUTH } from "../../routes";
import AlarmItem from "./AlarmItem";

import SVGAlarm from "../../static/svg/SVGAlarm";
import SVGAlarmOn from "../../static/svg/SVGAlarmOn";
import { AlarmWrap } from "./style";

class AlarmProvider extends Component {

    constructor(props){
        super(props);

        this.state = {
            modalState: false
        }
      }

    componentDidMount() {
        const { getAlarmList } = this.props;

        // 유저일경우 알람 업데이트 실행
        if(USER_AUTH())
            getAlarmList();
    }

    componentWillReceiveProps(nextProps) {
        const {
            alarm,
            alarm_delete,
            user_info,
            getAlarmList,
            logged
        } = this.props;

        // alarm 데이터값을 불러올경우 업데이트
        if(nextProps.alarm !== alarm) 
            this.handleUpdate(nextProps.alarm);

        // 알람 아이템 삭제 메소드
        if(nextProps.alarm_delete !== alarm_delete) 
            this.handleDeleteUpdate(nextProps.alarm_delete.data);

        // 유저가 로그인 했을경우 알람 업데이트 실행
        if(nextProps.user_info !== user_info) {
            if(nextProps.logged)
                getAlarmList();
        }
    }

    handleUpdate = (postData) => {
        const { 
            tempAlarmList,
            temp_alarm_list
        } = this.props;
        
        tempAlarmList(false, temp_alarm_list, postData.data);
    }

    handleDeleteUpdate = (postAlarm) => {
        // State Init
        const { temp_alarm_list, tempAlarmListUpdate } = this.props;
        let tempList = temp_alarm_list.filter(pugjjig => pugjjig.id !== postAlarm);
        
        tempAlarmListUpdate(tempList);
    }

    handleModalState = () => {
        const { modalState } = this.state;
        this.setState({
            modalState: !modalState
        });
    }

    render() {
        const { alarm, temp_alarm_list, deleteAlarmId, alarm_delete } = this.props;
        const { modalState } = this.state;

        return (
            <AlarmWrap>
                {
                    (alarm.data !== undefined) ? 
                        <Fragment>
                            <div onClick={this.handleModalState}>
                                {
                                    (temp_alarm_list.length > 0) ?
                                    <SVGAlarmOn width="50px" height="50px" color={"#d11d33"} />
                                    :
                                    <SVGAlarm width="50px" height="50px" color={"#ddd"} />
                                }
                            </div>
                            <AlarmItem
                                item             = {temp_alarm_list}
                                modalState       = {modalState}
                                handleModalState = {this.handleModalState}
                                deleteAlarmId    = {deleteAlarmId}
                                alarm_delete     = {alarm_delete}
                            />
                        </Fragment>
                    :
                    null
                }
            </AlarmWrap>
        )
    }
}

AlarmProvider.propTypes = {
    getAlarmList: PropTypes.func.isRequired,
    deleteAlarmId: PropTypes.func.isRequired,
    tempAlarmList: PropTypes.func.isRequired,
    tempAlarmListUpdate: PropTypes.func.isRequired,
    alarm: PropTypes.object.isRequired,
    user_info: PropTypes.object.isRequired,
    temp_alarm_list: PropTypes.array.isRequired,
    logged: PropTypes.bool.isRequired,
    error: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    alarm: state.alarm.alarm,
    temp_alarm_list: state.alarm.temp_alarm_list,
    alarm_delete: state.alarm.alarm_delete,
    user_info: state.account.user_info,
    logged: state.account.logged,
    error: state.errors
});
  
export default connect(
    mapStateToProps, 
    { getAlarmList, deleteAlarmId, tempAlarmList, tempAlarmListUpdate }
)(AlarmProvider);