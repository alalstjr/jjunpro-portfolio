package com.jjunpro.koreanair.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjunpro.koreanair.board.domain.BoardTask;
import com.jjunpro.koreanair.board.service.BoardTaskService;
import com.jjunpro.koreanair.file.domain.DBFile;
import com.jjunpro.koreanair.file.service.FileStorageService;

@RestController
@RequestMapping("/api/board")
@CrossOrigin
public class BoardTaskController {
	
	@Autowired
	private BoardTaskService boardTaskService;
	
	@Autowired
    private FileStorageService fileStorageService;
	
	// 생성 CREATE or UADATE
	@PostMapping("/")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> addPTToBoard(
			@Valid @RequestBody BoardTask boardTask, 
			BindingResult result, 
			HttpServletRequest request
	) {
		// IP 정보
		String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }
        
		if(result.hasErrors()) {
			Map<String, String> errorMap = new HashMap<String, String>();
			
			for(FieldError error : result.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			
			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		}
		
		BoardTask newBT = boardTaskService.saveOrUpdateBoardTask(boardTask, ip);
		
		return new ResponseEntity<BoardTask>(newBT, HttpStatus.CREATED);
	}
	
	// READ 모든 게시글 조회
	@GetMapping("/")
	public Iterable<BoardTask> getAllPTs(Sort sort) {
		sort = sort.and(new Sort(Sort.Direction.DESC, "regDate"));
		return boardTaskService.findAll(sort);
	}
	
	// READ 특정 게시글 조회
	@GetMapping("/{bo_num}")
	public ResponseEntity<?> getPTById(@PathVariable Long bo_num) {
		// 게시판 목록
		BoardTask boardTask = boardTaskService.findById(bo_num);
		
		// 게시글 조회가 안될경우 에러 리턴 (무분별한 게시글 입장 제한)
		if(boardTask == null) {
			String error = "존재하지 않는 게시글 입니다.";
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("error", error);
			System.out.println(errorMap);
			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		}
		
		// 해당 게시판의 모든 파일 목록
		DBFile[] files = fileStorageService.findByFile(bo_num);
		
		// 해당 게시판의 이미지 파일 목록
		DBFile[] imgFiles = fileStorageService.findByImgFile(bo_num);

		Map<Object, Object> boardView = new HashMap<Object, Object>();
		boardView.put("boardTask", boardTask);
		boardView.put("imgFiles", imgFiles);
		boardView.put("files", files);
		
		return new ResponseEntity<Map>(boardView, HttpStatus.OK);
	}
	
	// READ 카테고리 조회
	@GetMapping("/all/cate/{bo_category}")
	public Iterable<?> getBTByCa(@PathVariable String bo_category) {
		// 카테고리 게시판 목록
		return boardTaskService.findByCate(bo_category);
	}
	
	// DELETE 게시글 삭제
	@DeleteMapping("/{bo_num}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable Long bo_num) {
		boardTaskService.delete(bo_num);
		
		return new ResponseEntity<String>("Board Task delete", HttpStatus.OK);
	}
	
	// 페이징 & 게시글 조회
	@GetMapping("/page")
	public Page<BoardTask> getAccounts(
			@PageableDefault(sort = { "regDate" }, direction = Direction.DESC, size = 2)
			Pageable pageable
		) {
        return boardTaskService.findAll(pageable);
    }
	
	// 페이징 & 특정 카테고리 게시글 조회
	@GetMapping("/cate/{bo_category}")
	public Page<BoardTask> getBTByCaPaging(
			@PageableDefault(sort = { "regDate" }, direction = Direction.DESC, size = 2)
			Pageable pageable,
			@PathVariable String bo_category
		) {
        return boardTaskService.findByCate(bo_category, pageable);
    }
}
