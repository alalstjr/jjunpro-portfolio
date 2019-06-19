import axios from "axios";
import { GET_ERRORS, GET_BOARD_TASKS } from "./types";

export const insertBoardTask = (board_task, history) => async dispatch => {
    try {
        await axios.post("http://localhost:8080/api/board/insert", board_task);
        history.push("/");
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

export const getBoardTasks = () => async dispatch => {
    const res = await axios.get("http://localhost:8080/api/board/all");
    dispatch({
        type: GET_BOARD_TASKS,
        payload: res.data
    });
}