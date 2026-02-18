package com.mustapha.medDesk.service;

import com.mustapha.medDesk.dto.request.user.UserDtoReequest;
import com.mustapha.medDesk.dto.request.user.UserDtoResponse;
import com.mustapha.medDesk.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDtoResponse Create(UserDtoReequest reequest);
    UserDtoReequest update(Long id, UserDtoReequest reequest);
    void delete(Long id);
    Page<UserDtoResponse> getALl(int page, int size);
    UserDtoResponse getById(Long id);
}
