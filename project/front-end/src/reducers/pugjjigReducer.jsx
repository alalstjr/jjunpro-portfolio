import {
    GET_PUGJJIG_LIST,
    GET_PUGJJIG_STORE_LIST,
    GET_PUGJJIG_LIKE,
    GET_PUGJJIG_VIEW,
    GET_PUGJJIG_COUNT
} from "../actions/types"

const initialState = {
    pugjjig_list: {},
    pugjjig_store_list : {},
    pugjjig_like: {},
    pugjjig_view : {},
    pugjjig_count : {}
};

export default function(state=initialState, action) {
    switch(action.type) {

        case GET_PUGJJIG_LIST:
            return {
                ...state,
                pugjjig_list: {
                    data: action.payload.content
                }
            };

        case GET_PUGJJIG_STORE_LIST:
            return {
                ...state,
                pugjjig_store_list: {
                    data: action.payload.content[0].stoUniList
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

        default:
            return state;
    }
}
