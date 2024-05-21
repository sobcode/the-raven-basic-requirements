package com.app.theravenwithoutauth.controller.exceptionHandler;

import com.app.theravenwithoutauth.model.dto.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

/**
 * Global exception handler for controllers.
 * This class provides a centralized way to handle exceptions thrown by controllers.
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleNullPointerExceptions(NullPointerException exception) {
        return provideResponseEntity(HttpStatus.BAD_REQUEST,
                exception.getMessage(), exception.getClass().getSimpleName());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleValidationExceptions(HandlerMethodValidationException exception) {
        String[] exParts = exception.getMessage().split("\\\"");
        return provideResponseEntity(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE,
                exParts[exParts.length - 1], exception.getClass().getSimpleName());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleRuntimeExceptions(RuntimeException exception) {
        return provideResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(), exception.getClass().getSimpleName());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleException(Exception exception) {
        return provideResponseEntity(HttpStatus.BAD_REQUEST,
                exception.getMessage(), exception.getClass().getSimpleName());
    }

    /**
     * Generates a ResponseEntity with an ExceptionResponseDTO.
     *
     * @param status     HTTP status code for the response.
     * @param message    Error message to be included in the response.
     * @param simpleName Simple name of the exception class.
     * @return ResponseEntity containing the response DTO with error details.
     */
    private ResponseEntity<ExceptionResponseDTO> provideResponseEntity(HttpStatus status,
                                                                       String message, String simpleName) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(status.value(), message, simpleName);

        return new ResponseEntity<>(responseDTO, status);
    }
}

