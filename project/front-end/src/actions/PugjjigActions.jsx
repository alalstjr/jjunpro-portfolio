import axios from "axios"
import {SERVER_URL, USER_AUTH, USER_ID, AUTH_UPDATE} from "../routes"
import {
    GET_UNIVERSITY_COUNT,
    TEMP_UNIVERSITY_LIST,
    DELETE_PUGJJIG,
    DELETE_PUGJJIG_COMMENT,
    GET_UNIVERSITY,
    GET_PUGJJIG_LIST,
    GET_PUGJJIG_COMMENT,
    GET_PUGJJIG_COMMENT_LIST,
    GET_PUGJJIG_LIKE,
    GET_PUGJJIG_VIEW,
    GET_FILE_PROGRESS,
    GET_ERRORS
} from "./types"

/****************************************
 현재 검색중인 대학교 저장
 ****************************************/
export const getUniversity = (uniName) => dispatch => {
    dispatch({
        type: GET_UNIVERSITY,
        payload: uniName
    });
}

/****************************************
 현재 불러온 DATA를 담은 List
 ****************************************/
export const tempUniversityList = (reset, temp_pugjjig, postData) => dispatch => {
    dispatch({
        type: TEMP_UNIVERSITY_LIST,
        payload: reset ? [] : temp_pugjjig.concat(postData)
    });
}
export const tempUniversityListUpdate = (postData) => dispatch => {
    dispatch({
        type: TEMP_UNIVERSITY_LIST,
        payload: postData
    });
}


/****************************************
 INSERT University DATA
 ****************************************/
