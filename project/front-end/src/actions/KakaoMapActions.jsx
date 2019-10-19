import axios from "axios"
import { SERVER_URL, USER_AUTH } from "../routes"
import { GET_PUGJJIG_COUNT } from "./types"

/****************************************
    INSERT 푹찍 리뷰
****************************************/
export const pugjjigInsert = (pugjjig) => async dispatch => {
    
    // 유저 JWT Token정보
    USER_AUTH();

    try {
        await axios.post(`${SERVER_URL}/api/university`, pugjjig)
        .then(res => {
            console.log(res);
        });
    } catch (e) {
        console.log(e);
    }
}

/****************************************
    GET 푹찍 리뷰 
****************************************/
export const pugjjigGetCount = (storeId) =>  async dispatch => {
    
    USER_AUTH();
    
    return axios.get(`${SERVER_URL}/api/store/${storeId}/count`)
    .then(res => {
        return res.data;
    });

}