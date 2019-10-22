import axios from "axios"
import { SERVER_URL, USER_AUTH } from "../routes"
import { GET_PUGJJIG, GET_ERRORS, GET_PUGJJIG_VIEW, GET_PUGJJIG_VIEW_LIKE, GET_PUGJJIG_VIEW_LIKE_STATE } from "./types"

/****************************************
    POST 푹찍 리뷰
****************************************/

// 푹찍 리뷰 작성
export const pugjjigInsert = (pugjjig) => {
    
    // 유저 JWT Token정보
    USER_AUTH();

    axios.post(`${SERVER_URL}/api/university`, pugjjig)
    .then(res => {
        console.log(res);
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
            type: GET_PUGJJIG_VIEW_LIKE,
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
export const pugjjigGet = (storeId) => async dispatch => {

    // 유저 JWT Token정보
    USER_AUTH();

    await axios.get(`${SERVER_URL}/api/store/${storeId}`)
    .then(res => {
        dispatch({
            type: GET_PUGJJIG,
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

// 푹찍 좋아요 {id} 상태 체크
export const pugjjigLikeState = (storeId) =>  async dispatch => {
    
    // 유저 JWT Token정보
    USER_AUTH();
    
    return axios.get(`${SERVER_URL}/api/university/${storeId}/like`)
    .then(res => {
        dispatch({
            type: GET_PUGJJIG_VIEW_LIKE_STATE,
            payload: res.data
        });
    }).catch(error => {
        alert(error.response.data.error);
    });

}