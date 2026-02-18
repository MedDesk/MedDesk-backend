package com.mustapha.medDesk.service.impl;

import com.mustapha.medDesk.dto.request.user.UserDtoReequest;
import com.mustapha.medDesk.dto.request.user.UserUpdateRequest;
import com.mustapha.medDesk.dto.response.ApiResponse;
import com.mustapha.medDesk.dto.response.UserDtoResponse;
import com.mustapha.medDesk.exception.ResourceNotFoundException;
import com.mustapha.medDesk.exception.ValidationException;
import com.mustapha.medDesk.mapper.UserMapper;
import com.mustapha.medDesk.model.User;
import com.mustapha.medDesk.repository.UserRepository;
import com.mustapha.medDesk.service.UserService;
import com.mustapha.medDesk.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UserDtoResponse Create(UserDtoReequest request) {
        // check if the user already exist
        if (userRepository.findByEmail(request.getUsername()).isPresent()) {
            throw new ValidationException("Email is already exist");
        }

        // i get user dto so i have to transform it to entity
        User user = userMapper.toEntity(request);

        // hash the user password
        user.setPassword(PasswordUtil.hash(request.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
        }

    @Override
    public UserDtoResponse update(Long id, UserUpdateRequest request) {
        if(id == 1) throw  new ValidationException("you cannot update the main admin");

        // find user
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        // update basic user data we are not gonna to use manual method we gonna use mapstrcut integred method
        userMapper.updateUserFromDto(request, user);

        userRepository.save(user);
        return userMapper.toDto(user)

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<UserDtoResponse> getALl(int page, int size) {
        return null;
    }

    @Override
    public UserDtoResponse getById(Long id) {
        return null;
    }
}
