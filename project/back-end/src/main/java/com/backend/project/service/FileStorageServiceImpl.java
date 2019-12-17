package com.backend.project.service;

import com.backend.project.domain.File;
import com.backend.project.exception.StoreFileException;
import com.backend.project.repository.FileRepository;
import com.backend.project.util.CloudStorageHelper;
import org.imgscalr.Scalr;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
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
public class FileStorageServiceImpl implements FileStorageService
{
    private final String GCSID = "spring-project-261615_cloudbuild";

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CloudStorageHelper cloudStorageHelper;

    @Override
    public Optional<File> findById(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public void fileDelete(File file)
    {
        String fileUrl = file.getFileOriginal();
        String thumbnailUrl = file.getFileThumbnail();

        // GCS File Delete
        cloudStorageHelper.deleteFile(fileUrl, GCSID);
        cloudStorageHelper.deleteFile(thumbnailUrl, GCSID);

        // DB File Delete
        fileRepository.delete(file);
    }

    @Override
    public void filesDelete(List<File> file) {
        for(var i = 0; file.size() > i; i++) {
            fileDelete(file.get(i));
        }
    }

    @Override
    public List<File> uploadMultipleFiles(MultipartFile[] files, String domain) {

        // 서버로 받은 파일'들'을 List로 변환하여 하나씩 서버로 업로드 합니다.
        List<File> fileResult = Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, domain))
                .collect(Collectors.toList());

