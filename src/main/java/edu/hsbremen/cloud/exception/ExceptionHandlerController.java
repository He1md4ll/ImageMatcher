package edu.hsbremen.cloud.exception;

import edu.hsbremen.cloud.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public @ResponseBody ErrorDto handleAccessDeniedException(AccessDeniedException e) {
        return new ErrorDto("ERROR_ACCESS_DENIED", e.getMessage());
    }
}