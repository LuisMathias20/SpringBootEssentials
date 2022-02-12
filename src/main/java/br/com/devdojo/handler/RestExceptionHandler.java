package br.com.devdojo.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.devdojo.error.ResourceNotFoundDetails;
import br.com.devdojo.error.ResourceNotFoundException;
import br.com.devdojo.error.ValidationErrorDetails;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResourceNotFoundDetails> handleResourceNotFoundException(ResourceNotFoundException rfnException) {
		ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder
													.newBuilder()
													.timestamp(new Date().getTime())
													.status(HttpStatus.NOT_FOUND.value())
													.title("Resource not found")
													.detail(rfnException.getMessage())
													.developerMessage(rfnException.getClass().getName())
													.build();
		
		return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException manvException) {
		List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
		
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		
		ValidationErrorDetails rnfDetails = ValidationErrorDetails.Builder
													.newBuilder()
													.timestamp(new Date().getTime())
													.status(HttpStatus.BAD_REQUEST.value())
													.title("Field validation error")
													.detail("Field validation error")
													.developerMessage(manvException.getClass().getName())
													.field(fields)
													.fieldMessage(fieldMessages)
													.build();
		
		return new ResponseEntity<>(rnfDetails, HttpStatus.BAD_REQUEST);
	}
}
