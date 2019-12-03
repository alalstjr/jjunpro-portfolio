import axios from "axios"
import { SERVER_URL, USER_AUTH, USER_ID } from "../routes"
import { 
    GET_UNIVERSITY,
    DELETE_PUGJJIG,
    GET_PUGJJIG_LIST,
    GET_PUGJJIG_COMMENT,
    GET_PUGJJIG_COMMENT_LIST,
    DELETE_PUGJJIG_COMMENT,
    GET_ERRORS
} from "./types" 

/****************************************
    현재 검색중인 대학교 저장
****************************************/
export const getUniversity = (uniName) => dispatch => {
    
    dispatch({
        type: GET_UNIVERSITY,
        payload: uniName
    });
}

/****************************************
    Pugjjig Comment Insert 저장하기
****************************************/
export const pugjjigCommentInsert = (comment) =>  async dispatch => {
    try {
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

        const res = await axios.post(`${SERVER_URL}/api/comment`, comment);

        switch(res.status) {
            case 201 :
                dispatch({
                    type: GET_PUGJJIG_COMMENT,
                    payload: res.data
                });
                break;
        }
    } catch(error) {
        alert(error.response.data.error);
    }
}

/****************************************
    Pugjjig Comment Delete 저장하기
****************************************/
export const pugjjigCommentDelete = (id) =>  async dispatch => {
    try {
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

        const res = await axios.delete(`${SERVER_URL}/api/comment/${id}`);

        switch(res.status) {
            case 200 :
                dispatch({
                    type: DELETE_PUGJJIG_COMMENT,
                    payload: id
                });
                break;
        }
    } catch(error) {
        alert(error.response.data.error);
    }
}

/****************************************
    Pugjjig Delete 삭제
****************************************/
export const pugjjigDelete = (id) =>  async dispatch => {
    try {
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
        
        const res = await axios.delete(`${SERVER_URL}/api/university/${id}`);

        switch(res.status) {
            case 200 :
                dispatch({
                    type: DELETE_PUGJJIG,
                    payload: id
                });
                break;
            case 202 :
                alert(res.data.AuthenticationError);
                break;

            default :
                alert("잘못된 접근입니다.");
        }
        
    } catch (error) {
        alert(error.response.data.error);
    }
}

/****************************************
    Pugjjig Comment List 가져오기
****************************************/
export const pugjjigCommentGetList = (id) =>  async dispatch => {
    try {
        const res = await axios.get(`${SERVER_URL}/api/comment/${id}`);

        switch(res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_COMMENT_LIST,
                    payload: res.data
                });
                break;
        }
    } catch(error) {
        alert(error.response.data.error);
    }
}

/****************************************
    GET University List DATA Search
****************************************/
export const getUniListSearch = (searchDTO) =>  async dispatch => {
    try {
        const params = `classification=${searchDTO.classification}&offsetCount=${searchDTO.offsetCount}`;
        const res = await axios.get(`${SERVER_URL}/api/university/search/${searchDTO.keyword}?${params}`);

        switch(res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_LIST,
                    payload: res.data
                });
                break;
        }
    } catch(error) {
        alert(error.response.data.error);
    }
}

/****************************************
    GET University List DATA StoreId
****************************************/
export const getUniListStoreId = (searchDTO) => async dispatch => {
    try {
        // 유저 JWT Token정보
        USER_AUTH();

        const params = `offsetCount=${searchDTO.offsetCount}`;
        const res = await axios.get(`${SERVER_URL}/api/store/${searchDTO.keyword}?${params}`);
        
        switch(res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_LIST,
                    payload: res.data
                });
                break;
        }
    } catch(error) {
        alert(error.response.data.error);
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

/****************************************
    GET University List DATA UserId
****************************************/
export const getUniListUserId = (searchDTO) =>  async dispatch => {
    try {
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
        
        // 유저 {아이디값,페이지} 의 전달이 없는 경우 기본값 설정
        searchDTO.keyword = (searchDTO.keyword === undefined || searchDTO.keyword === null) ? USER_ID() : searchDTO.keyword;

        const params = `offsetCount=${searchDTO.offsetCount}`;
        const res = await axios.get(`${SERVER_URL}/api/university/pugjjigs/${searchDTO.keyword}?${params}`);

        switch(res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_LIST,
                    payload: res.data
                });
                break;
        }
    } catch(error) {
        alert(error.response.data.error);
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

/****************************************
    GET University List DATA UserId
****************************************/
export const getUniListUniLike = (searchDTO) => async dispatch => {
    try {
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

        // 유저 {아이디값,페이지} 의 전달이 없는 경우 기본값 설정
        searchDTO.keyword = (searchDTO.keyword === undefined || searchDTO.keyword === null) ? USER_ID() : searchDTO.keyword;

        const params = `offsetCount=${searchDTO.offsetCount}`;
        const res = await axios.get(`${SERVER_URL}/api/university/pugjjigLikes/${searchDTO.keyword}?${params}`);

        switch(res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_LIST,
                    payload: res.data
                });
                break;
        }
    } catch(error) {
        alert(error.response.data.error);
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}