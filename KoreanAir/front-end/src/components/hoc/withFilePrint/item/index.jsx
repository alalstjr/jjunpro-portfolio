import React, { Component, Fragment } from 'react'
import { Image } from "../../../../style/globalStyles";

class ImageItem extends Component {
    render() {
        // props Init
        const { file } = this.props;

        // file URL
        // Server Url
        const serverUrl = "http://localhost:8080/downloadFile/";

        // 확장자 조정
        let fileType =  file.fileType.split("/", 2 );
        fileType = (fileType[1] === "jpeg") ? "jpg" : fileType[1];

        // URL Result
        const imgUrl = serverUrl + file.id + "." + fileType;

        return (
            <Fragment>
                <Image src={imgUrl}/>
            </Fragment>
        )
    }
}

export default ImageItem;