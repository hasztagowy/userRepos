package hasztagowy.userepos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundEx(UserNotFoundException e) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ExceptionModel exceptionModel = new ExceptionModel(httpStatus.value(), e.getMessage());

        return new ResponseEntity<>(exceptionModel, httpStatus);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = UserExistException.class)
    public ResponseEntity<Object> userExist(UserNotFoundException e) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ExceptionModel exceptionModel = new ExceptionModel(httpStatus.value(), e.getMessage());

        return new ResponseEntity<>(exceptionModel, httpStatus);
    }

}
