package com.johnson.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.johnson.Repository.PostRepository;
import com.johnson.model.Post;
import com.johnson.model.EntityUser;
import com.johnson.service.PostService;
import com.johnson.utility.Constants;



@Service
@Transactional
public class PostServiceImpl implements PostService{
	
	
	@Autowired
	private PostRepository postRepository;
	
	

	@Override
	public Post savePost(EntityUser user, HashMap<String, String> request, String postImageName) {
		String caption = request.get("caption");
		String location = request.get("location");
		Post newPost = new Post();
		newPost.setCaption(caption);
		newPost.setLocation(location);
		newPost.setUsername(user.getName());
		newPost.setPostedDate(new Date());
		newPost.setUserImageId(user.getId());
		user.setPost(newPost);
		postRepository.save(newPost);
		return newPost;
	}

	@Override
	public List<Post> postList() {
		
		return postRepository.findAll();
	}

	@Override
	public Post getPostById(Long id) {
		// TODO Auto-generated method stub
		return postRepository.findPostById(id);
	}


	@Override
	public Post deletePost(Post post) {
		try {
			Files.deleteIfExists(Paths.get(Constants.POST_FOLDER + "/" + post.getName() +  ".png"));
			postRepository.deleteById(post.getId());
			return post;
		}
			catch(Exception e){
				
			
		}
		return null;
	}

	@Override
	public String savePostImage(HttpServletRequest request, String fileNmae) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> it = multipartRequest.getFileNames();
		MultipartFile multipartFile = multipartRequest.getFile(it.next());
		try {
			byte[] bytes = multipartFile.getBytes();
			Path path = Paths.get(Constants.POST_FOLDER + fileNmae +".png");
			Files.write(path, bytes, StandardOpenOption.CREATE);
			
			
		}catch(Exception e) {
				
			return "error occured. Photo not saved";
		}
		return "Saved Successfully";
	}

	@Override
	public List<Post> findPostByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

}
