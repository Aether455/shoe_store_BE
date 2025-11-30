package com.nguyenkhang.mobile_store.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.nguyenkhang.mobile_store.dto.request.user.*;
import com.nguyenkhang.mobile_store.dto.response.user.*;
import com.nguyenkhang.mobile_store.repository.StaffRepository;
import com.nguyenkhang.mobile_store.specification.WarehouseSpecification;
import jakarta.persistence.EntityManager;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    private final StaffRepository staffRepository;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;

    EntityManager entityManager;


    @Transactional
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
    public UserResponseForCustomer createUserForCustomer(UserCreationRequestForCustomer request) {
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

    @Transactional
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserResponse createUserForStaff(UserCreationRequestForStaff request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        if (staffRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw  new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
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
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<UserResponse> getUsers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return userRepository.findAll(pageable).map(userMapper::toUserResponse);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserResponse getUserById(long id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Transactional
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserResponse updateUser(long userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!Objects.equals(userUpdateRequest.getEmail(), user.getEmail())
                && userRepository.existsByEmail(userUpdateRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        userMapper.updateUser(user, userUpdateRequest);

        var roles = roleRepository.findAllById(userUpdateRequest.getRoles());
        user.setRoles(new HashSet<>(roles));

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional(noRollbackFor = ConstraintViolationException.class)
    public void deleteUser(long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.delete(user);
        try {
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new AppException(ErrorCode.USER_CAN_NOT_DELETE);
        }
    }

    public SimpleUserInfoResponse getMyInfo(){
        var currentUser = getCurrentUser();

        return userMapper.toSimpleUserInfoResponse(currentUser);
    }

    @Transactional
    public SimpleUserResponse changePassword( UserChangePasswordRequest request){
        var currentUser = getCurrentUser();

        var authenticated = passwordEncoder.matches(request.getPassword(),currentUser.getPassword());

        if (!authenticated){
            throw new AppException(ErrorCode.INVALID_OLD_PASSWORD);
        }
        var validateNewPassword = request.getNewPassword().trim().equals(request.getConfirmationPassword());

        if (!validateNewPassword){
            throw new AppException(ErrorCode.CONFIRM_PASSWORD_NOT_MATCH);
        }

        currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));

        currentUser =  userRepository.save(currentUser);

        return userMapper.toSimpleUserResponse(currentUser);
    }

    public User getCurrentUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public Page<UserResponse> searchUser(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return userRepository
                .findAll(UserSpecification.createSpecification(keyword), pageable)
                .map(userMapper::toUserResponse);
    }
}
