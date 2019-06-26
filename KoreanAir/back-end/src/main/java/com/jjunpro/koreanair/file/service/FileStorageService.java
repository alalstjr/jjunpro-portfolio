package com.jjunpro.koreanair.file.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jjunpro.koreanair.board.dto.BoardTask;
import com.jjunpro.koreanair.board.repository.BoardTaskRepository;
import com.jjunpro.koreanair.board.service.BoardTaskService;
import com.jjunpro.koreanair.file.dto.DBFile;
import com.jjunpro.koreanair.file.exception.FileStorageException;
import com.jjunpro.koreanair.file.exception.MyFileNotFoundException;
import com.jjunpro.koreanair.file.repository.DBFileRepository;
import com.jjunpro.koreanair.property.FileStorageProperties;

@Service
public class FileStorageService {

	@Autowired
    private DBFileRepository dbFileRepository;
	
	@Autowired
	private BoardTaskRepository boardTaskRepository;
	
	@Autowired
	private BoardTaskService boardTaskService;
	
	
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        /*
         * { Class Paths }
         * toAbsolutePath() 이 패스의 절대 패스를 나타내는 Path 오브젝트를 돌려줍니다.
         * normalize() 중복 된 이름의 경로가 제거 된 후 경로를 반환합니다. 
         */

        try {
        	/*
        	 * { Class File } 
        	 * createDirectories(Path dir)
        	 * 존재하지 않는 모든 부모 디렉토리를 먼저 작성하여 디렉토리를 작성합니다.
        	 */
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("업로드 된 파일을 저장할 디렉토리를 만들 수 없습니다.", e);
        }
    }

    public String storeFile(MultipartFile file, long num) {
        // 파일 이름 표준화
    	/*
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
                throw new FileStorageException("\r\n" + "오류! 파일 이름에 잘못된 경로 시퀀스가 ​​있습니다. " + fileName);
            }

            // DB Save Code
            DBFile dbFile = new DBFile(fileName, file.getSize(), file.getContentType(), num);
            dbFileRepository.save(dbFile);
            
            // 대상 위치로 파일 복사 (기존 파일을 같은 이름으로 바꾸기)
            // 파일이름을 DB에 저장하는 ID 값으로 저장합니다.
            /*
             * { Class java.nio.file.Path }
             * Path.resolve 는 하나의 경로를 다른것과 합치는데 사용
             * targetLocation 값에는 경로 + 파일 이름이 저장됩니다.
             * */
            String fileUrl = dbFile.getId() + fileType;
            Path targetLocation = this.fileStorageLocation.resolve(fileUrl);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileUrl;
        } catch (IOException e) {
            throw new FileStorageException("파일 " + fileName + " 를 저장할 수 없습니다.", e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new MyFileNotFoundException("File not found " + fileName, e);
        }
    }
    
    /*
     * 게시판 미리보기 이미지 업데이트 함수 
     */
    public void thumbUpdate(long num, String fileDownloadUri) {
		BoardTask boardTask = boardTaskService.findById(num);
		boardTask.setThumb(fileDownloadUri);
		
		boardTaskRepository.save(boardTask);
	}
}