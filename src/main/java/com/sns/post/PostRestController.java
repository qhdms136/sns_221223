package com.sns.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sns.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	@Autowired
	private PostBO postBO;
	
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("content") String content,
			@RequestParam("file") MultipartFile file,
			HttpSession session) {
		// 세션에서 유저 정보 꺼내오기
		Integer userId = (Integer)session.getAttribute("userId");
		String userloginId = (String)session.getAttribute("userloginId");
		
		Map<String, Object> result = new HashMap<>();
		
		// db insert
		int rowCount = postBO.addPost(userId, userloginId, content, file);
		
		if (userId == null) {
			result.put("code", 500); // 비로그인 상태
			result.put("result", "error");
			result.put("errorMessage", "로그인을 해주세요.");
			return result;
		}
		if(rowCount > 0) {
			result.put("code", 1);
			result.put("result", "성공");
		}
		return result;
	}
}