package com.sns.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sns.post.dao.PostMapper;

@Controller
public class TestController {
	
	@Autowired
	PostMapper postMapper;
	
	@ResponseBody
	@RequestMapping("/test1")
	public String test1() {
		return "Hello world";
	}
	
	@ResponseBody
	@RequestMapping("/test2")
	public Map<String, Object> test2(){
		Map<String, Object> map = new HashMap<>();
		map.put("q", 1);
		map.put("w", 2);
		map.put("e", 3);
		map.put("r", 4);
		return map;
	}
	
	@RequestMapping("/test3")
	public String  test3(){
		return "test/test";
	}
	
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("/test4") public List<Map<String, Object>> test4(){ return
	 * postMapper.selectPostList(); }
	 */
	
}
