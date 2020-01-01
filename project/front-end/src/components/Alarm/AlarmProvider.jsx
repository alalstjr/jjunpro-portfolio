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
        if(USER_AUTH()) {                        
            this.props.getAlarmList();
        }
    }

    componentWillReceiveProps(nextProps) {
        const {
            alarm,
            alarm_delete
        } = this.props;

        if(nextProps.alarm !== alarm) {
            this.handleUpdate(nextProps.alarm);
        }

        // 알람 아이템 삭제 메소드
        if(nextProps.alarm_delete !== alarm_delete) {
            this.handleDeleteUpdate(nextProps.alarm_delete.data);
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
        const { alarm, temp_alarm_list, user_info, deleteAlarmId, alarm_delete } = this.props;
        const { modalState } = this.state;

        return (
            <AlarmWrap>
                {
                    (alarm.data !== undefined && user_info.data !== undefined) ? 
                        <Fragment>
                            <div onClick={this.handleModalState}>
                                {
                                    (temp_alarm_list.length > 0) ?
                                    <SVGAlarmOn width="60px" height="60px" color={"#d11d33"} />
                                    :
                                    <SVGAlarm width="60px" height="60px" color={"#ddd"} />
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
    error: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    alarm: state.alarm.alarm,
    temp_alarm_list: state.alarm.temp_alarm_list,
    alarm_delete: state.alarm.alarm_delete,
    user_info: state.account.user_info,
    error: state.errors
});
  
export default connect(
    mapStateToProps, 
    { getAlarmList, deleteAlarmId, tempAlarmList, tempAlarmListUpdate }
)(AlarmProvider);