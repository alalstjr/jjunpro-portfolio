import {
    GET_ALARM_LIST,
    DELETE_ALARM
} from "../actions/types";

const initialState = {
    alarm: {}
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

        case DELETE_ALARM:
            return {
                ...state,
                alarm: {
                    data: action.payload
                }
            };

        default:
            return state;
    }
}
