package com.project.product_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 1. Why @ResponseStatus(HttpStatus.NOT_FOUND)?
// This is a convenient shortcut. If this exception is thrown and not caught by our
// @ControllerAdvice, Spring will automatically respond with a 404 NOT FOUND status.
// Our GlobalExceptionHandler will make this more robust, but it's good practice to have.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    // 2. Why extends RuntimeException?
    // We choose RuntimeException (an "unchecked" exception) over Exception (a "checked" exception)
    // because we don't want to be forced to add "throws ResourceNotFoundException" to every
    // method signature. It keeps the code cleaner. This type of exception typically indicates
    // a programming error or an unrecoverable situation, which fits a "not found" scenario.

    // 3. Why this constructor?
    // This allows us to create an instance of our exception with a clear, descriptive message
    // that explains what resource could not be found. For example: "User not found with id: 99"
    public ResourceNotFoundException(String message) {
        super(message);
    }
}