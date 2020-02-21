import {
    MODAL_ACCOUNT,
    ACCOUNT_CREATE,
    ACCOUNT_GET,

    CHECK_USER,
    CHECK_USER_SUCCESS,
    CHECK_USER_FAILURE
} from "../actions/types"

const initialState = {
    modal_account: {},
    user_info: {},
    account_create: {},
    account_get: {},
    logged: false
};

export default function (state = initialState, action) {

    switch (action.type) {

        case MODAL_ACCOUNT:
            return {
                ...state,
                modal_account: {
                    login: action.payload.login,
                    sign_up: action.payload.sign_up
                }
            };

        case ACCOUNT_CREATE:
            return {
                ...state,
                account_create: {
                    data: action.payload
                }
            };

        case ACCOUNT_GET:
            return {
                ...state,
                account_get: {
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
                        username: action.payload.id,
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
                        username: action.payload.id,
                        username: action.payload.username
                    }
                }
            };

        case CHECK_USER_FAILURE:
            return {
                ...state,
                logged: false,
                user_info: action.payload
            };

        default:
            return state;
    }
}
