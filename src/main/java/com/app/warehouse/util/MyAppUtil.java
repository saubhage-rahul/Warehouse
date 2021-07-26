package com.app.warehouse.util;

import java.util.UUID;

public interface MyAppUtil {

	public static String generatePassword() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
	}
}
