package com.nguyenkhang.shoe_store.service;

import jakarta.persistence.EntityManager;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenkhang.shoe_store.dto.Coordinates;
import com.nguyenkhang.shoe_store.dto.request.warehouse.WarehouseRequest;
import com.nguyenkhang.shoe_store.dto.response.warehouse.WarehouseResponse;
import com.nguyenkhang.shoe_store.entity.User;
import com.nguyenkhang.shoe_store.exception.AppException;
import com.nguyenkhang.shoe_store.exception.ErrorCode;
import com.nguyenkhang.shoe_store.mapper.WarehouseMapper;
import com.nguyenkhang.shoe_store.repository.UserRepository;
import com.nguyenkhang.shoe_store.repository.WarehouseRepository;
import com.nguyenkhang.shoe_store.specification.WarehouseSpecification;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WarehouseService {
    WarehouseMapper warehouseMapper;
    WarehouseRepository warehouseRepository;
    UserRepository userRepository;

    UserService userService;
    GeocodingService geocodingService;
    EntityManager entityManager;

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public WarehouseResponse create(WarehouseRequest request) {

        var warehouse = warehouseMapper.toWarehouse(request);
        User userCreate = userService.getCurrentUser();

        Coordinates coordinates =
                geocodingService.getCoordinates(request.getWard(), request.getDistrict(), request.getProvince());

        if (coordinates != null) {
            warehouse.setLatitude(coordinates.getLatitude());
            warehouse.setLongitude(coordinates.getLongitude());
        }

        warehouse.setCreateBy(userCreate);
        try {
            warehouse = warehouseRepository.save(warehouse);
        } catch (DataIntegrityViolationException e) {
            log.error("Error in create warehouse: ", e);
            throw new AppException(ErrorCode.PRIORITY_ALREADY_EXISTED);
        }
        return warehouseMapper.toWarehouseResponse(warehouse);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<WarehouseResponse> get(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var warehouse = warehouseRepository.findAll(pageable);

        return warehouse.map(warehouseMapper::toWarehouseResponse);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public WarehouseResponse update(long id, WarehouseRequest request) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var warehouse =
                warehouseRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED));
        warehouseMapper.update(warehouse, request);
        Coordinates coordinates =
                geocodingService.getCoordinates(request.getWard(), request.getDistrict(), request.getProvince());

        if (coordinates != null) {
            warehouse.setLatitude(coordinates.getLatitude());
            warehouse.setLongitude(coordinates.getLongitude());
        }

        warehouse.setUpdateBy(user);

        try {
            warehouse = warehouseRepository.save(warehouse);
        } catch (DataIntegrityViolationException e) {
            log.error("Error in create warehouse: ", e);
            throw new AppException(ErrorCode.PRIORITY_ALREADY_EXISTED);
        }

        return warehouseMapper.toWarehouseResponse(warehouse);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public WarehouseResponse getById(long id) {
        var warehouse =
                warehouseRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED));
        return warehouseMapper.toWarehouseResponse(warehouse);
    }

    @Transactional(rollbackFor = ConstraintViolationException.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(long id) {
        var warehouse =
                warehouseRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED));
        try {
            warehouseRepository.delete(warehouse);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new AppException(ErrorCode.CANNOT_DELETE_WAREHOUSE);
        }
    }

    public Page<WarehouseResponse> searchWarehouse(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return warehouseRepository
                .findAll(WarehouseSpecification.createSpecification(keyword), pageable)
                .map(warehouseMapper::toWarehouseResponse);
    }
}
