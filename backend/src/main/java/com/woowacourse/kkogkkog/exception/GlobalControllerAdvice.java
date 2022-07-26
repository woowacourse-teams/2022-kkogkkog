package com.woowacourse.kkogkkog.exception;

import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    private static final String INVALID_REQUEST_EXCEPTION_MESSAGE_FORMAT = "잘못된 입력입니다: [%s]";
    private static final String INVALID_REQUEST_DELIMITER = ", ";

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse("예상치 못한 에러가 발생했습니다."),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(InvalidRequestException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UnauthenticatedException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ForbiddenException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(
        MethodArgumentNotValidException bindingResult) {
        String causes = bindingResult.getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(INVALID_REQUEST_DELIMITER));
        String errorMessage = String.format(INVALID_REQUEST_EXCEPTION_MESSAGE_FORMAT, causes);
        return new ResponseEntity<>(new ErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @Getter
    private static class ErrorResponse {

        String message;

        ErrorResponse(String message) {
            this.message = message;
        }
    }
}
