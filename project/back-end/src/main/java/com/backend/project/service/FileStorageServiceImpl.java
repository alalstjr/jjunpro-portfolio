package com.backend.project.service;

import com.backend.project.domain.File;
import com.backend.project.exception.StoreFileException;
import com.backend.project.property.FileStorageProperties;
import com.backend.project.repository.FileRepository;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    private final Path fileStorageLocation;
    private final Path fileStorageLocationThumbnail;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        // { Class Paths }
        // toAbsolutePath() 이 패스의 절대 패스를 나타내는 Path 오브젝트를 돌려줍니다.
        // normalize() 중복 된 이름의 경로가 제거 된 후 경로를 반환합니다.

        // 원본 경로
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        // 썸네일 경로
        this.fileStorageLocationThumbnail = Paths.get(fileStorageProperties.getUploadDirThumbnail())
                .toAbsolutePath().normalize();


        try {
             // { Class File }
             // createDirectories(Path dir)
             // 존재하지 않는 모든 부모 디렉토리를 먼저 작성하여 디렉토리를 작성합니다.

            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(this.fileStorageLocationThumbnail);
        } catch (Exception e) {
            throw new StoreFileException("업로드 된 파일을 저장할 디렉토리를 만들 수 없습니다.", e);
        }
    }

    @Override
    public List<File> uploadMultipleFiles(MultipartFile[] files) {

        // 서버로 받은 파일'들'을 List로 변환하여 하나씩 서버로 업로드 합니다.
        List<File> fileResult = Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());

        return fileResult;
    }

    @Override
    public File uploadFile(MultipartFile file) {
        /*
         * 파일 이름 표준화
         * { Class StringUtils }
         * cleanPath()
         * 경로 / .." 및 내부 단순 점과 같은 시퀀스를 억제하여 경로를 표준화하십시오.
         * 결과는 경로 비교에 편리합니다.
         * 예제로 Windows 구분 기호 ("\")가 간단한 슬래시로 바뀌 었음을 알 수 있습니다.
         */
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileType = fileName.substring(fileName.lastIndexOf("."));

        try {
            // 파일의 이름에 유효하지 않은 문자가 포함되어 있는지 확인합니다.
            if(fileName.contains("..")) {
                throw new StoreFileException("\r\n" + "오류! 파일 이름에 잘못된 경로 시퀀스가 ​​있습니다. " + fileName);
            }

            // 이미지 파일 제목중복을 방지하기 위해서 UUID 값을 활용합니다.
            UUID uuid = UUID.randomUUID();

            /*
             * 파일타입을 구분합니다.
             * 이미지일경우 1
             * 이외 다른 파일인 경우 0 으로 구분하여 저장합니다.
             */
            String thisfileType = file.getContentType();
            int fileDivision = 0;
            fileDivision = thisfileType.split("/")[0].equals("image") ? 1 : 0;

            // 대상 위치로 파일 복사 (기존 파일을 같은 이름으로 바꾸기)
            // 파일이름을 DB에 저장하는 ID 값으로 저장합니다.
            /*
             * { Class java.nio.file.Path }
             * Path.resolve 는 하나의 경로를 다른것과 합치는데 사용
             * targetLocation 값에는 경로 + 파일 이름이 저장됩니다.
             * */
            String fileUrl = uuid + fileType;

            // 원본 이미지파일 저장
            Path targetLocation = this.fileStorageLocation.resolve(fileUrl);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 썸네일 이미지파일 저장
            // resizeWidth, resizeHeight 줄이는 이미지 크기
            // resizeContent 이미지 이름에 들어가는 사이즈 크기 문자열
            Integer resizeWidth = 300;
            Integer resizeHeight = 300;
            String resizeContent = "-" + resizeWidth + "x" + resizeHeight;

            String thumbnailUrlt = uuid + resizeContent + fileType;

            // 이미지 자르기 (uploadfile은 MultipartFile 유형의 객체 임)
            BufferedImage resizeImage = resize(file.getBytes(), resizeWidth, resizeHeight);

            // Bufferedimage to Inputstream
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(resizeImage, "jpg", os);
            InputStream thumbnail = new ByteArrayInputStream(os.toByteArray());

            Path targetLocationThumbnail = this.fileStorageLocationThumbnail.resolve(thumbnailUrlt);
            Files.copy(thumbnail, targetLocationThumbnail, StandardCopyOption.REPLACE_EXISTING);

            // DB Save Code
            File dbFile = File.builder()
                    .filename(file.getName())
                    .fileType(fileType)
                    .fileSize(file.getSize())
                    .fileDivision(fileDivision)
                    .fileThumbnail(thumbnailUrlt)
                    .build();

            File fileData = fileRepository.save(dbFile);

            return fileRepository.save(fileData);
        } catch (IOException e) {
            throw new StoreFileException("파일 " + fileName + " 를 저장할 수 없습니다.", e);
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
    private BufferedImage resize(byte[] image, int width, int height) throws IOException {
        // 바이트 배열에서 BufferedImage 객체를 가져옵니다.
        InputStream in = new ByteArrayInputStream(image);
        BufferedImage originalImage = ImageIO.read(in);

        // 이미지 치수 얻기
        int originHeight = originalImage.getHeight();
        int originWidth = originalImage.getWidth();

        Dimension imgSize = new Dimension(originWidth, originHeight);
        Dimension boundary = new Dimension(width, height);
        Dimension resultDimension = getScaledDimension(imgSize, boundary);

        Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = originalImage.getSubimage(width, height, resultDimension.width, resultDimension.height);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resized;
    }

    /**
     *  Java image resize, maintain aspect ratio
     *  이미지 가로 세로 비율치수 조정하는 메소드입니다.
     */
    private Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
}
