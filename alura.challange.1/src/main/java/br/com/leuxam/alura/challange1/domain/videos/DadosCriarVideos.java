package br.com.leuxam.alura.challange1.domain.videos;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;

public record DadosCriarVideos(
		@NotBlank
		String titulo,
		@NotBlank
		String descricao,
		@NotBlank
		@URL
		String url) {
	
}
