package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.request.StaffRequest;
import com.nguyenkhang.mobile_store.dto.response.StaffResponse;
import com.nguyenkhang.mobile_store.entity.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StaffMapper {

    Staff toStaff(StaffRequest request);
    
    StaffResponse toStaffResponse(Staff staff);

    void updateStaff(@MappingTarget Staff staff, StaffRequest request);
}
