import { GET_BOARD_TASKS } from "../actions/types"

const initialState = {
    board_tasks : [],
    board_task: {}
}

export default function(state=initialState, action) {
    switch(action.type) {

        case GET_BOARD_TASKS:
            return {
                ...state,
                board_tasks: action.payload
            };
        default:
            return state;
    }
}