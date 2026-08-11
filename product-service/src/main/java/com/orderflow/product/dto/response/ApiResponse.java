package com.orderflow.product.dto.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.orderflow.product.il8n.MessageHelper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	
    private boolean success;
    
    private String code;
    
    private String message;
    
    private T data;
    
    private List<FieldError> errors;
        
    private LocalDateTime timestamp;
    
    private String path;
    
    private String traceId;
    
    public static <T> ApiResponse<T> ok(String code, String messageKey, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(code)
                .message(MessageHelper.getMessage(messageKey))
                .data(data)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }
    
    public static <T> ApiResponse<T> error(String code, String messageKey) {
        return ApiResponse.<T>builder()
                .success(false)
                .code(code)
                .message(MessageHelper.getMessage(messageKey))
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }
    
    public static <T> ApiResponse<T> error(String code, String messageKey, List<FieldError> errors) {
        return ApiResponse.<T>builder()
                .success(false)
                .code(code)
                .message(MessageHelper.getMessage(messageKey))
                .errors(errors)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }
    
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldError {
        private String field;      
        private String code;       
        private String message;    
    }
}
