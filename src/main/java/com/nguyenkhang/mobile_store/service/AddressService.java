package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.AddressRequest;
import com.nguyenkhang.mobile_store.dto.response.AddressResponse;
import com.nguyenkhang.mobile_store.entity.Customer;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.AddressMapper;
import com.nguyenkhang.mobile_store.repository.AddressRepository;
import com.nguyenkhang.mobile_store.repository.CustomerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AddressService {
    AddressMapper addressMapper;
    AddressRepository addressRepository;
    CustomerRepository customerRepository;

    public AddressResponse create(AddressRequest request){
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(()->new AppException(ErrorCode.CUSTOMER_NOT_EXISTED));
        var address = addressMapper.toAddress(request);

        address.setCustomer(customer);

        address = addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    public List<AddressResponse> getAllByCustomerId(long customerId){
        var addresses = addressRepository.findAllByCustomerId(customerId);

        return addresses.stream().map(addressMapper::toAddressResponse).toList();
    }

    public List<AddressResponse> getAddresses(){
        return addressRepository.findAll().stream().map(addressMapper::toAddressResponse).toList();
    }

    public void delete(long addressId){
        addressRepository.deleteById(addressId);
    }
}
