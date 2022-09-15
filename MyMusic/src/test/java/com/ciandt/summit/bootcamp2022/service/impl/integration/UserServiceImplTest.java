package com.ciandt.summit.bootcamp2022.service.impl.integration;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.exceptions.UserAlreadyExistsException;
import com.ciandt.summit.bootcamp2022.exceptions.UserDoesntExistException;
import com.ciandt.summit.bootcamp2022.repository.UserRepository;
import com.ciandt.summit.bootcamp2022.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ciandt.summit.bootcamp2022.config.Factory.USER_NAME;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_NICKNAME;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_NICKNAME_NONEXISTENT;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserRepository userRepository;
    private UserDto userDtonotExisted;
    private UserDto userDtoExisted;

    @BeforeEach
    void setUp() {
        userDtonotExisted = Factory.createUserDtoNonExistent();
        userDtoExisted = Factory.createUserDto();
    }

    @Test
    @DisplayName("When get user by nickname then return UserDto")
    void whenGetUserByNicknameThenReturnUserDto() {

        var user = userServiceImpl.getUserByNickname(USER_NICKNAME);

        assertEquals(USER_NICKNAME, user.getNickname());
    }

    @Test
    @DisplayName("When get user by nickname then return UserDoesntExistException")
    void whenGetUserByNicknameTheReturnUserDoesntExistException() {

        var response = assertThrows(UserDoesntExistException.class,
                () -> userServiceImpl.getUserByNickname(USER_NICKNAME_NONEXISTENT));

        assertEquals(UserDoesntExistException.class, response.getClass());
    }

    @Test
    @DisplayName("When save user then return UserDto")
    void WhenSaveUserThenReturnUserDto(){
        var userSaved = userServiceImpl.saveUser(userDtonotExisted);

        assertEquals(USER_NICKNAME_NONEXISTENT,  userSaved.getNickname());
        assertEquals(USER_NAME, userSaved.getName());
        assertEquals(USER_TYPE, userSaved.getUserType());
        userRepository.deleteById(userSaved.getId());
    }

    @Test
    @DisplayName("When save user that already exists then return UserAlreadyExistsException")
    void WhenSaveUserThatAlreadyExistThenReturnUserAlreadyExistsException(){
        var response = assertThrows(UserAlreadyExistsException.class,
                () -> userServiceImpl.saveUser(userDtoExisted));

        assertEquals(UserAlreadyExistsException.class, response.getClass());
    }
}