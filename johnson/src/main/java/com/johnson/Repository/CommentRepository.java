package com.johnson.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnson.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{

}
