package com.johnson.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.johnson.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	

	
	public List<Post> findAll();

	
	public List<Post> findPostByUsername( String username);

	
	public Post findPostById( Long id);

	
	public void deletePostById( Long id);
}