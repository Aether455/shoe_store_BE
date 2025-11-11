package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.RoleRequest;
import com.nguyenkhang.mobile_store.dto.response.RoleResponse;
import com.nguyenkhang.mobile_store.entity.Role;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.RoleMapper;
import com.nguyenkhang.mobile_store.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleMapper roleMapper;
    RoleRepository roleRepository;

    public RoleResponse createRole(RoleRequest request){

        if (roleRepository.existsById(request.getName())){
            throw new  AppException(ErrorCode.ROLE_EXISTED);
        }

        Role role = roleMapper.toRole(request);

        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
