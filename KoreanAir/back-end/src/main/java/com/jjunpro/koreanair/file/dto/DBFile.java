package com.jjunpro.koreanair.file.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "upload_file")
public class DBFile {
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(name = "bf_bo_num")
	private String fileBoNum;
	
	@Column(name = "bf_file_name", length = 255)
	private String fileName;
	
	@Column(name = "bf_file_size")
	private long fileSize;

	@Column(name = "bf_file_type")
	private String fileType;
	
	@Column(name = "bf_file_download")
	private int fileDownload;
	
	@CreationTimestamp
	@Column(name = "bf_datetime")
	private LocalDateTime dateTime;

	public DBFile() {
		super();
	}

	public DBFile(String fileName, long fileSize, String fileType) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileType = fileType;
	}
}
