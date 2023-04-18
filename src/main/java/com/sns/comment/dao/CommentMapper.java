package com.sns.comment.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentMapper {
	public int insertComment(
			@Param("content") String content);
}
