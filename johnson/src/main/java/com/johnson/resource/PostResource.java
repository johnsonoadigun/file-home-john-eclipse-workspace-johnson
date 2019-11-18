package com.johnson.resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnson.model.Comment;
import com.johnson.model.Post;
import com.johnson.model.EntityUser;
import com.johnson.service.AccountService;
import com.johnson.service.CommentService;
import com.johnson.service.PostService;

@RestController
@RequestMapping("/post")
public class PostResource {
	
	private String postImageName;
	
	
	@Autowired
	private PostService postService;
	
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CommentService commentService;
	
	
	@GetMapping("/list")
	public List<Post> getPostList(){
		return postService.postList();
	}
	
	@GetMapping("/getPostById/{postId}")
	public Post getOnePostById(@PathVariable("postd")Long id) {
		return postService.getPostById(id);
		
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> savePost(@RequestBody HashMap<String,String> request){
		
	String username = request.get("username");
	EntityUser user = accountService.findByUsername(username);
	
	if(user ==null) {
		return new ResponseEntity<>("user not foun", HttpStatus.NOT_FOUND);
		
    	}
		postImageName = RandomStringUtils.randomAlphanumeric(10);
		try {
			Post post = postService.savePost(user, request, postImageName);
			return new ResponseEntity<>(post, HttpStatus.CREATED);
		}catch(Exception e ) {
			return new ResponseEntity<>("error occured", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete{id}")
	public ResponseEntity<?> deletePost(@PathVariable("id" )Long id){
		Post post = postService.getPostById(id);
		if(post==null) {
			return new ResponseEntity<>("post not found", HttpStatus.NOT_FOUND);
		}
		try {
			postService.deletePost(post);
			return new ResponseEntity<>(post, HttpStatus.OK);
			
		}catch(Exception e) {
		
			return new ResponseEntity<>("an error occured", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/like")
	public ResponseEntity<String> likePost(@RequestBody HashMap<String,String> request){
		String postId = request.get("postId");
		Post post = postService.getPostById(Long.parseLong(postId));
		if(post==null) {
			return new ResponseEntity<>("Post Not Found", HttpStatus.NOT_FOUND);
		}
		String username= request.get("username");
		EntityUser user = accountService.findByUsername(username);
		if(user==null) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		try {
			post.setLikes(1);
			user.setLikedPost(post);
			accountService.simpleSaveUser(user);
			return new ResponseEntity<>("post was liked",HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>("cant likes the post", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/unlike")
	public ResponseEntity<String> unlikePost(@RequestBody HashMap<String, String> request){
		String postId = request.get("postId");
		Post post = postService.getPostById(Long.parseLong(postId));
		if(post==null) {
			return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
		}
		String username = request.get("username");
		EntityUser user = accountService.findByUsername(username);
		if(user==null) {
			return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
		}
		try {
			post.setLikes(-1);
			user.getLikedPost().remove(post);
			accountService.simpleSaveUser(user);
			return new ResponseEntity<>("post was liked", HttpStatus.OK);
		}catch(Exception e ) {
			return new ResponseEntity<>("cant like the post", HttpStatus.OK);
		}
	}
	
	
	@PostMapping("/coment/add")
	public ResponseEntity<?> addComment(@RequestBody HashMap<String, String> request){
		String postId = request.get("postId");
		Post post = postService.getPostById(Long.parseLong(postId));
		if(post==null) {
			return new ResponseEntity<>("Post not found",HttpStatus.NOT_FOUND);
		}
		String username = request.get("username");
		EntityUser user = accountService.findByUsername(username);
		if(user==null) {
			return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
		}
		String content = request.get("content");
		try {
			Comment comment = new Comment();
			comment.setContent(content);
			comment.setUserName(username);
			comment.setPostedDate(new Date());
			post.setCommentList(comment);
			commentService.saveComment(comment);
			return new ResponseEntity<>(comment, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("comment not added", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("/photo/upload")
	public ResponseEntity<String> fileUpload(HttpServletRequest request){
		try {
			postService.savePostImage(request,  postImageName);
			return new ResponseEntity<>("picture saved", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("picture not saved", HttpStatus.BAD_REQUEST);
		}
	}
	
}
