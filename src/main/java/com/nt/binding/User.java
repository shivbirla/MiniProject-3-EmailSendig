package com.nt.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {
	
              private String fullName;
              private String email;
     
              private Long mobileNo;
              private String gender;
              private LocalDate dob;
              private Long ssn;
              private String createdBy;
              private String updatedBy;
              private LocalDate createdDate;
              private LocalDate updatedDate;
              
              
              
}
