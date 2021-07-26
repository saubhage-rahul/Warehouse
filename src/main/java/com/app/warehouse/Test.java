package com.app.warehouse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
	public static void main(String[] args) {

		String pwd = "SAMPLE";

		BCryptPasswordEncoder pwdEnc = new BCryptPasswordEncoder();
		String encPwd = pwdEnc.encode(pwd);
		System.out.println(encPwd);

	}
}
