package com.sns.like.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeMapper {
	// public int selectLikeByPostIdUserId(
	// 		@Param("postId") int postId,
	// 		@Param("userId") int userId);
	
	public void insertByPostIduserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	public void deleteByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
		
	// public int selectLikeCount(int postId);
	
	public int selectLikeCountByPostIdOrUserId(
			@Param("postId") int postId,
			@Param("userId") Integer userId);
	
	public void likeDeteByPostId(int postId);
}
