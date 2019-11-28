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

    // file upload form
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

    // pugjjig update 일경우 id값 전달
    if(pugjjig.uniId !== null) {
        formData.append("id", pugjjig.uniId);
        formData.append("removeFiles", pugjjig.removeFiles);
    }
    
    files.forEach(function(file) {
        formData.append('files', file); 
    });
    
    // 유저 JWT Token정보
    USER_AUTH();

    await axios.post(`${SERVER_URL}/api/university`, formData, config)
    .then(res => {
        console.log(res);
        history.push(`/pugjjig/${res.data.id}`);
        dispatch({
            type: GET_PUGJJIG_VIEW,
            payload: res.data
        });
    }).catch(error => {
        console.log(error);
    });
}

// 푹찍 좋아요 추가&삭제
export const pugjjigLike = (id) => async dispatch => {
    
    // 유저 JWT Token정보
    USER_AUTH();

    await axios.post(`${SERVER_URL}/api/university/like/${id}`)
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
export const pugjjigGetUserList = (userId, offsetCount) =>  async dispatch => {

    axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
    
    // 유저 {아이디값,페이지} 의 전달이 없는 경우 기본값 설정
    userId = (userId === undefined || userId === null) ? USER_ID() : userId;
    offsetCount = (offsetCount == null) ? 0 : offsetCount;

    await axios.get(`${SERVER_URL}/api/university/pugjjigs/${userId}/${offsetCount}`)
    .then(res => {
        dispatch({
            type: GET_PUGJJIG_LIST,
            payload: res.data
        });
    }).catch(error => {
        alert(error.response.data.error);
    });
}

// 푹찍 리뷰 좋아요 {userId} 목록 조회
export const pugjjigLikeGetUserList = (userId, offsetCount) =>  async dispatch => {

    axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

    // 유저 {아이디값,페이지} 의 전달이 없는 경우 기본값 설정
    userId = (userId === undefined || userId === null) ? USER_ID() : userId;
    offsetCount = (offsetCount == null) ? 0 : offsetCount;

    await axios.get(`${SERVER_URL}/api/university/pugjjigLikes/${userId}/${offsetCount}`)
    .then(res => {
        dispatch({
            type: GET_PUGJJIG_LIST,
            payload: res.data
        });
    }).catch(error => {
        alert(error.response.data.error);
    });
}