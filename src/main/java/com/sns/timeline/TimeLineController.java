package com.sns.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.model.CardView;

@RequestMapping("/timeline")
@Controller
public class TimeLineController {
	
	@Autowired
	private TimelineBO timelineBO;
	
	@GetMapping("/timeline_view")
	public String timeLineView(Model model) {
		
		// db select 
		/*
		 * List<Post> postList = postBO.getPostList(); List<Comment> commentList =
		 * commentBO.getCommentList();
		 */
		/*
		 * model.addAttribute("commentList", commentList);
		 * model.addAttribute("postList", postList);
		 */
		List<CardView> cardList = timelineBO.generateCardList();
		
		model.addAttribute("cardList", cardList);
		model.addAttribute("view","timeline/timeline");
		return "template/layout";
	}
}
