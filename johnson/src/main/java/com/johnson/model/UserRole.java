package com.johnson.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserRole implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7802201702566780081L;

	@Id
	@GeneratedValue
	private long userRoleId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private EntityUser user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;

	public UserRole() {
	}

	public UserRole(long userRoleId, EntityUser user, Role role) {
		this.userRoleId = userRoleId;
		this.user = user;
		this.role = role;
	}

	public UserRole(EntityUser user, Role role) {
		this.user = user;
		this.role = role;
	}

	public long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public EntityUser getUser() {
		return user;
	}

	public void setAppUser(EntityUser user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}