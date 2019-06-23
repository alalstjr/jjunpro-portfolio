import React, { Component } from 'react'
import { FilePond } from 'react-filepond';
import 'filepond/dist/filepond.min.css';

const FilePondBox = () => {
    return (
        <div>
            <FilePond
                allowMultiple={true}
                server="http://localhost:8080/uploadMultipleFiles"
                name="files"
            />
        </div>
    )
}

export default FilePondBox;