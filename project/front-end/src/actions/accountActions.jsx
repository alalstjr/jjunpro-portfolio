import axios from "axios";
import {SERVER_URL, USER_ID} from "../routes";
import {
    MODAL_ACCOUNT,
    GET_ERRORS,
    ACCOUNT_CREATE,
    ACCOUNT_GET,
    CHECK_USER,
    CHECK_USER_SUCCESS,
    CHECK_USER_FAILURE
} from "./types";

/****************************************
 로그인 모달 상태
 String login, String sign_up
 ****************************************/
export const modalAccount = (modal, state) => async dispatch => {
    let login = false;
    let sign_up = false;

    switch (modal) {
        case "login":
            login = state;
            break;
        case "sign_up":
            sign_up = state;
            break;
        default:
            break;
    }

    const action = {
        login,
        sign_up
    };

    dispatch({
        type: MODAL_ACCOUNT,
        payload: action
    });
}

/****************************************
 INSERT Account DATA
 ****************************************/
export const insertAccount = (account) => async dispatch => {

    try {
        // 유저가 로그인 상태가 아니라면
        if (!localStorage.getItem("userInfo")) {
            const res = await axios.post(`${SERVER_URL}/account`, account);

            switch (res.status) {
                case 201 :
                    dispatch({
                        type: ACCOUNT_CREATE,
                        payload: true
                    });

                    // create 상태 init
                    dispatch({
                        type: ACCOUNT_CREATE,
                        payload: false
                    });
                    break;

                case 202 :
                    dispatch({
                        type: GET_ERRORS,
                        payload: res.data
                    });
                    break;

                default :
                    alert("잘못된 접근입니다.");
            }
        }
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data.errors
        });
    }
}

/****************************************
 LOGIN Account DATA
 ****************************************/
export const loginAccount = (account) => async dispatch => {
    try {
        
        const res = await axios.post(`${SERVER_URL}/signin`, account);

        switch (res.status) {
            case 200 :
                const id = res.data.id;
                const token = res.data.token;
                const username = res.data.username;
                const nickname = res.data.nickname;

                if (token) {
                    /*
                    *  사용자가 서버에 서 로그인 인증을 받았을경우 localStorage에 Token을 저장합니다.
                    */
                    const userInfo = JSON.stringify({
                        id,
                        token,
                        username,
                        nickname
                    });
                    localStorage.setItem("userInfo", userInfo);

                    /*
                    *  action type은 CHECK_USER_SUCCESS 으로 보냅니다.
                    *  redux 를 통해서 유저 인증 상태를 프론트에 기록합니다. 
                    */
                    dispatch({
                        type: CHECK_USER_SUCCESS,
                        payload: {
                            id: res.data.id,
                            token: res.data.token,
                            username: res.data.username,
                            nickname: res.data.nickname,
                        }
                    });
                }
                break;

            default :
                alert("잘못된 접근입니다.");
        }
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

function getCookie(c_name) {
	var i,x,y,ARRcookies=document.cookie.split(";");
	for (i=0;i<ARRcookies.length;i++)
	{
	  x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
	  y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
	  x=x.replace(/^\s+|\s+$/g,"");
	  if (x==c_name)
		{
		return unescape(y);
		}
	  }
}

/****************************************
 LOGOUT Account DATA
 ****************************************/
export const logoutAccount = () => dispatch => {
    delete axios.defaults.headers.common['Authorization'];

    if (localStorage.removeItem("userInfo") === undefined) {
        dispatch({
            type: CHECK_USER_FAILURE,
            payload: {}
        });
    }
}

/****************************************
 Account DATA Check
 ****************************************/
export const accountLoginCheck = () => dispatch => {
    const userInfo = JSON.parse(localStorage.getItem("userInfo"));

    if (userInfo !== null) {
        dispatch({
            type: CHECK_USER,
            payload: {
                token: userInfo.token,
                username: userInfo.username,
                username: userInfo.username,
            }
        });
    }
}

/****************************************
 GET Account DATA userId
 ****************************************/
export const getAccountUserId = () => async dispatch => {
    try {
        const res = await axios.get(`${SERVER_URL}/account/${USER_ID()}`)

        switch (res.status) {
            case 200 :
                dispatch({
                    type: ACCOUNT_GET,
                    payload: res.data
                });
                break;

            default :
                alert("잘못된 접근입니다.");
        }
    } catch (error) {
        alert(error.response.data.error);
    }
}

/****************************************
 UPDATE Account DATA
 ****************************************/
export const updateAccount = (account, files, history) => async dispatch => {
    try {
        // 유저가 로그인 상태 라면
        if (localStorage.getItem("userInfo")) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

            // file upload form
            const formData = new FormData();
            const config = {
                headers: {
                    'Accept': 'application/json',
                    'content-type': 'multipart/form-data'
                },
                // File Upload 진행상황
                onUploadProgress: progressEvent => {
                    console.log("업로드 진행률.." + Math.round(progressEvent.loaded / progressEvent.total * 100) + "%");
                }
            };

            // pugjjig form 데이터
            formData.append("id", account.id);
            formData.append("nickname", account.nickname);
            formData.append("email", account.email);
            formData.append("urlList", account.urlList);

            if (files.length > 0) {
                formData.append('file', files[0]);
            }

            const res = await axios.post(`${SERVER_URL}/api/account/${account.id}`, formData, config)

            switch (res.status) {
                case 200 :
                    dispatch({
                        type: ACCOUNT_CREATE,
                        payload: true
                    });

                    // create 상태 init
                    dispatch({
                        type: ACCOUNT_CREATE,
                        payload: false
                    });

                    if (files.length > 0) {
                        window.location.reload();
                    }
                    break;

                case 202 :
                    dispatch({
                        type: GET_ERRORS,
                        payload: res.data
                    });
                    break;

                default :
                    alert("잘못된 접근입니다.");
            }
        }
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data.errors
        });
    }
}

/****************************************
 UPDATE Account Password DATA accountId
 ****************************************/
export const updateAccountPwdId = (account, history) => async dispatch => {
    try {
        // 유저가 로그인 상태 라면
        if (localStorage.getItem("userInfo")) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

            await axios.post(`${SERVER_URL}/api/account/password/${account.id}`, account)
                .then(res => {
                    switch (res.status) {
                        case 200 :
                            dispatch({
                                type: ACCOUNT_CREATE,
                                payload: true
                            });

                            // warningSet success 경고문 초기화해주기 위해서 false
                            dispatch({
                                type: ACCOUNT_CREATE,
                                payload: false
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
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data.errors
        });
    }
}

/* 
 *  관리자 상태 체크
 */
export const adminAccountCheck = async () => {
    axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
    return await axios.post("http://localhost:8080/api/account/admin")
        .then(function () {
            return Promise.resolve();
        }).catch(function () {
            return Promise.reject({redirectTo: '/'});
        });
}

/*
 *  관리자 로그인
 */
export const adminAccountLogin = async (account) => {
    return await axios.post("http://localhost:8080/api/account/login", account)
        .then(res => {
            // const token = res.data.token;
            // const username = res.data.username;
            // const username = res.data.username;

            // if (token) {
            //     const userInfo = JSON.stringify({
            //         token,
            //         username,
            //         username
            //     });
            //     localStorage.setItem('userInfo', userInfo);
            // } else {
            //     return Promise.reject("잘못된 상태 입니다.");
            // }
        }, (error) => {
            console.log(error);
            return Promise.reject("정보가 틀립니다.");
        });
}