package com.ah.filestorage.exception.handler;

import com.ah.filestorage.exception.BadRequestException;
import com.ah.filestorage.exception.NotFoundException;
import com.ah.filestorage.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus
    @ExceptionHandler({NotFoundException.class, BadRequestException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return new ResponseEntity<>( new ErrorResponse( exception.getMessage( ) ),
                exception instanceof NotFoundException ?
                        HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST );
    }
}
