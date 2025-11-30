package com.nguyenkhang.mobile_store.service;

import java.util.List;

import com.nguyenkhang.mobile_store.dto.response.option.SimpleOptionResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OptionService {
    OptionMapper optionMapper;
    OptionRepository optionRepository;
    UserRepository userRepository;

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public OptionResponse create(OptionRequest request) {
        if (optionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.OPTION_EXISTED);
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        User userCreate =
                userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var option = optionMapper.toOption(request);
        option.setCreateBy(userCreate);

        option = optionRepository.save(option);

        return optionMapper.toOptionResponse(option);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public List<OptionResponse> getAll() {
        return optionRepository.findAll().stream()
                .map(optionMapper::toOptionResponse)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public List<SimpleOptionResponse> getAllForUsing() {
        return optionRepository.findAll().stream()
                .map(optionMapper::toSimpleOptionResponse)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public OptionResponse getById(long id) {
        var option = optionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.OPTION_NOT_EXISTED));
        return optionMapper.toOptionResponse(option);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(long id) {
        optionRepository.deleteById(id);
    }
}
