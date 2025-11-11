package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.SupplierRequest;
import com.nguyenkhang.mobile_store.dto.request.WarehouseRequest;
import com.nguyenkhang.mobile_store.dto.response.StaffResponse;
import com.nguyenkhang.mobile_store.dto.response.SupplierResponse;
import com.nguyenkhang.mobile_store.dto.response.WarehouseResponse;
import com.nguyenkhang.mobile_store.entity.User;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.SupplierMapper;
import com.nguyenkhang.mobile_store.mapper.WarehouseMapper;
import com.nguyenkhang.mobile_store.repository.SupplierRepository;
import com.nguyenkhang.mobile_store.repository.UserRepository;
import com.nguyenkhang.mobile_store.repository.WarehouseRepository;
import com.nguyenkhang.mobile_store.specification.StaffSpecification;
import com.nguyenkhang.mobile_store.specification.SupplierSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierService {
    SupplierRepository supplierRepository;
    SupplierMapper supplierMapper;

    UserRepository userRepository;

    public SupplierResponse create(SupplierRequest request) {
        if (supplierRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User userCreate = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var supplier = supplierMapper.toSupplier(request);
        supplier.setCreateBy(userCreate);

        return supplierMapper.toSupplierResponse(supplierRepository.save(supplier));

    }

    public Page<SupplierResponse> get(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var suppliers = supplierRepository.findAll(pageable);

        return suppliers.map(supplierMapper::toSupplierResponse);
    }

    public SupplierResponse update(long id, SupplierRequest request) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var supplier = supplierRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.SUPPLIER_NOT_EXISTED));
        if (supplierRepository.existsByPhoneNumberAndIdNot(request.getPhoneNumber(),id)){
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }

        supplierMapper.update(supplier,request);
        supplier.setUpdateBy(user);

        return supplierMapper.toSupplierResponse(supplierRepository.save(supplier));    }

    public SupplierResponse getById(long id) {
        var supplier = supplierRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.SUPPLIER_NOT_EXISTED));
        return supplierMapper.toSupplierResponse(supplier);
    }

    public void delete(long id) {
        var supplier = supplierRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.SUPPLIER_NOT_EXISTED));
        supplierRepository.delete(supplier);
    }

    public Page<SupplierResponse> searchSupplier(String keyword, int page){
        Pageable pageable = PageRequest.of(page, 20);
        return supplierRepository.findAll(SupplierSpecification.createSpecification(keyword), pageable).map(supplierMapper::toSupplierResponse);
    }
}
