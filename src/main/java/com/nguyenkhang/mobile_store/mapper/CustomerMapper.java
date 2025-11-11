package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.request.CustomerCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.CustomerUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponse;
import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponseForUser;
import com.nguyenkhang.mobile_store.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "addresses", ignore = true)
    Customer toCustomer(CustomerCreationRequest request);

    CustomerResponse toCustomerResponse(Customer customer);

    CustomerResponseForUser toCustomerResponseForUser(Customer customer);


    void updateCustomer(@MappingTarget Customer customer, CustomerUpdateRequest request);
}
