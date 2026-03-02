package com.mustapha.medDesk.mapper;

import com.mustapha.medDesk.dto.request.user.UserDtoRequest;
import com.mustapha.medDesk.dto.request.user.UserUpdateRequest;
import com.mustapha.medDesk.dto.response.user.UserDtoResponse;
import com.mustapha.medDesk.model.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring",     builder = @Builder(disableBuilder = true) // This disables the use of Lombok Builders
)
public interface UserMapper {

    UserDtoResponse toDto(User user);

    User toEntity(UserDtoRequest request);


    // this updates the filed that are sent form the request, so we don't have to do that manually
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateRequest dto, @MappingTarget User user);
}
