package com.food.chicken.exceptions.advice;

import com.food.chicken.exceptions.ExceptionType;
import com.food.chicken.exceptions.ExternalException;
import com.food.chicken.exceptions.InternalException;
import com.food.chicken.exceptions.UnknownException;
import com.food.chicken.exceptions.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = ExternalException.class)
    @ResponseBody
    protected ResponseEntity handleExternalException(RuntimeException exception) {

        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setStatus(ExceptionType.EXTERNAL_ERROR);
        errorMessage.setMessage(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(errorMessage);
    }

    @ExceptionHandler(value = UnknownException.class)
    @ResponseBody
    protected ResponseEntity handleUnknownException(RuntimeException exception) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setStatus(ExceptionType.UNKNOWN_ERROR);
        errorMessage.setMessage(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorMessage);
    }

    @ExceptionHandler(value = InternalException.class)
    @ResponseBody
    protected ResponseEntity handleInternalException(RuntimeException exception) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setStatus(ExceptionType.INTERNAL_ERROR);
        errorMessage.setMessage(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorMessage);
    }
}