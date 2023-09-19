package br.com.leuxam.alura.challange1.domain.videos;

public class VideoDesativadoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public VideoDesativadoException(String msg) {
		super(msg);
	}
}
