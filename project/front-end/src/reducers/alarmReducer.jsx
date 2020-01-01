import {
    GET_ALARM_LIST,
    TEMP_ALARM_LIST,
    DELETE_ALARM
} from "../actions/types";

const initialState = {
    alarm: {},
    temp_alarm_list: []
};

export default function(state=initialState, action) {
    
    switch(action.type) {

        case GET_ALARM_LIST:
            return {
                ...state,
                alarm: {
                    data: action.payload
                }
            };

        case TEMP_ALARM_LIST:
            return {
                ...state,
                temp_alarm_list: action.payload
            };

        case DELETE_ALARM:
            return {
                ...state,
                alarm_delete: {
                    data: action.payload
                }
            };

        default:
            return state;
    }
}
