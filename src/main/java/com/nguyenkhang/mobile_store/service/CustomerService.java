package com.nguyenkhang.mobile_store.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenkhang.mobile_store.dto.request.customer.CustomerCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.customer.CustomerUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponse;
import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponseForUser;
import com.nguyenkhang.mobile_store.entity.Address;
import com.nguyenkhang.mobile_store.entity.Customer;
import com.nguyenkhang.mobile_store.entity.User;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.AddressMapper;
import com.nguyenkhang.mobile_store.mapper.CustomerMapper;
import com.nguyenkhang.mobile_store.repository.CustomerRepository;
import com.nguyenkhang.mobile_store.repository.UserRepository;
import com.nguyenkhang.mobile_store.specification.CustomerSpecification;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService {

    CustomerMapper customerMapper;
    CustomerRepository customerRepository;
    AddressService addressService;
    AddressMapper addressMapper;
    UserRepository userRepository;

    UserService userService;

    EntityManager entityManager;

    // admin & nv
    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public CustomerResponse create(CustomerCreationRequest request) {

        if (customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }

        User userCreate = userService.getCurrentUser();

        var customer = customerMapper.toCustomer(request);

        customer.setCreateBy(userCreate);

        if (!(request.getAddresses().isEmpty())) {
            List<Address> addresses = new ArrayList<>();

            for (var address : request.getAddresses()) {
                addresses.add(addressMapper.toAddress(address));
            }

            customer.setAddresses(addresses);
        }
        customer = customerRepository.save(customer);

        return customerMapper.toCustomerResponse(customer);
    }

    // admin & nv
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<CustomerResponse> getALl(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        var customers = customerRepository.findAll(pageable);

        return customers.map(customerMapper::toCustomerResponse);
    }

    // admin & nv
    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public CustomerResponse update(long id, CustomerUpdateRequest updateRequest) {
        if (customerRepository.existsByPhoneNumberAndIdNot(updateRequest.getPhoneNumber(), id)) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }

        Customer customer =
                customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_EXISTED));

        User userUpdate = userService.getCurrentUser();

        customerMapper.updateCustomer(customer, updateRequest);

        customer.setUpdateBy(userUpdate);
        customer = customerRepository.save(customer);

        return customerMapper.toCustomerResponse(customer);
    }

    // nguoi dung
    @Transactional
    public CustomerResponseForUser updateByCurrentUser(CustomerUpdateRequest updateRequest) {
        User user = userService.getCurrentUser();

        Customer customer = customerRepository.findByUserId(user.getId()).orElse(null);
        if (customer == null) {

            if (customerRepository.existsByPhoneNumber(updateRequest.getPhoneNumber())) {
                throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
            }

            customer = Customer.builder()
                    .user(user)
                    .fullName(updateRequest.getFullName())
                    .phoneNumber(updateRequest.getPhoneNumber())
                    .build();

        } else {
            if (customerRepository.existsByPhoneNumberAndIdNot(updateRequest.getPhoneNumber(), customer.getId())) {
                throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
            }
            customerMapper.updateCustomer(customer, updateRequest);
        }

        customer = customerRepository.save(customer);
        return customerMapper.toCustomerResponseForUser(customer);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<CustomerResponse> searchCustomers(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return customerRepository
                .findAll(CustomerSpecification.createSpecification(keyword), pageable)
                .map(customerMapper::toCustomerResponse);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public CustomerResponse getById(long id) {
        Customer customer =
                customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_EXISTED));

        return customerMapper.toCustomerResponse(customer);
    }

    public CustomerResponseForUser getCustomerByCurrentUser() {
        User user = userService.getCurrentUser();

        Customer customer = customerRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_EXISTED));

        return customerMapper.toCustomerResponseForUser(customer);
    }

    @Transactional(rollbackFor = ConstraintViolationException.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(long id) {
        Customer customer =
                customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_EXISTED));
        try {
            customerRepository.delete(customer);
            entityManager.flush();
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.CANNOT_DELETE_CUSTOMER);
        }
    }
}
