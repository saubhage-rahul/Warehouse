package com.app.warehouse;

import java.util.Random;

public class Test {
	public static void main(String[] args) {

		Random rob = new Random();
		// 0---999999
		int nextInt = rob.nextInt(999999);
		System.out.println(nextInt);
		String otp = String.format("%06d", nextInt);
		System.out.println(otp);
	}
}
