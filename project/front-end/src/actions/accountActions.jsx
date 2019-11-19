import axios from "axios"
import { SERVER_URL, USER_ID } from "../routes"
import { 
    GET_ERRORS,
    ACCOUNT_CREATE,
    ACCOUNT_GET,

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
            await axios.post(`${SERVER_URL}/api/account`, account)
            .then(res => {
                switch(res.status) {
                    case 200 : 
                        dispatch({
                            type: ACCOUNT_CREATE,
                            payload: true
                        });
                        break;
                        
                    case 202 : 
                        dispatch({
                            type: GET_ERRORS,
                            payload: res.data
                        });
                        break;

                    default :
                        console.log(res);
                        break;
                }
            });
        }
    } catch (e) {
        console.log(e);
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
    } catch (e) {
        const error = {
            AuthenticationError: "아이디나 비밀번호를 바르게 입력해주세요."
        };

        // 인증 실패 정보를 전송합니다.
        dispatch({
            type: GET_ERRORS,
            payload: error
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
export const accountLoginCheck = () => dispatch => {
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
 *  유저의 정보를 가져옵니다.
 */
export const accountGet = (history) => async dispatch => {
    try {
        await axios.get(`${SERVER_URL}/api/account/public/${USER_ID()}`)
            .then(res => {
                dispatch({
                    type: ACCOUNT_GET,
                    payload: res.data
                });
            });
    } catch (e) {
        console.log(e);
    }
}

/*
 *  유저의 정보를 수정합니다.
 */
export const accountUpdate = (account, history) => async dispatch => {
    try {
        // 유저가 로그인 상태 라면
        if(localStorage.getItem("userInfo")) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
            await axios.post(`${SERVER_URL}/api/account/${account.id}`, account)
            .then(res => {
                console.log(res);
                switch(res.status) {
                    case 200 : 
                        console.log("200");
                        // dispatch({
                        //     type: ACCOUNT_CREATE,
                        //     payload: true
                        // });
                        break;
                        
                    case 202 : 
                        console.log("202");
                        // dispatch({
                        //     type: GET_ERRORS,
                        //     payload: res.data
                        // });
                        break;

                    default :
                        console.log(res);
                        break;
                }
            });
        }
    } catch (e) {
        console.log(e);
    }
}


/* 
 *  관리자 상태 체크
 */
export const adminAccountCheck = async () => {
    axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
    return await axios.post("http://localhost:8080/api/account/admin")
        .then(function(){
            return Promise.resolve();
        }).catch(function() {
            return Promise.reject({ redirectTo: '/' });
        });    
}

/*
 *  관리자 로그인
 */
export const adminAccountLogin = async (account) => {
    return await axios.post("http://localhost:8080/api/account/login", account)
    .then(res => {
        const token = res.data.token;
        const userId = res.data.userId;
        const username = res.data.username;

        if(token) {
            const userInfo = JSON.stringify({
                token,
                userId,
                username
            });
            localStorage.setItem('userInfo', userInfo);
        } else {
            return Promise.reject("잘못된 상태 입니다.");    
        }
    }, (error) => {
        console.log(error);
        return Promise.reject("정보가 틀립니다.");
    });
}