import { GET_BOARD_TASKS, GET_BOARD_TASK } from "../actions/types"

const initialState = {
    board_tasks : [],
    board_task: {},
    board_task_files: []
}

export default function(state=initialState, action) {
    switch(action.type) {

        case GET_BOARD_TASKS:
            return {
                ...state,
                board_tasks: action.payload
            };
        case GET_BOARD_TASK:
            return {
                ...state,
                board_task: action.payload.boardTask,
                board_task_files: action.payload.files
            };
        default:
            return state;
    }
}