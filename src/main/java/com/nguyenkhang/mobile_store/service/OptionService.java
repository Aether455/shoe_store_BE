package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.option.OptionRequest;
import com.nguyenkhang.mobile_store.dto.response.option.OptionResponse;
import com.nguyenkhang.mobile_store.entity.User;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.OptionMapper;
import com.nguyenkhang.mobile_store.repository.OptionRepository;
import com.nguyenkhang.mobile_store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OptionService {
    OptionMapper optionMapper;
    OptionRepository optionRepository;
    UserRepository userRepository;

    @Transactional
    public OptionResponse create(OptionRequest request){
        if (optionRepository.existsByName(request.getName())){
            throw  new AppException(ErrorCode.OPTION_EXISTED);
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        User userCreate = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var option = optionMapper.toOption(request);
        option.setCreateBy(userCreate);

        option = optionRepository.save(option);

        return optionMapper.toOptionResponse(option);
    }

    public List<OptionResponse> getAll(){
        return optionRepository.findAll().stream().map(optionMapper::toOptionResponse).toList();
    }

    public OptionResponse getById(long id){
        var option = optionRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.OPTION_NOT_EXISTED));
        return optionMapper.toOptionResponse(option);
    }

    public void delete(long id){
        optionRepository.deleteById(id);
    }
}
