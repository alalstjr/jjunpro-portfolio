import React, { useState, useCallback } from 'react';
import { useDropzone } from 'react-dropzone';

import { DropZoneWrap, DropZone, FileList, FileRemove } from "./style";

const FileDrop = ({ fileState, registerFiles, registerFileState }) => {
    const [myFiles, setMyFiles] = useState([]);
    const maxSize = 10000000;
    const onDrop = useCallback(acceptedFiles => {
        setMyFiles([...myFiles, ...acceptedFiles]);
        fileState(acceptedFiles);        
    }, []);    
    const { isDragActive, getRootProps, getInputProps, isDragReject, rejectedFiles } = useDropzone({
        onDrop,
        // accept: 'image/jpeg, image/png, .pdf',
        minSize: 0,
        maxSize
    });

    const isFileTooLarge = rejectedFiles.length > 0 && rejectedFiles[0].size > maxSize;

    // 등록된 file 제거
    const fileDelete = file => () => {
        const newFiles = [...myFiles];
        newFiles.splice(newFiles.indexOf(file), 1);
        setMyFiles(newFiles);
        fileState(newFiles);
    }

    // 등록된 모든 file 제거
    const removeAll = () => {
        setMyFiles([]);
    }

    return (
        <DropZoneWrap>
            <DropZone {...getRootProps()}>
                <input {...getInputProps()}/>
                {!isDragActive && '여기를 클릭하거나 업로드 파일을 드롭하세요. (PDF, JPG, PNG)'}
                {isDragActive && !isDragReject && "파일 업로드가 가능합니다."}
                {isDragReject && "업로드가 불가능한 파일입니다."}
                {isFileTooLarge && (
                <div className="text-danger mt-2">
                    파일의 크기가 너무 큽니다.
                </div>
                )}
            </DropZone>
            <ul>
                {myFiles.length > 0 && myFiles.map(acceptedFile => (
                    <FileList key={acceptedFile.lastModified}>
                        {acceptedFile.path} - {acceptedFile.size} bytes{" "}
                        <FileRemove 
                            type="button" 
                            onClick={fileDelete(acceptedFile)}
                        />
                    </FileList>
                ))}

                {/* 게시글 수정시 등록된 파일 목록 */}
                {registerFiles.length > 0 && registerFiles.map(acceptedFile => (
                    <FileList key={acceptedFile.fileNo}>
                        {acceptedFile.fileName} - {acceptedFile.fileSize} bytes{" "}
                        <FileRemove 
                            type="button" 
                            onClick={registerFileState.bind(this, acceptedFile)}
                        />
                    </FileList>
                ))}
            </ul>
        </DropZoneWrap>
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