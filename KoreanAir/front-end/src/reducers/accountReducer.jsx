import {
    CHECK_USER,
    CHECK_USER_SUCCESS,
    CHECK_USER_FAILURE
} from "../actions/types"

const initialState = {
    userInfo : {}
};

export default function(state=initialState, action) {
    
    switch(action.type) {

        case CHECK_USER:
            return {
                ...state,
                logged: true,
                userInfo: {
                    token: action.payload.token,
                    userId: action.payload.id,
                    username: action.payload.username
                }
            };

        case CHECK_USER_SUCCESS:
            return {
                ...state,
                logged: true,
                userInfo: {
                    token: action.payload.token,
                    userId: action.payload.id,
                    username: action.payload.username
                }
            };

        case CHECK_USER_FAILURE:
            return {
                ...state,
                userInfo: action.payload
            };

        default:
            return state;
    }
}
