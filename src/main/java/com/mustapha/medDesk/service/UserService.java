package com.mustapha.medDesk.service;

import com.mustapha.medDesk.dto.request.user.UserDtoReequest;
import com.mustapha.medDesk.dto.request.user.UserUpdateRequest;
import com.mustapha.medDesk.dto.response.ApiResponse;
import com.mustapha.medDesk.dto.response.UserDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDtoResponse Create(UserDtoReequest request);
    UserDtoResponse update(Long id, UserUpdateRequest request);
    void delete(Long id);
    Page<UserDtoResponse> getALl(int page, int size);
    UserDtoResponse getById(Long id);
}
