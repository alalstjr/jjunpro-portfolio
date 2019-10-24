import axios from "axios"  


/**********************************************
■ SERVER API URL
**********************************************/
export const SERVER_URL = "http://localhost:8080";

/**********************************************
■ USER Authentication
**********************************************/
export const USER_AUTH = () => {
    return axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
}
export const USER_ID = () => {
    return JSON.parse(localStorage.getItem("userInfo")).userId;
}