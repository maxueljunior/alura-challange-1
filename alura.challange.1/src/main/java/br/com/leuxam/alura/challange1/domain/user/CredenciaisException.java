package br.com.leuxam.alura.challange1.domain.user;

public class CredenciaisException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public CredenciaisException(String msg) {
		super(msg);
	}
}
