package com.jjunpro.koreanair.file.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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
	
	@Column(name = "bf_file_name", length = 255)
	private String fileName;
	
	@Column(name = "bf_file_type")
	private String fileType;
	
	@Lob
    private byte[] data;
	
	@CreationTimestamp
	@Column(name = "bf_datetime")
	private LocalDateTime dateTime;

	public DBFile() {
		super();
	}
	
	public DBFile( String fileName, String fileType, byte[] data) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}
	
//	public DBFile(String fileName, String fileType, byte[] data) {
//        this.fileName = fileName;
//        this.fileType = fileType;
//        this.data = data;
//    }
	
	
}
