import {GET_ERRORS} from "../actions/types"

const initialState = {}

export default function (state = initialState, actions) {
    switch (actions.type) {
        case GET_ERRORS:
            return {
                data: actions.payload
            };

        default:
            return state;
    }
}