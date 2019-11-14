import {
    ACCOUNT_CREATE,

    CHECK_USER,
    CHECK_USER_SUCCESS,
    CHECK_USER_FAILURE
} from "../actions/types"

const initialState = {
    user_info : {},
    account_create: {}
};

export default function(state=initialState, action) {
    
    switch(action.type) {

        case ACCOUNT_CREATE:
            return {
                ...state,
                account_create: {
                    data: action.payload
                }
            };

        case CHECK_USER:
            return {
                ...state,
                logged: true,
                user_info: {
                    data: {
                        token: action.payload.token,
                        userId: action.payload.id,
                        username: action.payload.username
                    }
                }
            };

        case CHECK_USER_SUCCESS:
            return {
                ...state,
                logged: true,
                user_info: {
                    data: {
                        token: action.payload.token,
                        userId: action.payload.id,
                        username: action.payload.username
                    }
                }
            };

        case CHECK_USER_FAILURE:
            return {
                ...state,
                user_info: action.payload
            };

        default:
            return state;
    }
}
