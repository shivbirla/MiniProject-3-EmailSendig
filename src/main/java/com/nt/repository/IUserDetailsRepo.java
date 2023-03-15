package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nt.entity.UserDetails;

public interface IUserDetailsRepo extends JpaRepository<UserDetails, Integer> {
	

     public UserDetails findByEmailAndPassword(String email,String password);
     public UserDetails findByEmail(String email);
}
