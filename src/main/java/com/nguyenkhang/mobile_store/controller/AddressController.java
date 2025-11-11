package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.AddressRequest;
import com.nguyenkhang.mobile_store.dto.response.AddressResponse;
import com.nguyenkhang.mobile_store.service.AddressService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AddressController {
    AddressService addressService;

    @PostMapping
    public ApiResponse<AddressResponse> create(@RequestBody @Valid AddressRequest request){
        var response = addressService.create(request);
        return ApiResponse.<AddressResponse>builder().message("Success!").result(response).build();
    }

    @GetMapping
    public ApiResponse<List<AddressResponse>> getAddresses(){
        var response = addressService.getAddresses();
        return ApiResponse.<List<AddressResponse>>builder().message("Success!").result(response).build();
    }
    @GetMapping("/{customerId}")
    public ApiResponse<List<AddressResponse>> getAddressesByCustomerId(@PathVariable long customerId){
        var response = addressService.getAllByCustomerId(customerId);
        return ApiResponse.<List<AddressResponse>>builder().message("Success!").result(response).build();
    }

    @DeleteMapping("/{addressId}")
    public ApiResponse<String> delete(@PathVariable long addressId){
        addressService.delete(addressId);
        return ApiResponse.<String>builder().message("Success!").result("Address has been deleted").build();
    }
}
