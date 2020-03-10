import axios from "axios"


/**********************************************
 ■ SERVER API URL
 **********************************************/
// export const SERVER_URL = "http://localhost:8080";
export const SERVER_URL = "http://34.82.92.128:8080";
export const SERVER_FILE_URL = "https://storage.googleapis.com/pugjjig-storage/"

/**********************************************
 ■ Version
 **********************************************/
export const FVersion = "Fv.1.0.2";

/**********************************************
 ■ USER Authentication
 **********************************************/
export const USER_AUTH = () => {
    if (JSON.parse(localStorage.getItem("userInfo")) !== null) {
        return axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
    } else {
        return false;
    }
}
export const AUTH_UPDATE = () => {
    return axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
}
export const USER_ID = () => {
    if (localStorage.getItem("userInfo")) {
        return JSON.parse(localStorage.getItem("userInfo")).username;
    }
}
export const USER_LONG_ID = () => {
    if (localStorage.getItem("userInfo")) {
        return JSON.parse(localStorage.getItem("userInfo")).id;
    }
}