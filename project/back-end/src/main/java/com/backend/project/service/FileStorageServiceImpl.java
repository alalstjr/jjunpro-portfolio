package com.backend.project.service;

import com.backend.project.domain.File;
import com.backend.project.exception.SimpleException;
import com.backend.project.exception.StoreFileException;
import com.backend.project.repository.FileRepository;
import com.backend.project.util.CloudStorageHelper;
import lombok.RequiredArgsConstructor;
import org.imagelib.ImageLib;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    @Value("${google.id}")
    private String _GCSID;

    @Value("${max-upload-count}")
    private Integer _maxUploadCount;

    private final FileRepository     fileRepository;
    private final CloudStorageHelper cloudStorageHelper;

    @Override
    public Optional<File> findById(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public void fileDelete(File file) {
        String fileUrl      = file.getFileOriginal();
        String thumbnailUrl = file.getFileThumbnail();

        // GCS File Delete
        cloudStorageHelper.deleteFile(
                fileUrl,
                _GCSID
        );
        cloudStorageHelper.deleteFile(
                thumbnailUrl,
                _GCSID
        );

        // DB File Delete
        fileRepository.delete(file);
    }

    @Override
    public void filesDelete(List<File> file) {
        for (var i = 0; file.size() > i; i++) {
            fileDelete(file.get(i));
        }
    }

    /**
     * Client 에서 기존 업로드된 파일의 제거된 {id}값을 기존 제거되는 file 반환하는 메소드
     **/
    @Override
    public void deleteFileFilter(Long[] removeFiles) {
        Optional<File> uniFile;

        if (removeFiles != null && removeFiles.length > 0) {
            for (Long removeFile : removeFiles) {
                uniFile = findById(removeFile);
                uniFile.ifPresent(this :: fileDelete);
            }
        }
    }

    @Override
    public List<File> uploadMultipleFiles(int fileSize, MultipartFile[] files, String domain) {

        // File 최대갯수 확인
        // UPDATE 기존 file과 수정되는 file 갯수의 최대값을 비교합니다.
        if (files != null && fileSize + files.length > _maxUploadCount) {
            try {
                throw new SimpleException("파일은 최대 " + _maxUploadCount + "개 까지만 추가할 수 있습니다.");
            }
            catch (SimpleException e) {
                e.printStackTrace();
            }
        }

        // 서버로 받은 파일'들'을 List로 변환하여 하나씩 서버로 업로드 합니다.
        List<File> fileResult = Arrays
                .asList(files)
                .stream()
                .map(file -> uploadFile(
                        file,
                        domain
                ))
                .collect(Collectors.toList());

        return fileResult;
    }

    @Override
    public File uploadFile(MultipartFile file, String domain) {
        /*
         * 파일 이름 표준화
         * { Class StringUtils }
         * cleanPath()
         * 경로 / .." 및 내부 단순 점과 같은 시퀀스를 억제하여 경로를 표준화하십시오.
         * 결과는 경로 비교에 편리합니다.
         * 예제로 Windows 구분 기호 ("\")가 간단한 슬래시로 바뀌 었음을 알 수 있습니다.
         */
        final String fileOriName = StringUtils.cleanPath(file.getOriginalFilename());
        final String fileType = fileOriName
                .substring(fileOriName.lastIndexOf("."))
                .replace(
                        ".",
                        ""
                );
        final String fileRoute = domain + "/";

        try {
            // 파일의 이름에 유효하지 않은 문자가 포함되어 있는지 확인합니다.
            if (fileOriName.contains("..")) {
                throw new StoreFileException("\r\n" + "file name error!. " + fileOriName);
            }

            /*
             * 파일타입을 구분합니다.
             * 이미지일경우 1
             * 이외 다른 파일인 경우 0 으로 구분하여 저장합니다.
             */
            String thisfileType = file.getContentType();
            int    fileDivision = 0;
            fileDivision = thisfileType.split("/")[0].equals("image") ? 1 : 0;

            String gcsFileName = handleFileName(
                    fileOriName,
                    fileRoute
            );

            // 썸네일 이미지파일 저장
            // resizeWidth, resizeHeight 줄이는 이미지 크기
            // resizeContent 이미지 이름에 들어가는 사이즈 크기 문자열
            Integer resizeWidth  = 600;
            Integer resizeHeight = 600;
            // String resizeContent = imageSizeCheck(file.getBytes()) ? "-" + resizeWidth + "x" + resizeHeight : "-thumb";
            String resizeContent = "-" + resizeWidth + "x" + resizeHeight;

            String gcsThumbFileName = handleThumbFileName(
                    gcsFileName,
                    resizeContent
            );

            // 이미지 자르기 (uploadfile은 MultipartFile 유형의 객체 임)
            // BufferedImage resizeImage = imageSizeCheck(file.getBytes()) ? handleThumbnail(file.getBytes(), resizeWidth, resizeHeight) : cropImageSquare(file.getBytes());
            BufferedImage resizeImage = ImageLib.handleThumbnail(
                    file.getBytes(),
                    resizeWidth,
                    resizeHeight
            );

            // Bufferedimage to Inputstream
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(
                    resizeImage,
                    fileType,
                    os
            );
            InputStream thumbnail = new ByteArrayInputStream(os.toByteArray());

            // DB Save Code
            File dbFile = File
                    .builder()
                    .filename(file.getOriginalFilename())
                    .fileType(fileType)
                    .fileSize(file.getSize())
                    .fileDivision(fileDivision)
                    .fileOriginal(gcsFileName)
                    .fileThumbnail(gcsThumbFileName)
                    .build();

            File fileData = fileRepository.save(dbFile);

            // GCS Upload 원본 이미지파일 저장
            try {
                cloudStorageHelper.uploadFile(
                        file.getInputStream(),
                        gcsFileName,
                        _GCSID
                );
                cloudStorageHelper.uploadFile(
                        thumbnail,
                        gcsThumbFileName,
                        _GCSID
                );
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return fileRepository.save(fileData);
        }
        catch (IOException e) {
            throw new StoreFileException(
                    "file " + fileOriName + " not save!!.",
                    e
            );
        }
    }

    private String handleFileName(String fileOriName, String fileRoute) {
        DateTimeFormatter dtf      = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
        DateTime          dt       = DateTime.now(DateTimeZone.UTC);
        String            dtString = dt.toString(dtf);

        final String fileName = fileOriName.substring(
                0,
                fileOriName.lastIndexOf(".")
        );
        final String fileType = fileOriName.substring(fileOriName.lastIndexOf("."));

        String result = fileRoute + fileName + dtString + fileType;

        return result;
    }

    private String handleThumbFileName(String gcsFileName, String size) {
        final String fileName = gcsFileName.substring(
                0,
                gcsFileName.lastIndexOf(".")
        );
        final String fileType = gcsFileName.substring(gcsFileName.lastIndexOf("."));

        String result = fileName + size + fileType;

        return result;
    }
}
