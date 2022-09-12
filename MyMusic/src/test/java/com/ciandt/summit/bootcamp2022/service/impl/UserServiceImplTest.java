package com.ciandt.summit.bootcamp2022.service.impl;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.exceptions.UserAlreadyExistsException;
import com.ciandt.summit.bootcamp2022.exceptions.UserDoesntExistException;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.model.User;
import com.ciandt.summit.bootcamp2022.repository.PlaylistsRepository;
import com.ciandt.summit.bootcamp2022.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ciandt.summit.bootcamp2022.config.Factory.PLAYLIST_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_NAME;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_NICKNAME;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest{
    @Mock
    private PlaylistsRepository playlistsRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User user;
    private UserDto userDto;
    private Playlist playlist;

    @BeforeEach
    void setUp() {
        user = Factory.createUser();
        userDto = Factory.createUserDto();
        playlist = Factory.createPlaylist();
    }

    @Test
    @DisplayName("When save new user then return UserDto")
    void whenSaveNewUserThenReturnUserDto(){
        when(userRepository.findByNickname(anyString())).thenReturn(null);

        when(playlistsRepository.save(any())).thenReturn(playlist);

        when(userRepository.save(any())).thenReturn(user);

        var response = userServiceImpl.saveUser(userDto);

        assertNotNull(response);
        assertEquals(UserDto.class, response.getClass());
        assertEquals(USER_ID, response.getId());
        verify(userRepository, times(1)).save(any());
        verify(playlistsRepository, times(1)).save(any());
        verify(userRepository, times(1)).findByNickname(anyString());
    }

    @Test
    @DisplayName("When get user by nickname then return UserDto")
    void whenGetUserByNicknameThenReturnUserDto() {
        when(userRepository.findByNickname(anyString())).thenReturn(user);

        var response = userServiceImpl.getUserByNickname(USER_NICKNAME);

        assertNotNull(response);
        assertEquals(UserDto.class, response.getClass());
        assertEquals(USER_ID, response.getId());
        assertEquals(USER_NAME, response.getName());
        assertEquals(USER_NICKNAME, response.getNickname());
        assertEquals(USER_TYPE, response.getUserType());
        assertEquals(PLAYLIST_ID, response.getPlaylistId().getId());
        verify(userRepository, times(1)).findByNickname(anyString());
    }

    @Test
    @DisplayName("When get user by nickname the return UserDoesntExistException")
    void whenGetUserByNicknameTheReturnUserDoesntExistException() {
        when(userRepository.findByNickname(anyString())).thenReturn(null);

        var response = assertThrows(UserDoesntExistException.class,
                () -> userServiceImpl.getUserByNickname(USER_NICKNAME));

        assertNotNull(response);
        assertEquals(UserDoesntExistException.class, response.getClass());
        assertEquals(UserDoesntExistException.MESSAGE, response.getMessage());
        verify(userRepository, times(1)).findByNickname(anyString());
    }

    @Test
    @DisplayName("When verify user by nickname the return UserAlreadyExistsException")
    void whenVerifyUserByNicknameThenReturnUserAlreadyExistsException() {
        when(userRepository.findByNickname(anyString())).thenReturn(user);

        var response = assertThrows(UserAlreadyExistsException.class,
                () -> userServiceImpl.verifyUserByNickname(USER_NICKNAME));

        assertNotNull(response);
        assertEquals(UserAlreadyExistsException.class, response.getClass());
        assertEquals(UserAlreadyExistsException.MESSAGE, response.getMessage());
        verify(userRepository, times(1)).findByNickname(anyString());
    }
}