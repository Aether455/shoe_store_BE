package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.response.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.nguyenkhang.mobile_store.dto.request.user.UserCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.user.UserCreationRequestForCustomer;
import com.nguyenkhang.mobile_store.dto.request.user.UserCreationRequestForStaff;
import com.nguyenkhang.mobile_store.dto.request.user.UserUpdateRequest;
import com.nguyenkhang.mobile_store.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest userCreationRequest);

    User toUserForCustomer(UserCreationRequestForCustomer userCreationRequest);

    User toUserForStaff(UserCreationRequestForStaff userCreationRequest);

    UserResponse toUserResponse(User user);
    SimpleUserResponse toSimpleUserResponse(User user);
    SimpleUserInfoResponse toSimpleUserInfoResponse(User user);


    UserResponseForCustomer toUserResponseForCustomer(User user);

    UserResponseForStaff toUserResponseForStaff(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
