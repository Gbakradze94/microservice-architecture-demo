package com.microservice.resourceprocessor.util;

public interface Converter<O, I> {
    O convert(I input);
}
