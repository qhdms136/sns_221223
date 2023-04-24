package com.sns.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.like.dao.LikeMapper;

@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;
	
	public void LikeToggle(int postId, int userId) {
		int result = likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
		if(result > 0) {	// 있으면 제거
			likeMapper.deleteByPostIdUserId(postId, userId);
		} else {	// 없으면 추가
			likeMapper.insertByPostIduserId(postId, userId);
		}
	}
	
	public boolean existLike(int postId, Integer userId) {
		// 비로그인
		if (userId == null) {
			return false;
		}
		
		// 로그인
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) > 0;
		
	}
	
	public int LikeCount(int postId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, null);
	}
}

