import axios from "axios"
import { 
    GET_ERRORS,
    CHECK_USER,
    CHECK_USER_SUCCESS,
    CHECK_USER_FAILURE
} from "./types"

/*
 *  새로운 유저를 등록합니다.
 */
export const accountInsert = (account, history) => async dispatch => {
    try {
        // 유저가 로그인 상태가 아니라면
        if(!localStorage.getItem("userInfo")) {
            await axios.post("http://localhost:8080/api/account", account)
            .then(res => {
                console.log(res);
            });
        }
    } catch (errors) {
        // 회원가입 실패 정보를 전송합니다.
        const error = {
            AuthenticationError: "이미 존재하는 아이디 입니다."
        };

        dispatch({
            type: GET_ERRORS,
            payload: error
        });

        // 프론트단에서 쉽게 에러 메세지를 관리하기 위해서 초기화해줍니다.
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    }
}

/*
 *  로그인
 */
export const accountLogin = (account, history) => async dispatch => {
    try {
        await axios.post("http://localhost:8080/api/account/login", account)
        .then(res => {
            const token = res.data.token;
            const userId = res.data.userId;
            const username = res.data.username;

            if(token) {
                /*
                 *  사용자가 서버에 서 로그인 인증을 받았을경우 localStorage에 Token을 저장합니다.
                 */
                const userInfo = JSON.stringify({
                    token,
                    userId,
                    username
                });
                localStorage.setItem("userInfo", userInfo);

                /*
                 *  action type은 CHECK_USER_SUCCESS 으로 보냅니다.
                 *  redux 를 통해서 유저 인증 상태를 프론트에 기록합니다. 
                 */
                dispatch({
                    type: CHECK_USER_SUCCESS,
                    payload: {
                        token: res.data.token,
                        userId: res.data.userId,
                        username: res.data.username,
                    }
                });
            }
        });
    } catch (errors) {
        const error = {
            AuthenticationError: "정보에 맞는 계정이 없습니다."
        };

        // 인증 실패 정보를 전송합니다.
        dispatch({
            type: GET_ERRORS,
            payload: error
        });

        // 프론트단에서 쉽게 에러 메세지를 관리하기 위해서 초기화해줍니다.
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    }
}

/*
 *  로그아웃
 */
export const accountLogout = () => dispatch => {
    
    if(localStorage.removeItem("userInfo") === undefined) {
        dispatch({
            type: CHECK_USER_FAILURE,
            payload: {}
        });
    }
}

/*
 *  로그인 상태 체크
 */
export const userLoginCheck = () => dispatch => {
    const userInfo = JSON.parse(localStorage.getItem("userInfo"));
    
    if(userInfo !== null) {
        dispatch({
            type: CHECK_USER,
            payload: {
                token: userInfo.token,
                userId: userInfo.userId,
                username: userInfo.username,
            }
        });
    }
}

/*
 *  ex) 회원만 요청 가능
 */
// export const memberTaskLogin = (account, history) => async dispatch => {
//     try {
//     axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
//         await axios.get("http://localhost:8080/api/account/go",{ 
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