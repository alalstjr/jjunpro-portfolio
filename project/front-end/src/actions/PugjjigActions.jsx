import { 
    GET_UNIVERSITY
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