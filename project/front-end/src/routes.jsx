import axios from "axios"  


/**********************************************
■ SERVER API URL
**********************************************/
export const SERVER_URL = "http://localhost:8080";

/**********************************************
■ USER Authentication
**********************************************/
export const USER_AUTH = () => {
    if(JSON.parse(localStorage.getItem("userInfo")) !== null) {
        return axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
    } else {
        return false;
    }
}
export const USER_ID = () => {
    if(localStorage.getItem("userInfo")) {
        return JSON.parse(localStorage.getItem("userInfo")).userId;
    }
}
export const USER_LONG_ID = () => {
    if(localStorage.getItem("userInfo")) {
        return JSON.parse(localStorage.getItem("userInfo")).id;
    }
}