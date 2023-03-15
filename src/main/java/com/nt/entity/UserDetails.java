package com.nt.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;

import lombok.Data;

@Table(name = "USER_DATAILS")
@Entity
@Data
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String fullName;
	private String email;
	private Long mobileNo;
	private String gender;
	private LocalDate dob;
	private Long ssn;
	private String password;
	private String accStatus;
	private String createdBy;
	
	@Column(updatable = false)
	private LocalDate createdDate;
	
	private String updatedBy;
	@Column(insertable = false)
	private LocalDate updateDate;
	
}
