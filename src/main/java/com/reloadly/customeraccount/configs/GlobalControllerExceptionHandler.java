package com.reloadly.customeraccount.configs;

import com.reloadly.customeraccount.helpers.CustomException;
import com.reloadly.customeraccount.helpers.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLException;

/**
 * Handle global exception
 */
@Slf4j
@ResponseBody
@RestControllerAdvice(basePackages = "com.reloadly.customeraccount.controllers")
public class GlobalControllerExceptionHandler {

    @ExceptionHandler({ConversionFailedException.class, HttpClientErrorException.NotAcceptable.class, SQLException.class})
    public ResponseEntity<Response<String>> handleConversion(Exception ex) {
        return new ResponseEntity<>(Response.errorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response<String>> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(Response.errorMessage(ex.getMessage()), ex.getStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handleOtherException(Exception ex) {
        return new ResponseEntity<>(Response.errorMessage(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
