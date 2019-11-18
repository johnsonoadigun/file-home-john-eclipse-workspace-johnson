package com.johnson.service;

import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;
import org.springframework.web.multipart.MultipartFile;

import com.johnson.model.Role;
import com.johnson.model.EntityUser;

public interface AccountService {
	
	public EntityUser saveUser(String name, String username, String email) throws MailException, MessagingException;

	public EntityUser findByUsername(String username);

	public EntityUser findByEmail(String email);

	public List<EntityUser> userList();

	public Role findUserRoleByName(String string);

	public Role saveRole(Role role);

	public void updateUserPassword(EntityUser user, String newpassword) throws MailException, MessagingException;
	
	public EntityUser updateUser(EntityUser user, HashMap<String, String> request);

	public EntityUser simpleSaveUser(EntityUser user);

	public EntityUser findUserById(Long id);
	
	public void deleteUser(EntityUser user);

	public void resetPassword(EntityUser user);

	public List<EntityUser> getUsersListByUsername(String username);

	public String saveUserImage(MultipartFile multipartFile, Long userImageId);


}