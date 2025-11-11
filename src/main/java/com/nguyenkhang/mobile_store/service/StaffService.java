package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.StaffRequest;
import com.nguyenkhang.mobile_store.dto.response.StaffResponse;
import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponse;
import com.nguyenkhang.mobile_store.entity.Staff;
import com.nguyenkhang.mobile_store.entity.User;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.StaffMapper;
import com.nguyenkhang.mobile_store.repository.StaffRepository;
import com.nguyenkhang.mobile_store.repository.UserRepository;
import com.nguyenkhang.mobile_store.specification.CustomerSpecification;
import com.nguyenkhang.mobile_store.specification.StaffSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffService {
    StaffRepository staffRepository;
    StaffMapper staffMapper;

    UserRepository userRepository;


    public StaffResponse create(StaffRequest request) {
        if (staffRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User userCreate = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var staff = staffMapper.toStaff(request);
        staff.setUser(user);
        staff.setCreateBy(userCreate);
        staff = staffRepository.save(staff);

        return staffMapper.toStaffResponse(staff);
    }

    public Page<StaffResponse> getStaffs(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        var staffs = staffRepository.findAll(pageable);

        return staffs.map((staffMapper::toStaffResponse));
    }

    public StaffResponse getStaffById(long staffId) {
        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_EXISTED));

        return staffMapper.toStaffResponse(staff);
    }

    public StaffResponse update(Long staffId, StaffRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User userUpdate = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_EXISTED));

        staffMapper.updateStaff(staff, request);
        staff.setUpdateBy(userUpdate);

        staff = staffRepository.save(staff);

        return staffMapper.toStaffResponse(staff);
    }

    public Page<StaffResponse> searchStaffs(String keyword, int page){
        Pageable pageable = PageRequest.of(page, 20);
        return staffRepository.findAll(StaffSpecification.createSpecification(keyword), pageable).map(staffMapper::toStaffResponse);
    }

    public void delete(long staffId) {
        staffRepository.deleteById(staffId);
    }
}
