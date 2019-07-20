import axios from "axios";
import { 
    GET_ERRORS
} from "./types";

/*
 *  새로운 유저를 등록합니다.
 */
export const memberTaskInsert = (account, history) => async dispatch => {
    try {
        console.log(account);
        await axios.post("http://localhost:8080/api/member/", account)
        .then(res => {
            console.log(res);
        });
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

/*
 *  로그인
 */
export const memberTaskLogin = (account, history) => async dispatch => {
    try {
        await axios.post("http://localhost:8080/api/member/login", account)
        .then(res => {
            const token = res.data.token;
            console.log(token);
        });
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

/*
 *  ex) 회원만 요청 가능
 */
// export const memberTaskLogin = (account, history) => async dispatch => {
//     try {
//         await axios.get("http://localhost:8080/api/member/go",{ 
//             headers: {
//                 Authorization : `Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJqanVucHJvIiwiVVNFUk5BTUUiOiJhc2QiLCJVU0VSX1JPTEUiOiJST0xFX1VTRVIifQ.rGUlmMWPCEHMgY8YEakH7aMWSGUuCYnrxwQdRsMs1CY`
//             }
//         })
//         .then(res => {
//             console.log(res);
//         });
//     } catch (error) {
//         dispatch({
//             type: GET_ERRORS,
//             payload: error.response.data
//         });
//     }
// }