package simple.food.backend.infrastructur.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ServiceException extends RuntimeException {
    private final ErrorMessages error;
    private final HttpStatus status;
    private final List<String> details;

    public ServiceException(ErrorMessages error, HttpStatus status) {
        super(error != null ? error.getMessage() : null);
        this.error = error;
        this.status = status;
        this.details = null;
    }

    public ServiceException(ErrorMessages error, HttpStatus status, String message) {
        super(message);
        this.error = error;
        this.status = status;
        this.details = null;
    }

    public ServiceException(ErrorMessages error, HttpStatus status, List<String> details) {
        super(error != null ? error.getMessage() : null);
        this.error = error;
        this.status = status;
        this.details = details;
    }

    public ServiceException(ErrorMessages error, HttpStatus status, String message, List<String> details) {
        super(message);
        this.error = error;
        this.status = status;
        this.details = details;
    }

    public ErrorMessages getError() {
        return error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getDetails() {
        return details;
    }
}

