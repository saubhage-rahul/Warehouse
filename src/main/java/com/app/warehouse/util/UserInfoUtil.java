package com.app.warehouse.util;

import java.util.Set;
import java.util.stream.Collectors;

import com.app.warehouse.model.Role;

public class UserInfoUtil {

	public static Set<String> getRolesAsString(Set<Role> roles) {
		return roles.stream().map(role -> role.getRole().name()).collect(Collectors.toSet());
	}

}
