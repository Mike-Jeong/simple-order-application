package org.example.exception;

import org.example.type.CustomError;

public class CustomException extends RuntimeException {

    private final CustomError customError;

    public CustomException(CustomError customError) {
        super();
        this.customError = customError;
    }

    public String getErrorDescription() {
        return customError.getDescription();
    }
}
