package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.StaffUpdateRequest;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenkhang.mobile_store.dto.request.StaffRequest;
import com.nguyenkhang.mobile_store.dto.response.StaffResponse;
import com.nguyenkhang.mobile_store.entity.Staff;
import com.nguyenkhang.mobile_store.entity.User;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.StaffMapper;
import com.nguyenkhang.mobile_store.repository.StaffRepository;
import com.nguyenkhang.mobile_store.repository.UserRepository;
import com.nguyenkhang.mobile_store.specification.StaffSpecification;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffService {
    StaffRepository staffRepository;
    StaffMapper staffMapper;

    UserRepository userRepository;
    UserService userService;

    @Transactional
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public StaffResponse create(StaffRequest request) {
        if (staffRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        User userCreate = userService.getCurrentUser();
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var staff = staffMapper.toStaff(request);
        staff.setUser(user);
        staff.setCreateBy(userCreate);
        staff = staffRepository.save(staff);

        return staffMapper.toStaffResponse(staff);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<StaffResponse> getStaffs(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        var staffs = staffRepository.findAll(pageable);

        return staffs.map((staffMapper::toStaffResponse));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public StaffResponse getStaffById(long staffId) {
        Staff staff =
                staffRepository.findById(staffId).orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_EXISTED));

        return staffMapper.toStaffResponse(staff);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public StaffResponse update(Long staffId, StaffUpdateRequest request) {
        User userUpdate = userService.getCurrentUser();

        Staff staff =
                staffRepository.findById(staffId).orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_EXISTED));

        staffMapper.updateStaff(staff, request);
        staff.setUpdateBy(userUpdate);

        staff = staffRepository.save(staff);

        return staffMapper.toStaffResponse(staff);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<StaffResponse> searchStaffs(String keyword, int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        return staffRepository
                .findAll(StaffSpecification.createSpecification(keyword), pageable)
                .map(staffMapper::toStaffResponse);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(long staffId) {
        staffRepository.deleteById(staffId);
    }
}
