package com.sns.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sns.post.bo.PostBO;
import com.sns.post.model.Post;

@RequestMapping("/timeline")
@Controller
public class TimeLineController {
	
	@Autowired
	private PostBO postBO;
	
	@GetMapping("/timeline_view")
	public String timeLineView(Model model) {
		
		// db select 

		List<Post> postList = postBO.getPostList();
		model.addAttribute("postList", postList);
		model.addAttribute("view","timeline/timeline");
		return "template/layout";
	}
}
