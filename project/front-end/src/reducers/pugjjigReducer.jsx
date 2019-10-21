import {
    GET_PUGJJIG,
    GET_PUGJJIG_VIEW,
    GET_PUGJJIG_VIEW_LIKE,
    GET_PUGJJIG_COUNT
} from "../actions/types"

const initialState = {
    pugjjig_list : {},
    pugjjig_view : {},
    pugjjig_view_like : {},
    pugjjig_count : {}
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
