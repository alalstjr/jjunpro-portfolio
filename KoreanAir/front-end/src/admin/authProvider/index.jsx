import { AUTH_LOGIN, AUTH_LOGOUT, AUTH_ERROR, AUTH_CHECK } from 'react-admin';

import { accountLogin } from "../../actions/accountActions";

export default (type, params) => {
    // called when the user attempts to log in
    // 사용자가 로그인을 시도 할 때 호출
    if (type === AUTH_LOGIN) {
        const { accountContext } = params;
        
        // accept all username/password combinations
        // 모든 사용자 아이디 / 암호 서버로 인증 전송

        accountLogin(accountContext);

        return Promise.resolve();
    }
    // called when the user clicks on the logout button
    // 사용자가 로그 아웃 버튼을 클릭하면 호출
    if (type === AUTH_LOGOUT) {
        localStorage.removeItem('username');
        return Promise.resolve();
    }
    // called when the API returns an error
    // API가 오류를 반환하면 호출
    if (type === AUTH_ERROR) {
        const { status } = params;
        if (status === 401 || status === 403) {
            localStorage.removeItem('username');
            return Promise.reject();
        }
        return Promise.resolve();
    }
    // called when the user navigates to a new location
    // 로그인한 사용자가 관리자인지 확인합니다.
    if (type === AUTH_CHECK) {
        return localStorage.getItem('username') ? Promise.resolve() : Promise.reject();
    }
    return Promise.reject('Unknown method');
};