import { 
    GET_BOARD_TASKS, 
    GET_BOARD_TASK,
    DELETE_BOARD_TASK,
    GET_BOARD_PAGING
} from "../actions/types"

const initialState = {
    board_tasks : [],
    board_task: {},
    board_task_files: [],
    board_task_img_files: [],
    board_paging: {}
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
                board_task_files: action.payload.files,
                board_task_img_files: action.payload.imgFiles
            };
        case DELETE_BOARD_TASK:
            return {
                ...state,
                board_tasks: state.board_tasks.filter(
                    board_task => board_task.num !== action.payload
                )
            };
        case GET_BOARD_PAGING:
            return {
                ...state,
                board_paging: action.payload
            };
        default:
            return state;
    }
}