export const insertUniversity = (pugjjig, files, history) => async dispatch => {
    try {
        // file upload form
        const formData = new FormData();
        const config = {
            headers: {
                'Accept': 'application/json',
                'content-type': 'multipart/form-data'
            },
            // File Upload 진행상황
            onUploadProgress: progressEvent => {
                dispatch({
                    type: GET_FILE_PROGRESS,
                    payload: "업로드 진행률.." + Math.round(progressEvent.loaded / progressEvent.total * 100) + "%"
                });
            }
        };

        // pugjjig form 데이터
        formData.append("uniSubject", pugjjig.uniSubject);
        formData.append("uniAtmosphere", pugjjig.uniAtmosphere);
        formData.append("uniPrice", pugjjig.uniPrice);
        formData.append("uniContent", pugjjig.uniContent);
        formData.append("uniName", pugjjig.uniName);
        formData.append("uniTag", pugjjig.uniTag);
        formData.append("uniStar", pugjjig.uniStar);
        formData.append("stoId", pugjjig.stoId);
        formData.append("stoName", pugjjig.stoName);
        formData.append("stoAddress", pugjjig.stoAddress);
        formData.append("stoUrl", pugjjig.stoUrl);

        // pugjjig update 일경우 id값 전달
        if (pugjjig.uniId !== null) {
            formData.append("id", pugjjig.uniId);
            formData.append("removeFiles", pugjjig.removeFiles);
        }

        files.forEach(function (file) {
            formData.append('files', file);
        });

        // 유저 JWT Token정보
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`

        const res = await axios.post(`${SERVER_URL}/api/university`, formData, config);

        switch (res.status) {
            case 201 :
                history.push(`/pugjjig/${res.data.id}`);
                dispatch({
                    type: GET_PUGJJIG_VIEW,
                    payload: res.data
                });
                break;
            case 202 :
                dispatch({
                    type: GET_ERRORS,
                    payload: res.data.AuthenticationError
                });
                break;

            default :
                alert("잘못된 접근입니다.");
        }

        dispatch({
            type: GET_FILE_PROGRESS,
            payload: ""
        });

    } catch (error) {
        alert(error.response.data.error);
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

/****************************************
 DELETE University DATA uniId
 ****************************************/
export const deleteUniversityuniId = (id) => async dispatch => {
    try {
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

        const res = await axios.delete(`${SERVER_URL}/api/university/${id}`);

        switch (res.status) {
            case 200 :
                dispatch({
                    type: DELETE_PUGJJIG,
                    payload: id
                });
                break;
            case 202 :
                alert(res.data.AuthenticationError);
                break;

            default :
                alert("잘못된 접근입니다.");
        }

    } catch (error) {
        alert(error.response.data.error);
    }
}

/****************************************
 GET University List DATA Search
 ****************************************/
export const getUniListSearch = (searchDTO) => async dispatch => {
    try {
        searchDTO.keyword = (searchDTO.keyword === "") ? "all" : searchDTO.keyword;
        const classification = `classification=${searchDTO.classification}`;
        const offset = `offsetCount=${searchDTO.offsetCount}`;
        const cate = `ifCateA=${searchDTO.ifCateA}&ifCateB=${searchDTO.ifCateB}`;
        const params = `${classification}&${offset}&${cate}`;
        const res = await axios.get(`${SERVER_URL}/api/university/search/${searchDTO.keyword}?${params}`);

        // searchDTO.keyword = (searchDTO.keyword === "") ? "all" : searchDTO.keyword;
        // const category = `category=like`;
        // const offset = `offsetCount=${searchDTO.offsetCount}`;
        // const cate = `ifCateA=${searchDTO.ifCateA}&ifCateB=${searchDTO.ifCateB}`;
        // const params = `${category}&${searchDTO.keyword}&${offset}&${cate}`;
        // const res = await axios.get(`${SERVER_URL}/university/search?${params}`);

        switch (res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_LIST,
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
 GET University List DATA StoreId
 ****************************************/
export const getUniListStoreId = (searchDTO) => async dispatch => {
    try {
        // 유저 JWT Token정보
        USER_AUTH();

        searchDTO.keyword = (searchDTO.keyword === "") ? "all" : searchDTO.keyword;
        const classification = `classification=${searchDTO.classification}`;
        const offset = `offsetCount=${searchDTO.offsetCount}`;
        const cate = `ifCateA=${searchDTO.ifCateA}&ifCateB=${searchDTO.ifCateB}`;
        const params = `${classification}&${offset}&${cate}`;
        const res = await axios.get(`${SERVER_URL}/api/store/${searchDTO.keyword}?${params}`);

        switch (res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_LIST,
                    payload: res.data
                });
                break;

            default :
                alert("잘못된 접근입니다.");
        }
    } catch (error) {
        alert(error.response.data.error);
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

/****************************************
 GET University List DATA UserId
 ****************************************/
export const getUniListUserId = (searchDTO) => async dispatch => {
    try {
        // 유저 {아이디값,페이지} 의 전달이 없는 경우 기본값 설정
        searchDTO.keyword = (searchDTO.keyword === undefined || searchDTO.keyword === null) ? USER_ID() : searchDTO.keyword;

        searchDTO.keyword = (searchDTO.keyword === "") ? "all" : searchDTO.keyword;
        const offset = `offsetCount=${searchDTO.offsetCount}`;
        const cate = `ifCateA=${searchDTO.ifCateA}&ifCateB=${searchDTO.ifCateB}`;
        const params = `${offset}&${cate}`;
        const res = await axios.get(`${SERVER_URL}/api/university/pugjjigs/userId/${searchDTO.keyword}?${params}`);

        switch (res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_LIST,
                    payload: res.data
                });
                break;

            default :
                alert("잘못된 접근입니다.");
        }
    } catch (error) {
        alert(error.response.data.error);
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

/****************************************
 GET University List DATA nickname
 ****************************************/
export const getUniListNickname = (searchDTO) => async dispatch => {
    try {
        // 유저의 닉네임이 존재하는지 체크합니다.
        if (searchDTO.keyword === undefined || searchDTO.keyword === null) {
            dispatch({
                type: GET_ERRORS,
                payload: "유저 닉네임이 존재하지 않습니다. 새로고침을 시도해 주세요."
            });
            return false;
        }

        searchDTO.keyword = (searchDTO.keyword === "") ? "all" : searchDTO.keyword;
        const offset = `offsetCount=${searchDTO.offsetCount}`;
        const cate = `ifCateA=${searchDTO.ifCateA}&ifCateB=${searchDTO.ifCateB}`;
        const params = `${offset}&${cate}`;
        const res = await axios.get(`${SERVER_URL}/api/university/pugjjigs/nickname/${searchDTO.keyword}?${params}`);

        switch (res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_LIST,
                    payload: res.data
                });
                break;

            default :
                alert("잘못된 접근입니다.");
        }
    } catch (error) {
        alert(error.response.data.error);
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

/****************************************
 GET University List DATA UserId
 ****************************************/
export const getUniListUniLike = (searchDTO) => async dispatch => {
    try {
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

        // 유저 {아이디값,페이지} 의 전달이 없는 경우 기본값 설정
        searchDTO.keyword = (searchDTO.keyword === undefined || searchDTO.keyword === null) ? USER_ID() : searchDTO.keyword;

        searchDTO.keyword = (searchDTO.keyword === "") ? "all" : searchDTO.keyword;
        const category = `category=like`;
        const offset = `offsetCount=${searchDTO.offsetCount}`;
        const cate = `ifCateA=${searchDTO.ifCateA}&ifCateB=${searchDTO.ifCateB}`;
        const params = `${category}&${searchDTO.keyword}&${offset}&${cate}`;
        const res = await axios.get(`${SERVER_URL}/university/search?${params}`);

        switch (res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_LIST,
                    payload: res.data
                });
                break;

            default :
                alert("잘못된 접근입니다.");
        }
    } catch (error) {
        alert(error.response.data.error);
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

/****************************************
 GET University Count DATA StoreId
 ****************************************/
export const getUniCountStoId = (keyword) => async dispatch => {
    // try {
    //     // 유저 JWT Token정보
    //     USER_AUTH();

    //     const res = await axios.get(`${SERVER_URL}/api/store/count/${keyword}`)

    //     switch (res.status) {
    //         case 200 :
    //             return res.data;

    //         default :
    //             alert("잘못된 접근입니다.");
    //     }
    // } catch (error) {
    //     alert(error.response.data.error);
    // }
}

/****************************************
 GET University Count DATA UniId
 ****************************************/
export const getUniCountUniId = (uniName) => async dispatch => {
    try {
        const res = await axios.get(`${SERVER_URL}/university/count/${uniName}`)

        switch (res.status) {
            case 200 :
                dispatch({
                    type: GET_UNIVERSITY_COUNT,
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
 GET University DATA uniId
 ****************************************/
export const getUniversityUniId = (id, history) => async dispatch => {
    try {
        // 유저 JWT Token정보
        USER_AUTH();

        const res = await axios.get(`${SERVER_URL}/api/university/${id}`);

        switch (res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_VIEW,
                    payload: res.data
                });
                break;

            default :
                alert("잘못된 접근입니다.");
        }
    } catch (error) {
        alert(error.response.data.error);
        history.push("/");
    }
}

/****************************************
 GET University List DATA Most Like DESC
 ****************************************/
export const getUniversityMostLike = () => async dispatch => {
    try {
        // 유저 JWT Token정보
        USER_AUTH();

        const res = await axios.get(`${SERVER_URL}/api/university/best`);

        switch (res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_LIST,
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
 GET University List DATA CreatedDate DESC
 ****************************************/
export const getUniversityCreatedDate = () => async dispatch => {
    try {
        // 유저 JWT Token정보
        USER_AUTH();

        const res = await axios.get(`${SERVER_URL}/api/university`);

        switch (res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_LIST,
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
 UPDATE UniLike DATA uniId
 ****************************************/
export const UpdateUniLikeUniId = (id, history) => async dispatch => {
    try {
        // 유저 JWT Token정보
        USER_AUTH();

        const res = await axios.post(`${SERVER_URL}/api/university/like/${id}`);

        switch (res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_LIKE,
                    payload: res.data
                });
                break;

            default :
                alert("잘못된 접근입니다.");
        }
    } catch (error) {
        alert("회원만 이용 가능합니다.");
        dispatch({
            type: GET_ERRORS,
            payload: "회원만 이용 가능합니다."
        });
    }
}

/****************************************
 INSERT Comment DATA
 ****************************************/
export const insertComment = (comment) => async dispatch => {
    try {
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

        const res = await axios.post(`${SERVER_URL}/api/comment`, comment);

        switch (res.status) {
            case 201 :
                dispatch({
                    type: GET_PUGJJIG_COMMENT,
                    payload: res.data
                });
                break;

            default :
                alert("잘못된 접근입니다.");
        }
    } catch (error) {
        alert("잘못된 접근입니다.\n다시한번 시도해 주세요.");
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

/****************************************
 DELETE Comment DATA id
 ****************************************/
export const deleteCommentId = (id) => async dispatch => {
    try {
        axios.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userInfo")).token}`;

        const res = await axios.delete(`${SERVER_URL}/api/comment/${id}`);

        switch (res.status) {
            case 200 :
                dispatch({
                    type: DELETE_PUGJJIG_COMMENT,
                    payload: id
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
 GET Comment List DATA UniId
 ****************************************/
export const getCommentListUniId = (id) => async dispatch => {
    try {
        const res = await axios.get(`${SERVER_URL}/api/comment/${id}`);

        switch (res.status) {
            case 200 :
                dispatch({
                    type: GET_PUGJJIG_COMMENT_LIST,
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