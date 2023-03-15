package com.nt.service;

import java.util.List;

import com.nt.binding.ActivateAccount;
import com.nt.binding.Login;
import com.nt.binding.User;
import com.nt.entity.UserDetails;

public interface IUserDetailsService {

	public boolean saveUser(User user);
	public boolean activeUserAcc(ActivateAccount status);
	public User getUserById(Integer userId);
	public List<User> getAllUsers();
	public boolean removeUser(Integer userId);
	public boolean changeAccStatus(Integer userId,String AccStatus); 
	public String Login(Login login);
	public String resetPwd(String email);

}
