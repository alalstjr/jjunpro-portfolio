import {
    GET_UNIVERSITY_COUNT,
    TEMP_UNIVERSITY_LIST,
    GET_PUGJJIG_LIST,
    GET_PUGJJIG_LIKE,
    GET_PUGJJIG_VIEW,
    GET_PUGJJIG_COUNT,
    GET_UNIVERSITY,
    DELETE_PUGJJIG,
    GET_PUGJJIG_COMMENT,
    GET_PUGJJIG_COMMENT_LIST,
    DELETE_PUGJJIG_COMMENT
} from "../actions/types"

const initialState = {
    uni_count: {},
    temp_pugjjig_list : [],
    pugjjig_list : {},
    pugjjig_like : {},
    pugjjig_view : {},
    pugjjig_count : {},
    pugjjig_university: "",
    pugjjig_delete : 0,
    pugjjig_comment: {},
    pugjjig_comment_list: {},
    pugjjig_comment_delete : 0
};

export default function(state=initialState, action) {
    switch(action.type) {

        case GET_UNIVERSITY_COUNT:
            return {
                ...state,
                uni_count: action.payload.count
            };

        case TEMP_UNIVERSITY_LIST:
            return {
                ...state,
                temp_pugjjig_list: action.payload
            };

        case GET_PUGJJIG_LIST:
            return {
                ...state,
                pugjjig_list: {
                    data: action.payload
                }
            };

        case GET_PUGJJIG_LIKE:
            return {
                ...state,
                pugjjig_like: {
                    data: action.payload
                }
            };

        case GET_PUGJJIG_VIEW:
            return {
                ...state,
                pugjjig_view: {
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

        case GET_UNIVERSITY:
            return {
                ...state,
                pugjjig_university: action.payload
            };

        case DELETE_PUGJJIG:
            return {
                ...state,
                pugjjig_delete: action.payload
            };

        case GET_PUGJJIG_COMMENT:
            return {
                ...state,
                pugjjig_comment: {
                    data: [action.payload]
                }
            };

        case GET_PUGJJIG_COMMENT_LIST:
            return {
                ...state,
                pugjjig_comment_list: {
                    data: action.payload
                }
            };

        case DELETE_PUGJJIG_COMMENT:
            return {
                ...state,
                pugjjig_comment_delete: action.payload
            };

        default:
            return state;
    }
}
