import axios from "axios"
import { SERVER_URL } from "../routes"
import { 
    GET_UNIVERSITY,
    DELETE_PUGJJIG
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
    Pugjjig Delete 삭제
****************************************/
// 푹찍 리뷰 좋아요 {userId} 목록 조회
export const pugjjigDelete = (id) =>  async dispatch => {
    try {
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
        
        await axios.delete(`${SERVER_URL}/api/university/${id}`)
        dispatch({
            type: DELETE_PUGJJIG,
            payload: id
        });
    } catch (error) {
        alert(error.response.data.error);
    }
}