/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.urt.web.data;

import com.urt.dto.UserDto;
import com.urt.modules.test.data.RandomData;

public class UserData {

	public static UserDto randomNewUser() {
		UserDto user = new UserDto(1L);
		user.setLoginName(RandomData.randomName("user"));
		user.setName(RandomData.randomName("User"));
		user.setPlainPassword(RandomData.randomName("password"));

		return user;
	}
}
