import axios from "axios";
import { 
    GET_ERRORS, 
    GET_BOARD_TASKS,
    GET_BOARD_TASK,
    DELETE_BOARD_TASK,
    GET_BOARD_PAGING
} from "./types";

/*
 *  게시판 게시글을 추가합니다.
 */
export const boardTaskInsert = (board_task, history, files, removeFiles) => async dispatch => {
    try {

        // 유저 JWT Token정보
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

        // Write Update Set
        await axios.post("http://localhost:8080/api/board/", board_task)
        .then(res => {

            // File Upload Set
            if( board_task.files !== 0 || removeFiles.length !== 0) {
                let boNum = 0;
                boNum = res.data.num;
                dispatch(
                    fileUpload(boNum, files, removeFiles, history)
                );
            } else {
                history.push("/");
            }
        });
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

/*
 *  게시판 게시글을 삭제합니다.
 */
export const boardTaskDelete = (bo_num, history) => async dispatch => {
    await axios.delete(`http://localhost:8080/api/board/${bo_num}`);
    dispatch({
        type: DELETE_BOARD_TASK,
        payload: bo_num
    });
    history.push("../");
}

/*
 *  모든 게시판 목록을 불러옵니다. (리스트)
 */
export const getBoardTasks = () => async dispatch => {
    const res = await axios.get("http://localhost:8080/api/board/");
    dispatch({
        type: GET_BOARD_TASKS,
        payload: res.data
    });
}

/*
 *  특정 카테고리 게시판 목록을 불러옵니다.
 */
export const getBoardTasksCate = (bo_cate, history) => async dispatch => {
    try {
        const res = await axios.get(`http://localhost:8080/api/board/all/cate/${bo_cate}`);
        dispatch({
            type: GET_BOARD_TASKS,
            payload: res.data
        });
    } catch (error) {
        history.push("/");
    }
}

/*
 *  특정 게시판 목록을 불러옵니다. (상세페이지)
 */
export const getBoardTask = (bo_num, history) => async dispatch => {
    try {
        const res = await axios.get(`http://localhost:8080/api/board/${bo_num}`);
        dispatch({
            type: GET_BOARD_TASK,
            payload: res.data
        });
    } catch (error) {
        alert(error.response.data.error);
        history.push("/");
    }
}

/*
 *  파일을 업로드 합니다.
 */
export const fileUpload = (Num, files, removeFiles, history) => async dispatch => {
    try {
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
        formData.append('num', Num);
        formData.append('removeFiles', removeFiles);

        await axios.post("http://localhost:8080/uploadMultipleFiles", formData, config)
        .then(res => {
            console.log(res);
            history.push("/");
        });
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

/*
 *  페이징 & 게시글 조회
 */
export const getPaging = (page) => async dispatch => {
    if(page) {
        const res = await axios.get(`http://localhost:8080/api/board/page?page=${page}`);
        dispatch({
            type: GET_BOARD_PAGING,
            payload: res.data
        });
    } else {
        const res = await axios.get("http://localhost:8080/api/board/page");
        dispatch({
            type: GET_BOARD_PAGING,
            payload: res.data
        });
    }
}

/*
 *  페이징 & 특정 카테고리 게시글 조회
 */
export const getPagingCate = (bo_cate) => async dispatch => {
    const res = await axios.get(`http://localhost:8080/api/board/cate/${bo_cate}`);
    dispatch({
        type: GET_BOARD_PAGING,
        payload: res.data
    });
}