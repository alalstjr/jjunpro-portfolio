import axios from "axios"
import { SERVER_URL } from "../routes"

/****************************************
    INSERT 푹찍 리뷰
****************************************/
export const pugjjigInsert = (pugjjig) => async dispatch => {
    
    // 유저 JWT Token정보
    axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

    try {
        await axios.post(`${SERVER_URL}/api/university`, pugjjig)
        .then(res => {
            console.log(res);
        });
    } catch (e) {
        console.log(e);
    }
}