        return fileResult;
    }

    @Override
    public File uploadFile(MultipartFile file, String domain)
    {
        /*
         * 파일 이름 표준화
         * { Class StringUtils }
         * cleanPath()
         * 경로 / .." 및 내부 단순 점과 같은 시퀀스를 억제하여 경로를 표준화하십시오.
         * 결과는 경로 비교에 편리합니다.
         * 예제로 Windows 구분 기호 ("\")가 간단한 슬래시로 바뀌 었음을 알 수 있습니다.
         */
        final String fileOriName = StringUtils.cleanPath(file.getOriginalFilename());
        final String fileType = fileOriName.substring(fileOriName.lastIndexOf(".")).replace(".", "");
        final String fileRoute = domain + "/";

        try
        {
            // 파일의 이름에 유효하지 않은 문자가 포함되어 있는지 확인합니다.
            if(fileOriName.contains(".."))
            {
                throw new StoreFileException("\r\n" + "file name error!. " + fileOriName);
            }

            /*
             * 파일타입을 구분합니다.
             * 이미지일경우 1
             * 이외 다른 파일인 경우 0 으로 구분하여 저장합니다.
             */
            String thisfileType = file.getContentType();
            int fileDivision = 0;
            fileDivision = thisfileType.split("/")[0].equals("image") ? 1 : 0;

            String gcsFileName = handleFileName(fileOriName, fileRoute);

            // 썸네일 이미지파일 저장
            // resizeWidth, resizeHeight 줄이는 이미지 크기
            // resizeContent 이미지 이름에 들어가는 사이즈 크기 문자열
            Integer resizeWidth = 300;
            Integer resizeHeight = 300;
            String resizeContent = imageSizeCheck(file.getBytes()) ? "-" + resizeWidth + "x" + resizeHeight : "-thumb";

            String gcsThumbFileName = handleThumbFileName(gcsFileName, resizeContent);

            // 이미지 자르기 (uploadfile은 MultipartFile 유형의 객체 임)
            BufferedImage resizeImage = imageSizeCheck(file.getBytes()) ? handleThumbnail(file.getBytes(), resizeWidth, resizeHeight) : cropImageSquare(file.getBytes());

            // Bufferedimage to Inputstream
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(resizeImage, fileType, os);
            InputStream thumbnail = new ByteArrayInputStream(os.toByteArray());

            // DB Save Code
            File dbFile = File.builder()
                    .filename(file.getOriginalFilename())
                    .fileType(fileType)
                    .fileSize(file.getSize())
                    .fileDivision(fileDivision)
                    .fileOriginal(gcsFileName)
                    .fileThumbnail(gcsThumbFileName)
                    .build();

            File fileData = fileRepository.save(dbFile);

            // GCS Upload 원본 이미지파일 저장
            try
            {
                cloudStorageHelper.uploadFile(file.getInputStream(), gcsFileName, GCSID);
                cloudStorageHelper.uploadFile(thumbnail, gcsThumbFileName, GCSID);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return fileRepository.save(fileData);
        } catch (IOException e) {
            throw new StoreFileException("file " + fileOriName + " not save!!.", e);
        }
    }

    /**
     * 이미지를 정사각형으로 잘라주는 메소드입니다.
     */
    private BufferedImage cropImageSquare(byte[] image) throws IOException {
        // 바이트 배열에서 BufferedImage 객체를 가져옵니다.
        InputStream in = new ByteArrayInputStream(image);
        BufferedImage originalImage = ImageIO.read(in);

        // 이미지 치수 얻기
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();

        // 이미지가 이미 정사각형인지 확인합니다.
        if (height == width) {
            return originalImage;
        }

        // 정사각형의 크기를 계산합니다.
        int squareSize = (height > width ? width : height);

        // 이미지 중간 좌표
        int xc = width / 2;
        int yc = height / 2;

        // Crop
        BufferedImage croppedImage = originalImage.getSubimage(
                xc - (squareSize / 2), // x coordinate of the upper-left corner
                yc - (squareSize / 2), // y coordinate of the upper-left corner
                squareSize,            // widht
                squareSize             // height
        );

        return croppedImage;
    }

    /**
     *  이미지 파일을 설정에 맞춰서 크기를 맞춰주는 메소드입니다.
     *  썸네일 이미지 생성에 사용됩니다.
     */
    private BufferedImage handleThumbnail(byte[] image, int width, int height) throws IOException {
        // 바이트 배열에서 BufferedImage 객체를 가져옵니다.
        InputStream in = new ByteArrayInputStream(image);
        BufferedImage originalImage = ImageIO.read(in);

        // 이미지 치수 얻기
        int originWidth = originalImage.getWidth();
        int originHeight = originalImage.getHeight();

        // 늘어날 길이를 계산하여 패딩합니다.
        int padding = 0;
        if(originWidth > originHeight) {
            padding = (int)(Math.abs((height * originWidth / (double)width) - originHeight) / 2d);
        } else {
            padding = (int)(Math.abs((width * originHeight / (double)width) - originWidth) / 2d);
        }
        originalImage = Scalr.pad(originalImage, padding, Color.WHITE, Scalr.OP_ANTIALIAS);

        // 이미지 크기가 변경되었으므로 다시 구합니다.
        originWidth = originalImage.getWidth();
        originHeight = originalImage.getHeight();

        // 썸네일 비율로 크롭할 크기를 구합니다.
        int newWidth = originWidth;
        int newHeight = (originWidth * height) / width;

        if(newHeight > originHeight)
        {
            newWidth = (originHeight * width) / height;
            newHeight = originHeight;
        }

        // 늘려진 이미지의 중앙을 썸네일 비율로 크롭 합니다.
        BufferedImage cropImg = Scalr.crop(originalImage, (originWidth-newWidth)/2, (originHeight-newHeight)/2, newWidth, newHeight);
        // 리사이즈(썸네일 생성)
        BufferedImage result = Scalr.resize(cropImg, width, height);

        return result;
    }

    private String handleFileName(String fileOriName, String fileRoute)
    {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
        DateTime dt = DateTime.now(DateTimeZone.UTC);
        String dtString = dt.toString(dtf);

        final String fileName = fileOriName.substring(0, fileOriName.lastIndexOf("."));
        final String fileType = fileOriName.substring(fileOriName.lastIndexOf("."));

        String result = fileRoute + fileName + dtString + fileType;

        return result;
    }

    private String handleThumbFileName(String gcsFileName, String size)
    {
        final String fileName = gcsFileName.substring(0, gcsFileName.lastIndexOf("."));
        final String fileType = gcsFileName.substring(gcsFileName.lastIndexOf("."));

        String result = fileName + size + fileType;

        return result;
    }

    /**
     *  서버로 업로드되는 이미지의 크기가 300x300 기준을 체크하는 메소드입니다.
     *  thumb file 제작중에 사이즈가 작은 file을 잡아내는 역할을 합니다.
     */
    private Boolean imageSizeCheck(byte[] image) throws IOException
    {
        InputStream in = new ByteArrayInputStream(image);
        BufferedImage originalImage = ImageIO.read(in);

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        if(width < 300 || height < 300)
        {
            return false;
        }
        return true;
    }
}
