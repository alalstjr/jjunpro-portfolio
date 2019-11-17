import axios from "axios"
import { SERVER_URL, USER_AUTH, USER_ID } from "../routes"
import { 
    GET_PUGJJIG_LIST, 
    GET_PUGJJIG_STORE_LIST, 
    GET_PUGJJIG_LIKE, 
    GET_PUGJJIG_VIEW,
    GET_ERRORS
} from "./types"

/****************************************
    POST 푹찍 리뷰
****************************************/

// 푹찍 리뷰 작성
export const pugjjigInsert = (pugjjig, files, history) => async dispatch => {

    const formData = new FormData();
    const config = {
        headers: {
            'Accept': 'application/json',
            'content-type': 'multipart/form-data'
        },
        // File Upload 진행상황
        onUploadProgress: progressEvent => {
            console.log("업로드 진행률.." + Math.round(progressEvent.loaded / progressEvent.total*100) + "%");
        }
    };

    // pugjjig form 데이터
    formData.append("uniSubject", pugjjig.uniSubject);
    formData.append("uniContent", pugjjig.uniContent);
    formData.append("uniName", pugjjig.uniName);
    formData.append("uniTag", pugjjig.uniTag);
    formData.append("uniStar", pugjjig.uniStar);
    formData.append("stoId", pugjjig.stoId);
    formData.append("stoAddress", pugjjig.stoAddress);
    
    files.forEach(function(file) {
        formData.append('files', file); 
    });
    
    // 유저 JWT Token정보
    USER_AUTH();

    await axios.post(`${SERVER_URL}/api/university`, formData, config)
    .then(res => {
        history.push(`/pugjjig/${res.data.id}`);
    }).catch(error => {
        console.log(error);
    });
}

// 푹찍 좋아요 추가&삭제
export const pugjjigLike = (id) => async dispatch => {
    
    // 유저 JWT Token정보
    USER_AUTH();

    await axios.post(`${SERVER_URL}/api/university/${id}/like`)
    .then(res => {
        dispatch({
            type: GET_PUGJJIG_LIKE,
            payload: res.data
        });
    }).catch(error => {
        console.log(error);
    });
}

/****************************************
    GET 푹찍 리뷰 
****************************************/

// 푹찍 가게 {id} 조회
export const pugjjigGetStoreList = (storeId, offsetCount) => async dispatch => {

    // 유저 JWT Token정보
    USER_AUTH();

    await axios.get(`${SERVER_URL}/api/store/${storeId}/${offsetCount}`)
    .then(res => {
        dispatch({
            type: GET_PUGJJIG_STORE_LIST,
            payload: res.data
        });
    }).catch(error => {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });    
    })
}

// 푹찍 리뷰 {id} 조회
export const pugjjigGetView = (id, history) =>  async dispatch => {

    // 유저 JWT Token정보
    USER_AUTH();

    await axios.get(`${SERVER_URL}/api/university/${id}`)
    .then(res => {
        dispatch({
            type: GET_PUGJJIG_VIEW,
            payload: res.data
        });
    }).catch(error => {
        alert(error.response.data.error);
        history.push("/");
    });
}

// 푹찍 리뷰 {id} 갯수 조회
export const pugjjigGetCount = (storeId) =>  async dispatch => {
    
    // 유저 JWT Token정보
    USER_AUTH();
    
    return axios.get(`${SERVER_URL}/api/store/${storeId}/count`)
    .then(res => {
        return res.data;
    });

}

// 푹찍 리뷰 {userId} 목록 조회
export const pugjjigGetUserList = (history, userId) =>  async dispatch => {

    userId = (userId === undefined) ? USER_ID() : userId;

    await axios.get(`${SERVER_URL}/api/university/pugjjigs/${userId}`)
    .then(res => {
        dispatch({
            type: GET_PUGJJIG_LIST,
            payload: res.data
        });
    }).catch(error => {
        alert(error.response.data.error);
        history.push("/");
    });
}

// 푹찍 리뷰 좋아요 {userId} 목록 조회
export const pugjjigLikeGetUserList = (history, userId) =>  async dispatch => {

    userId = (userId === undefined) ? USER_ID() : userId;

    await axios.get(`${SERVER_URL}/api/university/pugjjigLikes/${userId}`)
    .then(res => {
        dispatch({
            type: GET_PUGJJIG_LIST,
            payload: res.data
        });
    }).catch(error => {
        alert(error.response.data.error);
        history.push("/");
    });
}