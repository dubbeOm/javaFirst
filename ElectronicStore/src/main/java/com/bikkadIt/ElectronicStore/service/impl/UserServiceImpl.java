package com.bikkadIt.ElectronicStore.service.impl;

import com.bikkadIt.ElectronicStore.dtos.UserDto;
import com.bikkadIt.ElectronicStore.entity.User;
import com.bikkadIt.ElectronicStore.exception.ResourseNotFoundException;
import com.bikkadIt.ElectronicStore.repository.UseRepository;
import com.bikkadIt.ElectronicStore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UseRepository useRepository;
    @Autowired
    private ModelMapper mapper;

    public UserServiceImpl(UseRepository useRepository, ModelMapper mapper) {
        this.useRepository = useRepository;
        this.mapper = mapper;
    }


    @Override
    public UserDto createUser(UserDto userDto) {

        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = dtoToEntity(userDto);
        User savedUser = useRepository.save(user);
        // entity -->dto
        return entityToDto(savedUser);
    }

    @Override
    public void deleteUser(String userId) {
        User user = useRepository.findById(userId).orElseThrow(()->new ResourseNotFoundException("this is user Repository"));
        this.useRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = useRepository.findAll();
        return (List<UserDto>) users.stream()
                .map(user -> entityToDto(user));

    }

    @Override
    public UserDto getUserById(String userId) {
        User user = useRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User not found"));
        //mapper.entityToDto
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String Email) {
        User user = useRepository.findByEmail(Email)
                .orElseThrow(()->new ResourseNotFoundException("user not found with given"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String Keyword) {
      List<User> users = useRepository.findByNameContaining(Keyword);

        return users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = useRepository.findById(userId).orElseThrow(()->new ResourseNotFoundException("User not found with given id"));
        user.setName(userDto.getName());
       //Email update
     user.setAbout(userDto.getAbout());
     user.setGender(userDto.getGender());
     user.setPassword(userDto.getPassword());
     user.setImageName(userDto.getImageName());

     //save data
        user.updateUser=useRepository.save(user);
        userDto.updatedDto = entityToDto(user);
        return userDto;
    }

    private UserDto entityToDto(User savedUser) {
       UserDto userDto=  UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .about(savedUser.getAbout())
                .gender(savedUser.getGender())
                .ImageName(savedUser.getImageName())
                .build();

               return mapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
     //se manually method dtoToEntity
        User user = User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .gender(userDto.getGender())
                .ImageName(userDto.getImageName())
                .build();
        return mapper.map(userDto,User.class);
    }
}