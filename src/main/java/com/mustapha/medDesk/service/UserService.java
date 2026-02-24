package com.mustapha.medDesk.service;


import com.mustapha.medDesk.dto.request.user.UserDtoRequest;
import com.mustapha.medDesk.dto.request.user.UserUpdateRequest;
import com.mustapha.medDesk.dto.response.user.UserDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDtoResponse Create(UserDtoRequest request);
    UserDtoResponse update(Long id, UserUpdateRequest request);
    void delete(Long id);
    Page<UserDtoResponse> getALl(int page, int size);
    UserDtoResponse getById(Long id);
}
