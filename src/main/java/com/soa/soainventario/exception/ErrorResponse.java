package com.soa.soainventario.exception;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;  // Opcional: para saber qué endpoint causó el error
    private Map<String, String> validationErrors;  // ← Campo necesario para errores de validación
}