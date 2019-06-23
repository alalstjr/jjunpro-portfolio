package com.jjunpro.koreanair.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jjunpro.koreanair.file.dto.DBFile;

//JpaRepository에 기본적인 CRUD 메소드들이 정의되어 있으므로 따로 구현할 필요없다.
@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String>{

}
