package com.sns.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.model.CardView;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/timeline")
@Controller
public class TimeLineController {
	
	@Autowired
	private TimelineBO timelineBO;
	
	@GetMapping("/timeline_view")
	public String timeLineView(Model model,
			HttpSession session) {
		// 비로그인 시에도 게시물이 보이기 위해 null값 허용
		Integer userId = (Integer)session.getAttribute("userId");
		List<CardView> cardList = timelineBO.generateCardList(userId);
		// db select 
		/*
		 * List<Post> postList = postBO.getPostList(); List<Comment> commentList =
		 * commentBO.getCommentList();
		 */
		/*
		 * model.addAttribute("commentList", commentList);
		 * model.addAttribute("postList", postList);
		 */
		
		model.addAttribute("cardList", cardList);
		model.addAttribute("view","timeline/timeline");
		return "template/layout";
	}
}
