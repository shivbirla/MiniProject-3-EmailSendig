package com.nt.binding;

import lombok.Data;

@Data
public class ActivateAccount {

	private String email;
	private String temPwd;
	private String newPass;
	private String confPwd;
	
}

