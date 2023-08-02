package com.microservice.resourceservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class Mp3Validator implements ConstraintValidator<Mp3FileType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       return value != null && value.equals("audio/mpeg");
    }
}
