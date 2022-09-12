package com.ciandt.summit.bootcamp2022.service.impl;

import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.exceptions.UserAlreadyExistsException;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.model.User;
import com.ciandt.summit.bootcamp2022.repository.PlaylistsRepository;
import com.ciandt.summit.bootcamp2022.repository.UserRepository;
import com.ciandt.summit.bootcamp2022.service.UserService;

import static com.google.common.base.Preconditions.checkNotNull;

public class UserServiceImpl implements UserService {

    private PlaylistsRepository playlistsRepository;
    private UserRepository userRepository;

    public UserDto getUserByNickname(String nickname){
        checkNotNull(nickname, "Nickname cannot be null");

        var user = userRepository.findByNickname(nickname);

        if(user == null)
            throw new UserAlreadyExistsException();

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .userType(user.getUserType())
                .playlistId(PlaylistDto.builder()
                        .id(user.getPlaylistId().getId())
                        .musics(user.getPlaylistId().getMusics())
                        .build())
                .build();
    }
    public UserDto saveUser(UserDto userDto){
        checkNotNull(userDto, "User is null");
        checkNotNull(userDto.getName(), "Payload body incorrect: name is null");
        checkNotNull(userDto.getNickname(), "Payload body incorrect: nickname is null");
        checkNotNull(userDto.getUserType(), "Payload body incorrect: user type is null");

        getUserByNickname(userDto.getNickname());

        var playlist = playlistsRepository.save(new Playlist());

        userDto.setPlaylistId(PlaylistDto.builder()
                .id(playlist.getId())
                .build());

        var userSave = userRepository.save(
                User.builder()
                       .name(userDto.getName())
                       .nickname(userDto.getNickname())
                       .userType(userDto.getUserType())
                       .playlistId(playlist)
                       .build()
        );

        return UserDto.builder()
                .id(userSave.getId())
                .name(userSave.getName())
                .nickname(userSave.getNickname())
                .userType(userSave.getUserType())
                .playlistId(PlaylistDto.builder()
                        .id(userSave.getPlaylistId().getId())
                        .build())
                .build();
    }
}
