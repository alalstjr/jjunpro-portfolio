import {
    GET_PUGJJIG,
    GET_PUGJJIG_VIEW,
    GET_PUGJJIG_VIEW_LIKE,
    GET_PUGJJIG_VIEW_LIKE_STATE,
    GET_PUGJJIG_COUNT,
    GET_PUGJJIG_USER,
    GET_PUGJJIG_LIKE_USER
} from "../actions/types"

const initialState = {
    pugjjig_list : {},
    pugjjig_view : {},
    pugjjig_view_like : {},
    pugjjig_view_like_state : {},
    pugjjig_count : {},
    pugjjig_list_user: {},
    pugjjig_list_like_user: {}
};

export default function(state=initialState, action) {
    switch(action.type) {

        case GET_PUGJJIG:
            return {
                ...state,
                pugjjig_list: {
                    data: action.payload.content[0]
                }
            };

        case GET_PUGJJIG_VIEW:
            return {
                ...state,
                pugjjig_view: {
                    data: action.payload
                }
            };

        case GET_PUGJJIG_VIEW_LIKE:
            return {
                ...state,
                pugjjig_view_like: {
                    data: action.payload
                }
            };

        case GET_PUGJJIG_VIEW_LIKE_STATE:
            return {
                ...state,
                pugjjig_view_like_state: {
                    data: action.payload
                }
            };

        case GET_PUGJJIG_COUNT:
            return {
                ...state,
                pugjjig_count: {
                    count: action.payload.count
                }
            };

        case GET_PUGJJIG_USER:
            return {
                ...state,
                pugjjig_list_user: {
                    data: action.payload.content
                }
            };

        case GET_PUGJJIG_LIKE_USER:
            return {
                ...state,
                pugjjig_list_like_user: {
                    data: action.payload.content
                }
            };

        default:
            return state;
    }
}
