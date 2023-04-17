package com.sns.timeline;

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

@RequestMapping("/timeline")
@RestController
public class TimeLineRestController {
	
	@Autowired
	private PostBO postBO;
	
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam(value="writeTextArea", required=false) String writeTextArea,
			@RequestParam("file") MultipartFile file,
			HttpSession session) {
		// 세션에서 유저 정보 꺼내오기
		int userId = (int)session.getAttribute("userId");
		String userloginId = (String)session.getAttribute("userId");
		
		// db insert
		int rowCount = postBO.addPost(userId, userloginId, writeTextArea, file);
		
		Map<String, Object> result = new HashMap<>();
		if(rowCount > 0) {
			result.put("code", 1);
		}
		return result;
	}
}
