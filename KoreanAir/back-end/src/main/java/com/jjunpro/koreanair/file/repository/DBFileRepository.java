package com.jjunpro.koreanair.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jjunpro.koreanair.file.dto.DBFile;

//JpaRepository에 기본적인 CRUD 메소드들이 정의되어 있으므로 따로 구현할 필요없다.
@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String>{
	long countByFileBoNum(long num);
	
	// 게시판 미리보기 이미지 업데이트 쿼리
	DBFile findTop1ByFileBoNumAndFileDivisionOrderByFileNoAsc(long num, int division);
	
	// 게시판 모든 파일 가져오는 쿼리
	DBFile[] findByFileBoNumOrderByFileNoAsc(long num);
	
	// 게시글의 이미지 파일만 가져오는 쿼리
	DBFile[] findByFileBoNumAndFileDivisionOrderByFileNoAsc(long num, int division);
	
	// 게시글 수정시 파일 삭제가 전송된경우 bo_num 값을 확인하여 파일 삭제(숨김) 처리
	DBFile findByFileBoNumAndId(long num, String file);
}

//fileBoNum 1 이고
//fileType 이 이미지이고
//FileNo 내림차순중 첫번째인 칼럼을 불러온다.
//
//SELECT * FROM g
//	WHERE fileBoNum = 1
//	AND fileType = "image/jpeg"
//	ORDER BY FileNo ASC 

//@Query("SELECT u FROM upload_file u"
//+ "WHERE u.fileBoNum = :num"
//+ "AND u.fileType = 'image/jpeg'"
//+ "ORDER BY u.FileNo ASC");