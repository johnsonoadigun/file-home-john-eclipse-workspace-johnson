package com.johnson.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class Post implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1808834245915725398L;
	@Id
	@GeneratedValue
	@Column(updatable=false, nullable=false)
	private Long id;
	private String name;
	private String username;
	
	
	@Column(columnDefinition="text")
	private String caption;
	private String location;
	private int likes;

	private Date postedDate;
	private Long userImageId;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="post_id")
	private List<Comment> commentList;

	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Post(Long id, String name, String caption, String location, int likes, Date postedDate, Long userImageId,
			List<Comment> commentList) {
		super();
		this.id = id;
		this.name = name;
		this.caption = caption;
		this.location = location;
		this.likes = likes;
		this.postedDate = postedDate;
		this.userImageId = userImageId;
		this.commentList = commentList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public Long getUserImageId() {
		return userImageId;
	}

	public void setUserImageId(Long userImageId) {
		this.userImageId = userImageId;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(Comment comment) {
		this.commentList.add(comment);
	}

}
