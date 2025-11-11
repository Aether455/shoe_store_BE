package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.AddressRequest;
import com.nguyenkhang.mobile_store.dto.request.CustomerCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.CustomerUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponse;
import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponseForUser;
import com.nguyenkhang.mobile_store.dto.response.order.SimpleOrderResponse;
import com.nguyenkhang.mobile_store.entity.Customer;
import com.nguyenkhang.mobile_store.entity.User;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.CustomerMapper;
import com.nguyenkhang.mobile_store.repository.CustomerRepository;
import com.nguyenkhang.mobile_store.repository.UserRepository;
import com.nguyenkhang.mobile_store.specification.CustomerSpecification;
import com.nguyenkhang.mobile_store.specification.OrderSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService {

    CustomerMapper customerMapper;
    CustomerRepository customerRepository;
    AddressService addressService;
    UserRepository userRepository;

    UserService userService;

// admin & nv
    @Transactional
    public CustomerResponse create(CustomerCreationRequest request) {

        if (customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);

        }
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User userCreate = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var customer = customerMapper.toCustomer(request);

        customer.setCreateBy(userCreate);
        customer = customerRepository.save(customer);

        if (!(request.getAddresses().isEmpty())) {
            AddressRequest addressRequest = new AddressRequest();

            for (String address : request.getAddresses()) {
                addressRequest.setCustomerId(customer.getId());
                addressRequest.setAddress(address);
                addressService.create(addressRequest);
            }
        }


        return customerMapper.toCustomerResponse(customer);
    }
// admin & nv

    public Page<CustomerResponse> getALl(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        var customers = customerRepository.findAll(pageable);

        return customers.map(customerMapper::toCustomerResponse);
    }
// admin & nv
@Transactional
    public CustomerResponse update(long id, CustomerUpdateRequest updateRequest) {
        if (customerRepository.existsByPhoneNumberAndIdNot(updateRequest.getPhoneNumber(), id)) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_EXISTED));



        User userUpdate = userService.getCurrentUser();

        customerMapper.updateCustomer(customer, updateRequest);

        customer.setUpdateBy(userUpdate);
        customer = customerRepository.save(customer);

        return customerMapper.toCustomerResponse(customer);
    }
//nguoi dung
@Transactional
public CustomerResponseForUser updateByCurrentUser(CustomerUpdateRequest updateRequest) {
        User user = userService.getCurrentUser();

        Customer customer = customerRepository.findByUserId(user.getId()).orElse(null);
        if (customer == null) {

            if (customerRepository.existsByPhoneNumber(updateRequest.getPhoneNumber())) {
                throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
            }

            customer = Customer.builder().user(user)
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

    public Page<CustomerResponse> searchCustomers(String keyword, int page){
        Pageable pageable = PageRequest.of(page, 20);
        return customerRepository.findAll(CustomerSpecification.createSpecification(keyword),pageable).map(customerMapper::toCustomerResponse);
    }

    public CustomerResponse getById(long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_EXISTED));

        return customerMapper.toCustomerResponse(customer);
    }

    public CustomerResponseForUser getCustomerByCurrentUser() {
        User user = userService.getCurrentUser();

        Customer customer = customerRepository.findByUserId(user.getId()).orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_EXISTED));

        return customerMapper.toCustomerResponseForUser(customer);
    }

    public void delete(long id) {
        customerRepository.deleteById(id);
    }
}
