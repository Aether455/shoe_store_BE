package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.option.OptionValueRequest;
import com.nguyenkhang.mobile_store.dto.response.option.OptionValueResponse;
import com.nguyenkhang.mobile_store.entity.User;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.OptionValueMapper;
import com.nguyenkhang.mobile_store.repository.OptionRepository;
import com.nguyenkhang.mobile_store.repository.OptionValueRepository;
import com.nguyenkhang.mobile_store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OptionValueService {
    OptionValueMapper optionValueMapper;
    OptionValueRepository optionValueRepository;
    UserRepository userRepository;
    OptionRepository optionRepository;
    @Transactional
    public OptionValueResponse create(OptionValueRequest request){

        if (optionValueRepository.existsByValue(request.getValue())){
            throw  new AppException(ErrorCode.OPTION_VALUE_EXISTED);
        }

        var option = optionRepository.findById(request.getOptionId()).orElseThrow(()->new AppException(ErrorCode.OPTION_NOT_EXISTED));
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        User userCreate = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var optionValue = optionValueMapper.toOptionValue(request);
        optionValue.setOption(option);
        optionValue.setCreateBy(userCreate);
        optionValue = optionValueRepository.save(optionValue);

        return optionValueMapper.toOptionValueResponse(optionValue);
    }

    public List<OptionValueResponse> getByOptionId(long optionId){
        return optionValueRepository.findAllByOptionId(optionId).stream().map(optionValueMapper::toOptionValueResponse).toList();
    }

    public void delete(long id){
        optionValueRepository.deleteById(id);
    }
}
