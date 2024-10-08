package com.bikkadIt.ElectronicStore.service;

import com.bikkadIt.ElectronicStore.dtos.UserDto;
import com.bikkadIt.ElectronicStore.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //create
    UserDto createUser(UserDto user);


    //update

   // UserDto updateuser(UserDto userDto, String userId);

    void deleteUser(String userId);

    List<UserDto>getAllUser();
    UserDto getUserById(String UserId);

    UserDto getUserByEmail(String UserEmail);

    List<UserDto> searchUser(String Keyword);

    UserDto updateUser(UserDto userDto, String userId);
}
