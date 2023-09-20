package br.com.leuxam.alura.challange1.infra.exception;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.leuxam.alura.challange1.domain.ValidacaoException;
import br.com.leuxam.alura.challange1.domain.videos.VideoDesativadoException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class HandleExceptions {
	
	@ExceptionHandler({EntityNotFoundException.class, NullPointerException.class, NoSuchElementException.class})
	public ResponseEntity Error404() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity Error400(MethodArgumentNotValidException ex) {
		var errors = ex.getFieldErrors();
		return ResponseEntity.badRequest().body(errors.stream().map(TratadorDeErrosRequisicao::new));
	}
	
	@ExceptionHandler(VideoDesativadoException.class)
	public ResponseEntity Error400VideoDesativado(VideoDesativadoException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity Error400(ValidacaoException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	public record TratadorDeErrosRequisicao(String field, String defaultMessage) {
		public TratadorDeErrosRequisicao(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
}













