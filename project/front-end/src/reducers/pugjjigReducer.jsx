import {
    GET_PUGJJIG_COUNT
} from "../actions/types"

const initialState = {
    pugjjig : {}
};

export default function(state=initialState, action) {
    switch(action.type) {

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
