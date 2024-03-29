package br.com.devdojo.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import br.com.devdojo.error.ErrorDetails;
import br.com.devdojo.error.ResourceNotFoundDetails;
import br.com.devdojo.error.ResourceNotFoundException;
import br.com.devdojo.error.ValidationErrorDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
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
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(	MethodArgumentNotValidException manvException, 
																HttpHeaders headers, 
																HttpStatus status, 
																WebRequest request	) { 
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
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(	Exception ex, 
																@Nullable Object body, 
																HttpHeaders headers, 
																HttpStatus status, 
																WebRequest request	) {
		ErrorDetails errorDetails = ResourceNotFoundDetails.Builder
				.newBuilder()
				.timestamp(new Date().getTime())
				.status(status.value())
				.title("Internal Exception")
				.detail(ex.getMessage())
				.developerMessage(ex.getClass().getName())
				.build();
		
		return new ResponseEntity<>(errorDetails, headers, status);
	}
}
