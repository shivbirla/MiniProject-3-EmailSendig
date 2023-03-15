package com.nt.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nt.binding.ActivateAccount;
import com.nt.binding.Login;
import com.nt.binding.User;
import com.nt.service.UserDetailsServiceImpl;

@RestController
public class UserRestController {
 
	@Autowired
	private UserDetailsServiceImpl service;
	
	@PostMapping("/user")
	public ResponseEntity<String> userRegistration(@RequestBody User user){
		    boolean saveUser = service.saveUser(user);
		    
		    if(saveUser) {
			return new ResponseEntity<>("Registration Success",HttpStatus.CREATED);
		    }
		    else {
		    	return new ResponseEntity<>("Registration Faild",HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	
	@PostMapping("/active")
	public ResponseEntity<String>activateAccount(@RequestBody ActivateAccount acc){
		   boolean activeUserAcc = service.activeUserAcc(acc);
		   	   
		    if(activeUserAcc) {
			return new ResponseEntity<>("Account Activate Success",HttpStatus.OK);
		    }
		    else {
		    	return new ResponseEntity<>("Invalid Tempory password",HttpStatus.BAD_REQUEST);
		    }
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>>getAllUser(){
		   List<User> users = service.getAllUsers();
		   return new ResponseEntity<>(users,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Integer userId){
		      User userById = service.getUserById(userId);
		      return new ResponseEntity<>(userById,HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<String> deleteUserById( @PathVariable Integer userId){
		boolean removeUser = service.removeUser(userId);
		
		if(removeUser) {
			return new ResponseEntity<>("Deleted Success",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Faild to Delete",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/status/{userId}/{accStatus}")
	public ResponseEntity<String> statusChange(@PathVariable Integer userId, @PathVariable String accStatus){
		boolean changeAccStatus = service.changeAccStatus(userId, accStatus);
   
		if(changeAccStatus) {
			return new ResponseEntity<>("Account Status change Successfully",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Status Changed Faild",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Login login){
		   String status= service.Login(login);
		   
		   return new ResponseEntity<>(status,HttpStatus.OK);
	}
	
	@GetMapping("/forgotPwd/{email}")
	public ResponseEntity<String> forgetPwd(@PathVariable String email){
		  String resetPwd = service.resetPwd(email);
		  return new ResponseEntity<String>(resetPwd,HttpStatus.OK);
	}
}
