package org.nkcoder.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.stream.Collectors;
import org.nkcoder.policy.exception.PolicyNotExistException;
import org.nkcoder.quotation.exception.InvalidQuotationException;
import org.nkcoder.user.exception.EmailExistException;
import org.nkcoder.user.exception.LoginFailedException;
import org.nkcoder.user.exception.UserNotExistException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorInputException.class)
    @ResponseStatus(FORBIDDEN)
    @ResponseBody
    public String handleException(ErrorInputException e) {
        return "You have input wrong information, please try it again.";
    }

    @ExceptionHandler(EmailExistException.class)
    @ResponseStatus(FOUND)
    @ResponseBody
    public String handleException(EmailExistException e) {
        return "Email already registered, please login.";
    }

    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public String handleException(UserNotExistException e) {
        return "User not exist";
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(FORBIDDEN)
    @ResponseBody
    public String handleException(LoginFailedException e) {
        return "Login failed";
    }

    @ExceptionHandler(PolicyNotExistException.class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public String handleException(PolicyNotExistException e) {
        return "Invalid policyNumber";
    }

    @ExceptionHandler(InvalidQuotationException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public String handleException(InvalidQuotationException e) {
        return "Please input information again";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public String handleException(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" | "));
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public String handleException(InvalidFormatException e) {
        return "Invalid input format";
    }
}
