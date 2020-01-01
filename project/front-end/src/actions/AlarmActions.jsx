import axios from "axios";
import { SERVER_URL } from "../routes";
import { GET_ALARM_LIST, TEMP_ALARM_LIST, DELETE_ALARM } from "./types";

/****************************************
    현재 불러온 DATA를 담은 List
****************************************/
export const tempAlarmList = (reset, temp_data, postData) => dispatch => {
    dispatch({
        type: TEMP_ALARM_LIST,
        payload: reset ? [] : temp_data.concat(postData)
    });
}
export const tempAlarmListUpdate = (postData) => dispatch => {
    dispatch({
        type: TEMP_ALARM_LIST,
        payload: postData
    });
}

/****************************************
    GET Alarm List DATA
****************************************/
export const getAlarmList = () =>  async dispatch => {
    try {
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
        const res = await axios.get(`${SERVER_URL}/api/alarm`);

        switch(res.status) {
            case 200 :
                dispatch({
                    type: GET_ALARM_LIST,
                    payload: res.data
                });
                break;

            default :
                alert("잘못된 접근입니다.");
        }
    } catch(error) {
        alert(error.response.data.error);
    }
}

/****************************************
    DELETE Alarm DATA
****************************************/
export const deleteAlarmId = (id) => async dispatch => {
    try {
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
        const res = await axios.delete(`${SERVER_URL}/api/alarm/${id}`);

        switch(res.status) {
            case 200 :
                dispatch({
                    type: DELETE_ALARM,
                    payload: id
                });
                break;

            default :
                alert("잘못된 접근입니다.");
        }
    } catch(error) {
        alert(error.response.data.error);
    }
}