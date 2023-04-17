package com.sns.post.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface PostMapper {
	
	public List<Map<String, Object>> selectPostList();
	
	public int insertPost(
			@Param("userId") int userId,
			@Param("loginId") String loginId,
			@Param("content") String content,
			@Param("file") MultipartFile file);
}
