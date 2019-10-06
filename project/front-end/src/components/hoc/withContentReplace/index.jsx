import React, { Fragment } from 'react'

const WithContentReplace = (props) => {
    // props Init
    const {
        content,
        produce
    } = props;

    // Variables Init
    let contentResult = [];

    // Execution function 
    if(content !== undefined) {
        if(produce) {
            // DB값의 \n(줄바꿈) 값을 기준으로 배열을 나눠 저장합니다.
            const contentArr = content.split('\n').map( (line, index) => (
                <Fragment key={index}>{line}<br/></Fragment>
            ));
            
            // 변환된 값을 최종 배열에 넣습니다.
            for(let i = 0; i<contentArr.length; i++) {
                contentResult.push(contentArr[i]);
            }

            return(
                <Fragment>{contentResult}</Fragment>
            );
        }
    } else {
        return(
            <Fragment>내용이 존재하지 않습니다.</Fragment>
        );
    }
}



export default WithContentReplace;