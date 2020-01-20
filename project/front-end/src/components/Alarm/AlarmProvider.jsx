import React, { Component, Fragment } from 'react';
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { getAlarmList, deleteAlarmId } from "../../actions/AlarmActions";
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
            user_info,
            getAlarmList,
            logged
        } = this.props;

        // 유저가 로그인 했을경우 알람 업데이트 실행
        if(nextProps.user_info !== user_info) {
            if(nextProps.logged)
                getAlarmList();
        }
    }

    handleModalState = () => {
        const { modalState } = this.state;
        this.setState({
            modalState: !modalState
        });
    }

    render() {
        const { alarm, deleteAlarmId, alarm_delete } = this.props;
        const { modalState } = this.state;
        return (
            <AlarmWrap>
                {
                    (alarm.data !== undefined) && (USER_AUTH()) ? 
                        <Fragment>
                            <div onClick={this.handleModalState}>
                                {
                                    (alarm.data.length > 0) ?
                                    <SVGAlarmOn width="50px" height="50px" color={"#d11d33"} />
                                    :
                                    <SVGAlarm width="50px" height="50px" color={"#ddd"} />
                                }
                            </div>
                            <AlarmItem
                                item             = {alarm.data}
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
    alarm: PropTypes.object.isRequired,
    user_info: PropTypes.object.isRequired,
    logged: PropTypes.bool.isRequired,
    error: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    alarm: state.alarm.alarm,
    alarm_delete: state.alarm.alarm_delete,
    user_info: state.account.user_info,
    logged: state.account.logged,
    error: state.errors
});
  
export default connect(
    mapStateToProps, 
    { getAlarmList, deleteAlarmId }
)(AlarmProvider);