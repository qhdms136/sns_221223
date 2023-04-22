package com.sns.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.like.dao.LikeMapper;

@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;
	
	public void LikeToggle(int postId, int userId) {
		int result = likeMapper.selectLikeByPostIdUserId(postId, userId);
		if(result > 0) {	// 있으면 제거
			likeMapper.deleteByPostIdUserId(postId, userId);
		} else {	// 없으면 추가
			likeMapper.insertByPostIduserId(postId, userId);
		}
	}
	
	public int LikeCount(int postId) {
		return likeMapper.selectLikeCount(postId);
	}
}

