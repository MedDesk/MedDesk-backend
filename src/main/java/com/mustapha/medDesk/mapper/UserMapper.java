package com.mustapha.medDesk.mapper;

import com.mustapha.medDesk.dto.request.user.UserDtoReequest;
import com.mustapha.medDesk.dto.request.user.UserDtoResponse;
import com.mustapha.medDesk.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")

public interface UserMapper {

    UserDtoResponse toDto(User user);

    User toEntity(UserDtoReequest request);
}
