package com.bikkadIt.ElectronicStore.service.impl;

import com.bikkadIt.ElectronicStore.dtos.UserDto;
import com.bikkadIt.ElectronicStore.entity.User;
import com.bikkadIt.ElectronicStore.repository.UseRepository;
import com.bikkadIt.ElectronicStore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UseRepository useRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public UserDto createUser(UserDto userDto) {

        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = dtoToEntity(userDto);
        User savedUser = useRepository.save(user);
        // entity -->dto
        UserDto newDto = entityToDto(savedUser);
        return newDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = useRepository.findById(userId).orElseThrow(() -> new RuntimeException());
        this.useRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = useRepository.findAll();
        List<UserDto> dtosList = (List<UserDto>) users.stream()
                .map(user -> entityToDto(user));
        return dtosList;

    }

    @Override
    public UserDto getUserById(String userId) {
        User user = useRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        //mapper.entityToDto
        UserDto userDto = entityToDto(user);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String Email) {
        User user = useRepository.findByEmail(Email)
                .orElseThrow(()->new RuntimeException("user not found with given"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String Keyword) {
      List<User> users = useRepository.findByNameContaining(Keyword);
        List<UserDto> collect = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());

        return collect;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {


        return null;
    }

    private UserDto entityToDto(User savedUser) {
//       UserDto userDto=  UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .ImageName(savedUser.getImageName())
//                .build();

               return mapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
     //se manually method dtoToEntity
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .ImageName(userDto.getImageName())
//                .build();
        return mapper.map(userDto,User.class);
    }
}