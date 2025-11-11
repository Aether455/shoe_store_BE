package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.request.VoucherRequest;
import com.nguyenkhang.mobile_store.dto.response.vouchers.VoucherResponse;
import com.nguyenkhang.mobile_store.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VoucherMapper {

    Voucher toVoucher(VoucherRequest request);


    VoucherResponse toVoucherResponse(Voucher voucher);

    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    void updateVoucher(@MappingTarget Voucher voucher, VoucherRequest request);
}
