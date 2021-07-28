package com.app.warehouse.util;

import java.util.Random;
import java.util.UUID;

public interface MyAppUtil {

	public static String generatePassword() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
	}

	public static String generateOTP() {
		return String.format("%06d", new Random().nextInt(666666));
	}
}
