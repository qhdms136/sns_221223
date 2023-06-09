package com.sns.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sns.post.bo.PostBO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Tag(name="PostRestController", description="게시물 생성과 삭제기능")
@RestController
public class PostRestController {
	
	@Autowired
	private PostBO postBO;
	
	@Operation(summary = "게시물 생성", description = "content, file, 로그인 되어있는 userId의 값을 받아 게시물을 생성해주는 기능")
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("content") String content,
			@RequestParam("file") MultipartFile file,
			HttpSession session) {
		// 세션에서 유저 정보 꺼내오기
		Integer userId = (Integer)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		Map<String, Object> result = new HashMap<>();
		
		// db insert
		int rowCount = postBO.addPost(userId, userLoginId, content, file);
		
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
	
	@Operation(summary = "게시물 삭제", description = "postId, 로그인 되어있는 userId 값을 조회해서 게시물 삭제기능")
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId") int postId,
			HttpSession session){
		Map<String, Object> result = new HashMap<>();
		// 세션 정보 꺼내오기
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId == null) {
			result.put("code", 300);
			result.put("errorMessage", "로그인을 해주세요");
		}
		// db delete
		int rowCount = postBO.deletePostByPostIdUserId(postId, userId);
		// 응답
		if(rowCount > 0) {
			result.put("code", 1);
			result.put("result", "성공");			
		} else {
			result.put("code", 500);
			result.put("errorMessage", "게시물 삭제중 오류가 발생했습니다. 관리자에게 문의해주세요");
		}
		return result;
	}
}