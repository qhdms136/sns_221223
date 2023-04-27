package com.sns.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sns.like.bo.LikeBO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@Tag(name="LikeRestController", description="값을 동적으로 받아 토글형식으로 만든다 (@PathVariable int postId)")
public class LikeRestController {
	
	@Autowired
	private LikeBO likeBO;
	
	//	GET: /like?postId=13	@RequsetParam
	//	GET or POST: /like/{13}	@PathVariable
	@RequestMapping("/like/{postId}")
	public Map<String, Object> like(
			@PathVariable int postId,
			HttpSession session){
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("userId");
		
		if (userId == null) {
			result.put("code", 300); // 비로그인 상태
			result.put("result", "error");
			result.put("errorMessage", "로그인을 해주세요.");
			return result;
		}
		// BO 호출 => BO안에서 like 여부 체크
		likeBO.LikeToggle(postId, userId);

		// 응답(토글)
		result.put("code", 1);
		result.put("result", "성공");
		return result;					
	}
	
}
