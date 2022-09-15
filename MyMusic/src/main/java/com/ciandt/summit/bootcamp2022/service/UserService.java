package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.dto.UserDto;

public interface UserService {

    public UserDto getUserByNickname(String nickname);

    public UserDto saveUser(UserDto user);
}
