package com.umarta.dailyinfo.app.handler;

import com.umarta.dailyinfo.app.dto.ApiErrorDTO;
import com.umarta.dailyinfo.app.exception.DailyInfoException;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    // 400
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        log.error("Exception thrown in controller", ex);
        return createErrorResponseEntity(HttpStatus.BAD_REQUEST,
                new ApiErrorDTO("Некорректные параметры запроса"));
    }

    // 404
    @ExceptionHandler({DailyInfoException.class})
    public ResponseEntity<?> handleErrorAsNotFound(Throwable ex) {
        return createErrorResponseEntity(HttpStatus.NOT_FOUND,
                new ApiErrorDTO(ex.getLocalizedMessage()));
    }

    // 500
    @ExceptionHandler({ParseException.class})
    public ResponseEntity<?> handleErrorAsInternalError(Throwable ex) {
        return createErrorResponseEntity(HttpStatus.NOT_IMPLEMENTED,
                new ApiErrorDTO(ex.getLocalizedMessage()));
    }

    private ResponseEntity<?> createErrorResponseEntity(HttpStatus httpStatus, Serializable bodyDto) {
        return ResponseEntity
                .status(httpStatus)
                .body(bodyDto);
    }
}
