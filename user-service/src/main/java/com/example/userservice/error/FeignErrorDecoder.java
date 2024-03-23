package com.example.userservice.error;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {


    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400 -> {}
            case 404 -> {
                if (methodKey.contains("getOrders")) {
                    return FeignException.errorStatus(methodKey, response);
                }
                return new Exception(response.reason());
            }
            default-> {
                return new Exception(response.reason());
            }
        }
        return null;
    }
}
