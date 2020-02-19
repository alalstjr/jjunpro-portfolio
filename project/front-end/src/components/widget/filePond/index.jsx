import React, {Component} from 'react'
import {FilePond, registerPlugin} from 'react-filepond';
import 'filepond/dist/filepond.min.css';

import FilePondPluginImageExifOrientation from 'filepond-plugin-image-exif-orientation';
import FilePondPluginImagePreview from 'filepond-plugin-image-preview';
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css';

registerPlugin(FilePondPluginImageExifOrientation, FilePondPluginImagePreview);

const FilePondBox = ({fileState}) => {
    return (
        <div>
            <FilePond
                allowMultiple={true}
                name="files"
                onupdatefiles={
                    fileItems => {
                        fileState(fileItems);
                    }
                }
                server={
                    {
                        url: 'http://localhost:8080/uploadMultipleFiles',
                        process: {
                            onload: (response) => fileState(JSON.parse(response))
                        }
                    }
                }
            />
        </div>
    )
}

export default FilePondBox;