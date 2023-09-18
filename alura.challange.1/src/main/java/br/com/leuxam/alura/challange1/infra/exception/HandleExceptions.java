package br.com.leuxam.alura.challange1.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class HandleExceptions {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity Error404(EntityNotFoundException ex) {
		return ResponseEntity.notFound().build();
	}
}
