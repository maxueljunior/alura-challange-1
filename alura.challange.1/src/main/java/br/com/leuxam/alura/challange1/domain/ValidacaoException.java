package br.com.leuxam.alura.challange1.domain;

public class ValidacaoException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ValidacaoException(String msg) {
		super(msg);
	}
}
