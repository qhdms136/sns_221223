package com.sns.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.dao.CommentMapper;
import com.sns.comment.model.Comment;
import com.sns.comment.model.CommentView;
import com.sns.user.bo.UserBO;
import com.sns.user.model.User;

@Service
public class CommentBO {
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private UserBO userBO;
	
	public int addComment(int userId, int postId, String content) {
		return commentMapper.insertComment(userId, postId, content);
	}
	
	// input : postId
	// output : 가공된 댓글 리스트
	public List<CommentView> generateCommentList(int postId){
		List<CommentView> commentViewList = new ArrayList<>();
		
		// 글에 해당하는 댓글들
		List<Comment> commentList = commentMapper.selectCommentListByPostId(postId);
		
		// 반복문 Comment => CommentView =>> 결과 리스트에 담는다.
		for(Comment comment : commentList) {
			CommentView commentView = new CommentView();
			
			// 댓글 하나
			commentView.setComment(comment);
			
			// 댓글쓴이
			User user = userBO.getUserById(comment.getUserId());
			commentView.setUser(user);
			
			// !!!!! 새로 만든 댓글 정보를 list에 담는다.
			commentViewList.add(commentView);
		}
		return commentViewList;
	}
}
