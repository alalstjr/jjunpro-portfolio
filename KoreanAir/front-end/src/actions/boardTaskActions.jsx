import axios from "axios";
import { GET_ERRORS, GET_BOARD_TASKS } from "./types";

export const insertBoardTask = (board_task, history, files) => async dispatch => {
    try {
        /*
         * Global variable
         */
        let boNum = 0;

        /*
         * Write Update Set
         */
        await axios.post("http://localhost:8080/api/board/insert", board_task)
        .then(res => {
            boNum = res.data.num;
        });

        /*
         * File Upload Set
         */
        if( files.length !== 0 ) { 
            const formData = new FormData();
            const config = {
                headers: {
                    'content-type': 'multipart/form-data'
                },
                // File Upload 진행상황
                onUploadProgress: progressEvent => {
                    console.log("업로드 진행률.." + Math.round(progressEvent.loaded / progressEvent.total*100) + "%");
                }
            };

            // File 여러개 전송되는경우 formData에 각각의 파일을 담아줍니다.
            files.forEach(function(file) {
                formData.append('files', file);
            });
            formData.append('bo_num', boNum);

            await axios.post("http://localhost:8080/uploadMultipleFiles", formData, config)
            .then(res => {
                console.log(res);
            });
        }

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