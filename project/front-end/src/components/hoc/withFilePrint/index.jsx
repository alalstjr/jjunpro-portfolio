import React, { Fragment } from 'react';

import ImageItem from "./item";

const WithFilePrint = (props) => {
    // props Init
    const {
        imgFileBundle
    } = props;

    // Variables Init
    let items = [];

    // Execution function 
    const files = imgFileBundle.map(file => (
        <ImageItem
            key={file.fileNo}
            file={file}
        />
    ));

    for(let i = 0; i < files.length; i++) {
        items.push(files[i]);
    }

    return(
        <Fragment>{items}</Fragment>
    );
}
export default WithFilePrint;