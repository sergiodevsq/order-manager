package br.com.amcom.order.custom.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.amcom.order.custom.exceptions.DataIntegrityException;
import br.com.amcom.order.custom.exceptions.ObjectNotFoundException;
import br.com.amcom.order.custom.exceptions.StandardException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardException> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		StandardException standardException = new StandardException(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardException);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardException> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		StandardException standardException = new StandardException(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardException);
	}
	
}
