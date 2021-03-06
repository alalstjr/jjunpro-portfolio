package com.jjunpro.project.service;

import com.jjunpro.project.common.CloudStorageHelper;
import com.jjunpro.project.domain.File;
import com.jjunpro.project.enums.DomainType;
import com.jjunpro.project.exception.FileStorageException;
import com.jjunpro.project.exception.MyFileNotFoundException;
import com.jjunpro.project.exception.SimpleException;
import com.jjunpro.project.properties.FileStorageProperties;
import com.jjunpro.project.repository.FileRepository;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.RequiredArgsConstructor;
import org.imagelib.ImageLib;
import org.imgscalr.Scalr;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${max-upload-count}")
    private Integer _maxUploadCount;

    private final FileRepository fileRepository;
    private final Path           fileStorageLocation;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties,
            FileRepository fileRepository) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        this.fileRepository = fileRepository;

        try {
            /* 파일을 저장할 폴더를 생성합니다. */
            Files.createDirectories(
                    this.fileStorageLocation.resolve(DomainType.ACCOUNT.getValue()));
            Files.createDirectories(
                    this.fileStorageLocation.resolve(DomainType.UNIVERSITY.getValue()));
        } catch (Exception ex) {
            throw new FileStorageException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    @Override
    public Optional<File> findById(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public void fileDelete(File file) {
        String fileUrl      = file.getFileOriginal();
        String thumbnailUrl = file.getFileThumbnail();

        try {
            /* 원본파일 삭제 */
            Path filePath = this.fileStorageLocation
                    .resolve(fileUrl).normalize();
            Files.delete(filePath);

            /* 썸네일 삭제 */
            Path filePathThumb = this.fileStorageLocation
                    .resolve(thumbnailUrl).normalize();
            Files.delete(filePathThumb);

            // DB File Delete
            fileRepository.delete(file);
        } catch (
                MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found", ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void filesDelete(List<File> file) {
        for (File value : file) {
            fileDelete(value);
        }
    }

    /* Client 에서 기존 업로드된 파일의 제거된 {id}값을 기존 제거되는 file 반환하는 메소드 */
    @Override
    public void deleteFileFilter(Long[] removeFiles) {
        Optional<File> uniFile;

        if (removeFiles != null && removeFiles.length > 0) {
            for (Long removeFile : removeFiles) {
                uniFile = findById(removeFile);
                uniFile.ifPresent(this::fileDelete);
            }
        }
    }

    @Override
    public List<File> uploadMultipleFiles(
            int fileSizeDB,
            MultipartFile[] files,
            String domain
    ) {
        /* File 최대갯수 확인 UPDATE 기존 file과 수정되는 file 갯수의 최대값을 비교합니다. */
        if (files != null && fileSizeDB + files.length > _maxUploadCount) {
            try {
                throw new SimpleException("파일은 최대 " + _maxUploadCount + "개 까지만 추가할 수 있습니다.");
            } catch (SimpleException e) {
                e.printStackTrace();
            }
        }

        /* 서버로 받은 파일'들'을 List로 변환하여 하나씩 서버로 업로드 합니다. */
        assert files != null;
        return Arrays
                .stream(files)
                .map(file -> uploadFile(
                        file,
                        domain
                ))
                .collect(Collectors.toList());
    }

    @Override
    public File uploadFile(
            MultipartFile file,
            String fileRouter
    ) {
        /*
         * 파일 이름 표준화
         * { Class StringUtils }
         * cleanPath()
         * 경로 / .." 및 내부 단순 점과 같은 시퀀스를 억제하여 경로를 표준화하십시오.
         * 결과는 경로 비교에 편리합니다.
         * 예제로 Windows 구분 기호 ("\")가 간단한 슬래시로 바뀌 었음을 알 수 있습니다.
         */
        final String fileOriName = StringUtils
                .cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        final String fileType = fileOriName
                .substring(fileOriName.lastIndexOf("."))
                .replace(
                        ".",
                        ""
                );
        final String fileRoute = fileRouter + "/";

        try {
            // 파일의 이름에 유효하지 않은 문자가 포함되어 있는지 확인합니다.
            if (fileOriName.contains("..")) {
                throw new SimpleException("\r\n" + "file name error!. " + fileOriName);
            }

            /*
             * 파일타입을 구분합니다.
             * 이미지일경우 1
             * 이외 다른 파일인 경우 0 으로 구분하여 저장합니다.
             */
            String thisfileType = file.getContentType();
            int    fileDivision = 0;

            assert thisfileType != null;
            fileDivision = thisfileType.split("/")[0].equals("image") ? 1 : 0;

            String gcsFileName = handleFileName(
                    fileOriName,
                    fileRoute
            );

            // 썸네일 이미지파일 저장
            // resizeWidth, resizeHeight 줄이는 이미지 크기
            // resizeContent 이미지 이름에 들어가는 사이즈 크기 문자열
            int resizeWidth  = 600;
            int resizeHeight = 600;
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

            /* 대상 위치로 파일 복사(같은 이름으로 기존 파일 바꾸기) */
            Path targetLocation = this.fileStorageLocation
                    .resolve(gcsFileName);
            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            Path targetLocationThumb = this.fileStorageLocation
                    .resolve(gcsThumbFileName);
            Files.copy(
                    thumbnail,
                    targetLocationThumb,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return fileRepository.save(dbFile);
        } catch (IOException e) {
            throw new SimpleException(
                    "file " + fileOriName + " not save!!.",
                    e
            );
        }
    }

    private String handleFileName(
            String fileOriName,
            String fileRoute
    ) {
        DateTimeFormatter dtf      = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
        DateTime          dt       = DateTime.now(DateTimeZone.UTC);
        String            dtString = dt.toString(dtf);

        final String fileName = fileOriName.substring(
                0,
                fileOriName.lastIndexOf(".")
        );
        final String fileType = fileOriName.substring(fileOriName.lastIndexOf("."));

        return fileRoute + fileName + dtString + fileType;
    }

    private String handleThumbFileName(
            String gcsFileName,
            String size
    ) {
        final String fileName = gcsFileName.substring(
                0,
                gcsFileName.lastIndexOf(".")
        );
        final String fileType = gcsFileName.substring(gcsFileName.lastIndexOf("."));

        return fileName + size + fileType;
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path     filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
