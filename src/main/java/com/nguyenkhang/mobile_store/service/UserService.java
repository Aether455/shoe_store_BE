package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.user.UserCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.user.UserCreationRequestForCustomer;
import com.nguyenkhang.mobile_store.dto.request.user.UserCreationRequestForStaff;
import com.nguyenkhang.mobile_store.dto.request.user.UserUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.user.UserResponse;
import com.nguyenkhang.mobile_store.dto.response.user.UserResponseForCustomer;
import com.nguyenkhang.mobile_store.dto.response.user.UserResponseForStaff;
import com.nguyenkhang.mobile_store.entity.Customer;
import com.nguyenkhang.mobile_store.entity.Staff;
import com.nguyenkhang.mobile_store.entity.User;
import com.nguyenkhang.mobile_store.enums.Role;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.UserMapper;
import com.nguyenkhang.mobile_store.repository.RoleRepository;
import com.nguyenkhang.mobile_store.repository.UserRepository;
import com.nguyenkhang.mobile_store.specification.UserSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(new HashSet<>(roleRepository.findAllById(roles)));
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toUserResponse(user);
    }


    @Transactional
    public UserResponseForCustomer createUserForCustomer(UserCreationRequestForCustomer request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user = userMapper.toUserForCustomer(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.CUSTOMER.name());
        user.setRoles(new HashSet<>(roleRepository.findAllById(roles)));

        Customer customer = Customer.builder()
                .createBy(user)
                .user(user)
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .build();

        user.setCustomer(customer);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponseForCustomer(user);

    }

    public UserResponseForStaff createUserForStaff(UserCreationRequestForStaff request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user = userMapper.toUserForStaff(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(new HashSet<>(roleRepository.findAllById(roles)));

        Staff staff = Staff.builder()
                .createBy(user)
                .user(user)
                .fullName(request.getFullName())
                .position(request.getPosition())
                .hireDate(request.getHireDate())
                .phoneNumber(request.getPhoneNumber())
                .salary(request.getSalary())
                .build();
        user.setStaff(staff);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toUserResponseForStaff(user);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    public UserResponse getUserById(long id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Transactional
    public UserResponse updateUser(long userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!Objects.equals(userUpdateRequest.getEmail(), user.getEmail()) && userRepository.existsByEmail(userUpdateRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        userMapper.updateUser(user, userUpdateRequest);

        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));

        var roles = roleRepository.findAllById(userUpdateRequest.getRoles());
        user.setRoles(new HashSet<>(roles));

         user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    public User getCurrentUser(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public Page<UserResponse> searchUser(String keyword, int page){
        Pageable pageable = PageRequest.of(page, 20);
        return userRepository.findAll(UserSpecification.createSpecification(keyword), pageable).map(userMapper::toUserResponse);
    }

}
