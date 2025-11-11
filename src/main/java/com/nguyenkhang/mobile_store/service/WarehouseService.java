package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.Coordinates;
import com.nguyenkhang.mobile_store.dto.request.WarehouseRequest;
import com.nguyenkhang.mobile_store.dto.response.WarehouseResponse;
import com.nguyenkhang.mobile_store.entity.User;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.WarehouseMapper;
import com.nguyenkhang.mobile_store.repository.UserRepository;
import com.nguyenkhang.mobile_store.repository.WarehouseRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public WarehouseResponse create(WarehouseRequest request) {


        var warehouse = warehouseMapper.toWarehouse(request);
        User userCreate = userService.getCurrentUser();

        Coordinates coordinates = geocodingService.getCoordinates(
                request.getWard(),
                request.getDistrict(),
                request.getProvince()
        );

        if (coordinates != null) {
            warehouse.setLatitude(coordinates.getLatitude());
            warehouse.setLongitude(coordinates.getLongitude());
        }

        warehouse.setCreateBy(userCreate);
        try{
            warehouse = warehouseRepository.save(warehouse);
        }catch(DataIntegrityViolationException e){
            log.error("Error in create warehouse: ",e);
            throw new AppException(ErrorCode.PRIORITY_ALREADY_EXISTED);
        }
        return warehouseMapper.toWarehouseResponse(warehouse);
    }

    public Page<WarehouseResponse> get(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var warehouse = warehouseRepository.findAll(pageable);

        return warehouse.map(warehouseMapper::toWarehouseResponse);
    }

    @Transactional
    public WarehouseResponse update(long id, WarehouseRequest request) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var warehouse = warehouseRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED));
        warehouseMapper.update(warehouse, request);
        Coordinates coordinates = geocodingService.getCoordinates(
                request.getWard(),
                request.getDistrict(),
                request.getProvince()
        );

        if (coordinates != null) {
            warehouse.setLatitude(coordinates.getLatitude());
            warehouse.setLongitude(coordinates.getLongitude());
        }

        warehouse.setUpdateBy(user);

        warehouse = warehouseRepository.save(warehouse);

        return warehouseMapper.toWarehouseResponse(warehouse);
    }

    public WarehouseResponse getById(long id) {
        var warehouse = warehouseRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED));
        return warehouseMapper.toWarehouseResponse(warehouse);
    }

    public void delete(long id) {
        warehouseRepository.deleteById(id);
    }
}
