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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    private final Path fileStorageLocation;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        // { Class Paths }
        // toAbsolutePath() 이 패스의 절대 패스를 나타내는 Path 오브젝트를 돌려줍니다.
        // normalize() 중복 된 이름의 경로가 제거 된 후 경로를 반환합니다.

        try {
             // { Class File }
             // createDirectories(Path dir)
             // 존재하지 않는 모든 부모 디렉토리를 먼저 작성하여 디렉토리를 작성합니다.

            Files.createDirectories(this.fileStorageLocation);
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

            /*
             * 파일타입을 구분합니다.
             * 이미지일경우 1
             * 이외 다른 파일인 경우 0 으로 구분하여 저장합니다.
             */
            String thisfileType = file.getContentType();
            int fileDivision = 0;
            fileDivision = thisfileType.split("/")[0].equals("image") ? 1 : 0;

            // DB Save Code
            File dbFile = File.builder()
                    .filename(file.getName())
                    .fileType(fileType)
                    .fileSize(file.getSize())
                    .fileDivision(fileDivision)
                    .build();

            File fileData = fileRepository.save(dbFile);

            // 대상 위치로 파일 복사 (기존 파일을 같은 이름으로 바꾸기)
            // 파일이름을 DB에 저장하는 ID 값으로 저장합니다.
            /*
             * { Class java.nio.file.Path }
             * Path.resolve 는 하나의 경로를 다른것과 합치는데 사용
             * targetLocation 값에는 경로 + 파일 이름이 저장됩니다.
             * */
            String fileUrl = fileData.getId().toString() + fileType;
            Path targetLocation = this.fileStorageLocation.resolve(fileUrl);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileRepository.save(fileData);
        } catch (IOException e) {
            throw new StoreFileException("파일 " + fileName + " 를 저장할 수 없습니다.", e);
        }
    }

    // BufferedImage croppedImage = cropImageSquare(file.getBytes());
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

        // 파일 crop 참고 링크
        // https://blog.netgloo.com/2015/03/03/spring-boot-crop-uploaded-image/

        return croppedImage;
    }
}
