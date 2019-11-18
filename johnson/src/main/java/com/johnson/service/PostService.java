package com.johnson.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.johnson.model.Post;
import com.johnson.model.EntityUser;

public interface PostService {
	
	public Post savePost(EntityUser user, HashMap<String, String> request, String postImageName);
	
	public List<Post> postList();
	
	
	public Post getPostById(Long id);
	
	
	public List<Post>  findPostByUserName(String userName);
	
	
	public Post deletePost(Post post);
	
	
	public String savePostImage(HttpServletRequest request, String fileNmae);
	

}
