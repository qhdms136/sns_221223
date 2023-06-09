package com.sns.comment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.comment.bo.CommentBO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/comment")
@RestController
@Tag(name="CommentRestController", description="댓글 추가와 댓글 삭제 기능")
public class CommentRestController {
	
	@Autowired
	private CommentBO commentBO;
	
	
	@Operation(summary="댓굴 추가", description="postId, content를 받아 댓글 생성 기능")
	@PostMapping("/create")
	public Map<String, Object> commentCreate(
			@RequestParam("postId") int postId,
			@RequestParam("content") String content,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId == null) {
			result.put("code", 300);
			result.put("errorMessage", "로그인을 해주세요");
			return result;	// 일괄적으로 처리하기 힘드므로 나눠서 처리
		}
		// db insert
		int rowCount = commentBO.addComment(userId, postId, content);
		if(rowCount > 0 ) {
			result.put("code", 1);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "댓글 작성에 실패했습니다.");
		}
		return result;
	}
	@Operation(summary = "댓글 삭제", description = "댓글 삭제 시 값을 동적으로 받아 삭제한다.(@PathVariable int commentId)")
	@RequestMapping("/delete/{commentId}")
	public Map<String, Object> commentDelete(
			@PathVariable int commentId,
			HttpSession session){
		Map<String, Object> result = new HashMap<>();
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId == null) {
			result.put("code", 300);
			result.put("errorMessage", "로그인을 해주세요");
			return result;	// 일괄적으로 처리하기 힘드므로 나눠서 처리
		}
		
		// delete
		commentBO.deleteComment(commentId);
		
		// 응답
		result.put("code", 1);
		result.put("result", "댓글을 삭제하였습니다.");
		return result;
	}
}
