import React, { useCallback } from 'react';
import { useDropzone } from 'react-dropzone';

const FileDrop = ({ fileState }) => {
    const maxSize = 99999999999;

    const onDrop = useCallback(acceptedFiles => {
        fileState(acceptedFiles);
    }, []);    

    const { isDragActive, getRootProps, getInputProps, isDragReject, acceptedFiles, rejectedFiles } = useDropzone({
        onDrop,
        accept: 'image/png',
        minSize: 0,
        maxSize
    });

    const isFileTooLarge = rejectedFiles.length > 0 && rejectedFiles[0].size > maxSize;

    return (
        <div>
            <div {...getRootProps()}>
                <input {...getInputProps()}/>
                {!isDragActive && 'Click here or drop a file to upload!'}
                {isDragActive && !isDragReject && "Drop it like it's hot!"}
                {isDragReject && "File type not accepted, sorry!"}
                {isFileTooLarge && (
                <div className="text-danger mt-2">
                    File is too large.
                </div>
                )}
            </div>
            <ul className="list-group mt-2">
                {acceptedFiles.length > 0 && acceptedFiles.map(acceptedFile => (
                    <li key={acceptedFile.lastModified} className="list-group-item list-group-item-success">
                    {acceptedFile.name}
                    </li>
                ))}
            </ul>
        </div>
    )
}

export default FileDrop;

/*  File State Function
    // File Setup
    fileState = (target) => {
        this.setState({
            files: target[0]
        });
    }
 */