package com.jjunpro.project.common;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Component
public class CloudStorageHelper {

    private static Storage     storage = null;
    private static Credentials credentials;

    // [START init]
    static {
        try {
            credentials = GoogleCredentials.fromStream(new FileInputStream("./src/main/java/com/jjunpro/project/common/spring-project-869b85004058.json"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        storage = StorageOptions
                .newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();
    }
    // [END init]

    // [START uploadFile]

    /**
     * Uploads a file to Google Cloud Storage to the bucket specified in the BUCKET_NAME
     * environment variable, appending a timestamp to end of the uploaded filename.
     */
    // Note: this sample assumes small files are uploaded. For large files or streams use:
    // Storage.writer(BlobInfo blobInfo, Storage.BlobWriteOption... options)
    public String uploadFile(
            InputStream file,
            String fileName,
            final String bucketName
    ) throws IOException {
        // The InputStream is closed by default, so we don't need to close it here
        // Read InputStream into a ByteArrayOutputStream.
        ByteArrayOutputStream os      = new ByteArrayOutputStream();
        byte[]                readBuf = new byte[4096];
        while (file.available() > 0) {
            int bytesRead = file.read(readBuf);
            os.write(
                    readBuf,
                    0,
                    bytesRead
            );
        }

        // Convert ByteArrayOutputStream into byte[]
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(
                        bucketName,
                        fileName
                )
                        // Modify access list to allow all users with link to read file
                        .setAcl(new ArrayList<>(Collections.singletonList(Acl.of(
                                Acl.User.ofAllUsers(),
                                Acl.Role.READER
                        ))))
                        .build(),
                os.toByteArray()
        );
        // return the public download link
        return blobInfo.getMediaLink();
    }
    // [END uploadFile]

    public void deleteFile(
            String blobName,
            String bucketName
    ) {
        BlobId blobId = BlobId.of(
                bucketName,
                blobName
        );
        boolean deleted = storage.delete(blobId);
        if (deleted) {
            // the blob was deleted
            System.out.println("OK");
        }
        else {
            // the blob was not found
            System.out.println("FAILE");
        }
    }
}