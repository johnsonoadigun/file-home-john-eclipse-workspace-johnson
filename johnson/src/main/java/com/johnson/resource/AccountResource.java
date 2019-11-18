package com.johnson.resource;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.johnson.model.EntityUser;
import com.johnson.service.AccountService;

@RestController
@RequestMapping("/user")
public class AccountResource {

	private Long userImageId;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AccountService accountService;

	@GetMapping("/list")
	public ResponseEntity<?> getUsersList() {
		List<EntityUser> users = accountService.userList();
		if (users.isEmpty()) {
			return new ResponseEntity<>("no users found", HttpStatus.OK);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{username}")
	public ResponseEntity<?> getUserInfo(@PathVariable("username") String username) {
		EntityUser user = accountService.findByUsername(username);
		if (user == null) {
			return new ResponseEntity<>("No user Found", HttpStatus.OK);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("findByUsername/{username}")
	public ResponseEntity<?> getUsersListByUsername(@PathVariable("username") String username) {
		List<EntityUser> users = accountService.getUsersListByUsername(username);
		if (users.isEmpty()) {
			return new ResponseEntity<>("No users found", HttpStatus.OK);
		}

		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@PostMapping("register")
	public ResponseEntity<?> register(@RequestBody HashMap<String, String> request){
		String username = request.get("username");
		if(accountService.findByUsername(username)!=null) {
			return new ResponseEntity<>("Username Exist", HttpStatus.CONFLICT);
		}
		
		String email = request.get("email");
		if(accountService.findByEmail(email) != null) {
			return new ResponseEntity<>("emailExists", HttpStatus.CONFLICT);
		}
		
		String name = request.get("name");
		try {
			EntityUser user = accountService.saveUser(name, username, email);
			return new ResponseEntity<>(user, HttpStatus.OK);
			
		}catch(Exception e){
			return new ResponseEntity<>("An Erroe Occured", HttpStatus.BAD_REQUEST);
			
		}
		
		
	}
	
	
	@PostMapping("/update")
	public ResponseEntity<?> updateProfile(@RequestBody HashMap<String, String> request){
		String id = request.get("id");
		EntityUser user = accountService.findUserById(Long.parseLong(id));
		
		if(user == null) {
			return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
		}try {
			accountService.updateUser(user, request);
			userImageId = user.getId();
			return new ResponseEntity<>(user,HttpStatus.OK);
			
			
		}catch(Exception e){
			return new ResponseEntity<>("userNotFound", HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@PostMapping("/photo/upload")
	public ResponseEntity<String> fileUpload(@RequestParam("image") MultipartFile multipartFile){ 
		try {
			accountService.saveUserImage(multipartFile, userImageId);
			return new ResponseEntity<>("User picture saved!", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("User picture not saved", HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody HashMap<String, String> request){
		String username = request.get("username");
		EntityUser user = accountService.findByUsername(username);
		if(user ==null) {
			return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
		}
		String currentPassword = request.get("currentPassword");
		String newPassword = request.get("newPassword");
		String confirmPassword = request.get("confirmPassword");
		if(!newPassword.equals(confirmPassword)) {
			return new ResponseEntity<>("PasswordNotMatched", HttpStatus.BAD_REQUEST);
		}
		
		String userPassword = user.getPassword();
		try {
			if(newPassword !=null && !newPassword.isEmpty() && !StringUtils.isEmpty(newPassword)) {
				if(bCryptPasswordEncoder.matches(currentPassword, userPassword)) {
					accountService.updateUserPassword(user, newPassword);
				}
				}else {
					return new ResponseEntity<>("In correctcurrent password", HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>("Password successfully changed", HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>("error occured", HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@GetMapping("/resetPassword/{email}")
	public ResponseEntity<?> resetPassword(@PathVariable("email")String email){
		EntityUser user = accountService.findByEmail(email);
		if(user==null) {
			return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
		}
		accountService.resetPassword(user);
		return new ResponseEntity<>("Email Sent", HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	

}
