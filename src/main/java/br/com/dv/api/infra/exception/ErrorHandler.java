package br.com.dv.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    // We should return 404 instead of 500 when the entity is not found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleEntityNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    // We should return 400 and a json with the errors when there are validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorData>> handleMethodArgumentNotValidException(MethodArgumentNotValidException
                                                                                             exception) {
        var errors = exception.getBindingResult().getFieldErrors();
        var errorData = errors.stream().map(ErrorData::new).toList();

        return ResponseEntity.badRequest().body(errorData);
    }

    private record ErrorData(String field, String message) {
        public ErrorData(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}
