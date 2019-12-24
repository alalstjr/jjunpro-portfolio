import React, { useState, useCallback } from 'react';
import { useDropzone } from 'react-dropzone';

import { DropZoneWrap, DropZone, FileList, FileRemove } from "./style";

const FileDrop = ({ fileState, registerFileState, registerFiles, multiple }) => {
    const [myFiles, setMyFiles] = useState([]);
    const maxSize = 20000000;
    const onDrop = useCallback(acceptedFiles => {
        setMyFiles([...myFiles, ...acceptedFiles]);
        fileState(acceptedFiles);        
    }, []);    
    const { isDragActive, getRootProps, getInputProps, isDragReject, rejectedFiles } = useDropzone({
        onDrop,
        accept: 'image/jpeg, image/png',
        minSize: 0,
        maxSize,
        maxFiles: 5,
        multiple
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
                {!isDragActive && '여기를 클릭하거나 업로드 파일을 드롭하세요. (JPG, PNG)'}
                {isDragActive && !isDragReject && "파일 업로드가 가능합니다."}
                {isDragReject && "업로드가 불가능한 파일입니다."}
                {isFileTooLarge && (
                <div className="text-danger mt-2">
                    파일의 크기가 너무 큽니다.
                </div>
                )}
            </DropZone>
            <ul>
                {myFiles.length > 0 && myFiles.map((acceptedFile, index) => (
                    <FileList key={index}>
                        {acceptedFile.path} - {acceptedFile.size} bytes{" "}
                        <FileRemove 
                            type="button" 
                            onClick={fileDelete(acceptedFile)}
                        />
                    </FileList>
                ))}

                {/* 게시글 수정시 등록된 파일 목록 */}
                {registerFiles.length > 0 && registerFiles.map((acceptedFile, index) => (
                    <FileList key={index}>
                        {acceptedFile.filename} - {acceptedFile.fileSize} bytes{" "}
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

/*
 *  위젯 사용시 필수 메소드, state 설정 
 */

 /*
constructor(props){
    super(props);

    this.state = {
        files: [],
        registerFiles: [],
        fileCount: 0
    }
}

// File Setup
// 파일의 상태정보를 저장하는 메소드입니다.
fileState = (target) => {
    this.setState({
        files: target.map(fileItem => fileItem),
        fileCount: target.length
    });
}

// registerFiles Setup
// 파일 삭제 관리를 해주는 메소드입니다.
registerFileState = (file) => {
    const { registerFiles, removeFiles } = this.state;
    registerFiles.splice(registerFiles.indexOf(file), 1);

    removeFiles.push(file.id)

    this.setState({
        registerFiles,
        removeFiles
    });
}

// 위 두개의 메소드와 파일이 저장된 state를 매개변수로 전달합니다.
component return (
    <FileDrop
        fileState={this.fileState}
        registerFileState={this.registerFileState}
        registerFiles={registerFiles}
    />
);
*/