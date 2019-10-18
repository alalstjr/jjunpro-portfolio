import axios from "axios"  


/**********************************************
■ SERVER API URL
**********************************************/
export const SERVER_URL = "http://localhost:8080";

/**********************************************
■ API KEY
**********************************************/
export const API_KEY = "e4886ec63d8dacf6d7f11ab426759a84";

/**********************************************
■ USER Authentication
**********************************************/
export const USER_AUTH = () => {
    return axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;
}