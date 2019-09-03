import axios from "axios"

import { AUTH_LOGIN, AUTH_LOGOUT, AUTH_ERROR, AUTH_CHECK } from 'react-admin';

import { adminAccountLogin, adminAccountCheck } from '../../actions/accountActions';

export default async (type, params) => {
    switch(type) {
        case AUTH_LOGIN: 
            // called when the user attempts to log in
            // 사용자가 로그인을 시도 할 때 호출
            // accept all username/password combinations
            // 모든 사용자 아이디 / 암호 서버로 인증 전송
            // const { username, password } = params;

            const param = {
                userId : params.username,
                password : params.password
            };

            return adminAccountLogin(param);
        break
        
        case AUTH_LOGOUT:
            // called when the user clicks on the logout button
            // 사용자가 로그 아웃 버튼을 클릭하면 호출
            localStorage.removeItem('userInfo');
            return Promise.resolve();
        break

        case AUTH_ERROR:
            // called when the API returns an error
            // API가 오류를 반환하면 호출
            const { status } = params;
            if (status === 401 || status === 403) {
                localStorage.removeItem('userInfo');
                return Promise.reject("");
            }
            return Promise.resolve();
        break

        case AUTH_CHECK:
            // called when the user navigates to a new location
            // 로그인한 사용자가 관리자인지 확인합니다.
            // return localStorage.getItem('userInfo') ? Promise.resolve() : Promise.reject("관리자가 아닙니다.");
            return adminAccountCheck();
            // return adminAccountCheck() ? Promise.resolve() : Promise.reject()

        break
    }
    return Promise.reject('Unknown method')
}