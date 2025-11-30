package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.request.StaffUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.nguyenkhang.mobile_store.dto.request.StaffRequest;
import com.nguyenkhang.mobile_store.dto.response.StaffResponse;
import com.nguyenkhang.mobile_store.entity.Staff;

@Mapper(componentModel = "spring")
public interface StaffMapper {

    Staff toStaff(StaffRequest request);

    StaffResponse toStaffResponse(Staff staff);

    void updateStaff(@MappingTarget Staff staff, StaffUpdateRequest request);
}
