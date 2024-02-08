package com.example.ourThinkstore.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    String message;
    Boolean isSuccess;
    T data;
    int code;

    public static ApiResponse<?> success() {
        return new ApiResponse<>("Success", true, null, 200);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("Success", true, data, 200);
    }

    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<T>(message, false, null, 400);
    }

    public static <T> ApiResponse<T> forbidden(String message) {
        return new ApiResponse<T>(message, false, null, 403);
    }
}
