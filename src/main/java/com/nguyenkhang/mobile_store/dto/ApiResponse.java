package com.nguyenkhang.mobile_store.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.JoinColumn;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @Builder.Default
    int code = 1000;
    @Builder.Default
    String message = "Success!";
    T result;
}
