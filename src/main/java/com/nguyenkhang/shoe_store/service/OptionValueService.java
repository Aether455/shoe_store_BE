package com.nguyenkhang.shoe_store.service;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenkhang.shoe_store.dto.request.option.OptionValueRequest;
import com.nguyenkhang.shoe_store.dto.response.option.OptionValueResponse;
import com.nguyenkhang.shoe_store.entity.User;
import com.nguyenkhang.shoe_store.exception.AppException;
import com.nguyenkhang.shoe_store.exception.ErrorCode;
import com.nguyenkhang.shoe_store.mapper.OptionValueMapper;
import com.nguyenkhang.shoe_store.repository.OptionRepository;
import com.nguyenkhang.shoe_store.repository.OptionValueRepository;
import com.nguyenkhang.shoe_store.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OptionValueService {
    OptionValueMapper optionValueMapper;
    OptionValueRepository optionValueRepository;
    UserRepository userRepository;
    OptionRepository optionRepository;

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public OptionValueResponse create(OptionValueRequest request) {

        if (optionValueRepository.existsByValue(request.getValue())) {
            throw new AppException(ErrorCode.OPTION_VALUE_EXISTED);
        }

        var option = optionRepository
                .findById(request.getOptionId())
                .orElseThrow(() -> new AppException(ErrorCode.OPTION_NOT_EXISTED));
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        User userCreate =
                userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var optionValue = optionValueMapper.toOptionValue(request);
        optionValue.setOption(option);
        optionValue.setCreateBy(userCreate);
        optionValue = optionValueRepository.save(optionValue);

        return optionValueMapper.toOptionValueResponse(optionValue);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public List<OptionValueResponse> getByOptionId(long optionId) {
        return optionValueRepository.findAllByOptionId(optionId).stream()
                .map(optionValueMapper::toOptionValueResponse)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(long id) {
        var value = optionValueRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.OPTION_VALUE_NOT_EXISTED));
        try {
            optionValueRepository.delete(value);
            optionValueRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.CANNOT_DELETE_OPTION_VALUE);
        }
    }
}
