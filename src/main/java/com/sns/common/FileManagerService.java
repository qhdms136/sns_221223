package com.sns.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component	//일반적인 spring bean
public class FileManagerService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	// 실제 업로드 된 이미지가 저장될 경로(서버)
	// home -- D:\장보은\6_spring_project\sns\workspace\images
	// C:\\jbe\\6_spring_project\\sns\\workspace\\images
	public static final String FILE_UPLOAD_PATH = "C:\\jbe\\6_spring_project\\sns\\workspace\\images/";
	
	//input : MultipartFile(이미지 파일), loginId
	// output : image path(String)
	public String saveFile(String loginId, MultipartFile file) {
		// 파일 디렉토리(폴더) 예 ) aaaa_1678694564/sun.png
		String directoryName = loginId + "_" + System.currentTimeMillis() + "/";	// "aaaa_1678694564"
		String filePath = FILE_UPLOAD_PATH + directoryName; // D:\\장보은\\6_spring_project\\sns\\workspace\\images/aaaa_1678694564
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			return null; // 폴더 만드는데 실패 시 이미지 경로 null
		}
		
		// 파일 업로드: byte 단위로 업로드
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename(), "UTF-8"); // 디렉토리명 + originalFileName은 사용자가 올린 파일명
			Files.write(path, bytes); // 파일 업로드
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		// 파일 업로드가 성공했으면 이미지 url path를 리턴한다.
		// http://localhost/images/aaaa_1678694564/sun.png
		return "/images/" + directoryName + file.getOriginalFilename(); // originalFileName은 사용자가 올린 파일명
	}
	
	public void deleteFile(String imagePath) {
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		// 이미지 삭제
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("[이미지 삭제] 이미지 삭제 실패. imagePath:{}" , imagePath);
				return;
			}
		}
	}
}
