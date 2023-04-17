package com.sns.post.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sns.post.dao.PostMapper;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;
	
	public int addPost(
			int userId,
			String loginId,
			String content,
			MultipartFile file) {
		// 예외 처리
		String imagPath = null;
		// 서버 이미지 업로드 후 imagePath 받아옴
		/*
		 * if(file != null) { imagePath = ; }
		 */
		return postMapper.insertPost();
	}
}
