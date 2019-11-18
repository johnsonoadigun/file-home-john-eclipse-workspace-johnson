package com.johnson.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.johnson.Repository.RoleRepository;
import com.johnson.Repository.UserRepository;
import com.johnson.model.Role;
import com.johnson.model.EntityUser;
import com.johnson.model.UserRole;
import com.johnson.service.AccountService;
import com.johnson.utility.Constants;
import com.johnson.utility.EmailConstructor;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountService accountService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository appUserRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private EmailConstructor emailConstructor;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Transactional
	public EntityUser saveUser(String name, String username, String email) throws MailException, MessagingException {
		String password = RandomStringUtils.randomAlphanumeric(10);
		String encryptedPassword = bCryptPasswordEncoder.encode(password);
		EntityUser user = new EntityUser();
		user.setPassword(encryptedPassword);
		user.setName(name);
		user.setUsername(username);
		user.setEmail(email);
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, accountService.findUserRoleByName("USER")));
		user.setUserRoles(userRoles);
		appUserRepo.save(user);
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(Constants.TEMP_USER.toPath());
			String fileName = user.getId() + ".png";
			Path path = Paths.get(Constants.USER_FOLDER + fileName);
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mailSender.send(emailConstructor.constructNewUserEmail(user, password));
		return user;
	}

	@Override
	public void updateUserPassword(EntityUser user, String newpassword) throws MailException, MessagingException {
		String encryptedPassword = bCryptPasswordEncoder.encode(newpassword);
		user.setPassword(encryptedPassword);
		appUserRepo.save(user);
		mailSender.send(emailConstructor.constructResetPasswordEmail(user, newpassword));
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepo.save(role);
	}

	

	@Override
	public List<EntityUser> userList() {
		return appUserRepo.findAll();
	}

	@Override
	public Role findUserRoleByName(String name) {
		return roleRepo.findRoleByName(name);
	}



	@Override
	public List<EntityUser> getUsersListByUsername(String username) {
		return appUserRepo.findByUsernameContaining(username);
	}

	@Override
	public String saveUserImage(MultipartFile multipartFile, Long userImageId) {
		/*
		 * MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)
		 * request; Iterator<String> it = multipartRequest.getFileNames(); MultipartFile
		 * multipartFile = multipartRequest.getFile(it.next());
		 */
		byte[] bytes;
		try {
			Files.deleteIfExists(Paths.get(Constants.USER_FOLDER + "/" + userImageId + ".png"));
			bytes = multipartFile.getBytes();
			Path path = Paths.get(Constants.USER_FOLDER + userImageId + ".png");
			Files.write(path, bytes);
			return "User picture saved to server";
		} catch (IOException e) {
			return "User picture Saved";
		}
	}



	@Override
	public EntityUser findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityUser findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public EntityUser updateUser(EntityUser user, HashMap<String, String> request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityUser simpleSaveUser(EntityUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityUser findUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(EntityUser appUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPassword(EntityUser user) {
		// TODO Auto-generated method stub
		
	}

}