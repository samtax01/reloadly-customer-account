package com.reloadly.customeraccount.helpers;

import com.reloadly.customeraccount.models.requests.CustomerRequest;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

public class Validator {

    @SneakyThrows
    public static void validate(Object request){
        Set<ConstraintViolation<Object>> factory = Validation.buildDefaultValidatorFactory().getValidator().validate(request);

        // return if no error
        if(factory.isEmpty())
            return;

        // format error
        StringBuilder messages = new StringBuilder();
        for (var d: factory)
            messages.append(d.getPropertyPath()).append(" ").append(d.getMessage()).append(". ");

        // throw an exception. (global handler to handle the error)
        throw new CustomException(messages.toString().trim(), HttpStatus.BAD_REQUEST);
    }

}
