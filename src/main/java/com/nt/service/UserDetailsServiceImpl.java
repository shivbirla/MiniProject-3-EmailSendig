package com.nt.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.nt.binding.ActivateAccount;
import com.nt.binding.User;
import com.nt.entity.UserDetails;
import com.nt.repository.IUserDetailsRepo;
import com.nt.utils.EmailUtils;
import com.nt.binding.Login;

@Service
public class UserDetailsServiceImpl implements IUserDetailsService {

	@Autowired
	private IUserDetailsRepo repo;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public boolean saveUser(User user) {
		UserDetails destination = new UserDetails();
		BeanUtils.copyProperties(user, destination);// copy data from binding obj to entity object 
		destination.setPassword(generatedRandomPwd());
		destination.setAccStatus("In-Active");
		UserDetails save = repo.save(destination);
		
		String subject = "Your Registration Success";
	    String fileName = "REG-EMAIL-BODY.txt";
       String body = readRegEmailBody(destination.getFullName(),destination.getPassword(), fileName);
      
		// TODO: Send Registration to Email
		emailUtils.sendEmail(user.getEmail(), subject, body);
		return save.getUserId() != null;
	}

	@Override
	public boolean activeUserAcc(ActivateAccount status) {
		UserDetails entity = new UserDetails();
		entity.setEmail(status.getEmail());
		entity.setPassword(status.getTemPwd());

		//select * from user where email=? and temp=?; 
		Example<UserDetails> of = Example.of(entity);
		List<UserDetails> findAll = repo.findAll(of);

		if (findAll.isEmpty()) {
			return false;
		} else {
			UserDetails details = findAll.get(0);
			details.setPassword(status.getNewPass());
			details.setAccStatus("Active");
			repo.save(details);
			return true;
		}
	}

	@Override
	public User getUserById(Integer userId) {
		Optional<UserDetails> findById = repo.findById(userId);
		if (findById.isPresent()) {
			User user = new User();
			UserDetails users = findById.get();
			BeanUtils.copyProperties(users, user);
			return user;
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		List<UserDetails> findAll = repo.findAll();
		List<User> users = new ArrayList<>();
		for (UserDetails entity : findAll) {
			User user = new User();
			BeanUtils.copyProperties(entity, user);// source is entity class and target is binding class
			users.add(user);
		}
		return users;
	}

	@Override
	public boolean removeUser(Integer userId) {
		try {
			repo.deleteById(userId);
			return true;
		} catch (Exception e) {
			e.getMessage();
		}
		return false;
	}

	@Override
	public boolean changeAccStatus(Integer userId, String AccStatus) {
		Optional<UserDetails> findById = repo.findById(userId);

		if (findById.isPresent()) {
			UserDetails Details = findById.get();
			Details.setAccStatus(AccStatus);
			return true;
		}
		return false;
	}

	@Override
	public String Login(Login login) {
		/*
    UserDetails entity = new UserDetails();
    entity.setEmail(login.getEmail());
    entity.setPassword(login.getPwd());
     
    //select * from User_Details where email=? and pwd=?;
	Example<UserDetails> of = Example.of(entity);
	 List<UserDetails> findAll = repo.findAll();
	 */   
//		or
		UserDetails findAll= repo.findByEmailAndPassword(login.getEmail(), login.getPwd());
	 
	 if(findAll==null) {
		 return "Invailid Email or password";
	 }else {
//		 UserDetails userDetails = findAll.get(0);
		 if(findAll.getAccStatus().equals("Active")) {
			 return "Success";
		 }
		 else {
			return "Account not activated";
		}
	 }
		
	}//login()

	@Override
	public String resetPwd(String email) {
		UserDetails findByEmail = repo.findByEmail(email);
		
		if(findByEmail==null) {
			return "Invalid Email Address";
		}else {
			
			//TODO  :  send password to Email
			
			String subject = "Forget Password so Recover";
			String fileName = "RECOVER-PWD-BODY.txt";
			String body = readRegEmailBody(findByEmail.getFullName(), findByEmail.getPassword(), fileName);
			boolean sendEmail = emailUtils.sendEmail(email, subject, body);
			
			if(sendEmail) {
				return "Password Sent to Your Register Email ";
			}
			
		}
		return "Mail not send some problem";
	}

	private String generatedRandomPwd() {

		// create a string of uppercase and lowercase characters and numbers
		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";

		// combine all strings
		String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

		// create random string builder
		StringBuilder sb = new StringBuilder();

		// create an object of Random class
		Random random = new Random();

		// specify length of random string
		int length = 6;

		for (int i = 0; i < length; i++) {

			// generate random index number
			int index = random.nextInt(alphaNumeric.length());

			// get character specified by index
			// from the string
			char randomChar = alphaNumeric.charAt(index);

			// append the character to string builder
			sb.append(randomChar);
		}

		return sb.toString();

	}
	public String readRegEmailBody(String fullname,String Pwd,String fileName) {
		String url = "";
		String mailBody = null;
		try {	
		         FileReader fr =  new FileReader(fileName);
		        BufferedReader br  = new BufferedReader(fr);
		        
		        StringBuffer sb =  new StringBuffer();
		        
		        String line = br.readLine();
		        
		        while(line!=null) {
		        	//process the data
		        	sb.append(line);
		        	line = br.readLine();
		        }
		        br.close();
		        mailBody = sb.toString();
		        
		        mailBody = mailBody.replace("{FULLNAME}", fullname);
		        mailBody = mailBody.replace("{TEMP-PWD}", Pwd);
		        mailBody = mailBody.replace("{URL}", url );
		        mailBody = mailBody.replace("PWD", Pwd);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;
	}

}
