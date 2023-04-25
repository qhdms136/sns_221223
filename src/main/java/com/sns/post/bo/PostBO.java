package com.sns.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sns.comment.bo.CommentBO;
import com.sns.common.FileManagerService;
import com.sns.like.bo.LikeBO;
import com.sns.post.dao.PostMapper;
import com.sns.post.model.Post;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	@Autowired
	private LikeBO likeBO;
	
	@Autowired
	private CommentBO commentBO;
	
	public int addPost(int userId, String userLoginId, String content, MultipartFile file) {
		String imagePath = null;
		if (file != null) {
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		
		return postMapper.insertPost(userId, content, imagePath);
	}
	
	
	 public List<Post> getPostList(){ 
		 return postMapper.selectPostList();
	}
	 
	 public Post getPostByPostIdUserId(int postId, int userId) {
		 return postMapper.selectPostByPostIdUserId(postId, userId);
	 }
	 
	 // 게시물 삭제	
	 public int deletePostByPostIdUserId(int postId, int userId) {
		 Post post = getPostByPostIdUserId(postId, userId);
		 // db 삭제
		 // 좋아요, 댓글, 사진 삭제(각각의 BO를 불러서 삭제)
		 // 이미지 삭제
		 fileManager.deleteFile(post.getImagePath());
		 
		 // post 좋아요 삭제
		 likeBO.likeDeteByPostId(postId);
		 
		 // post 댓글 삭제
		 commentBO.deleteCommentByPostId(postId);
		 
		 return postMapper.deletePostByPostIdUserId(postId, userId);
	 }
		 
}
