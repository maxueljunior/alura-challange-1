package br.com.leuxam.alura.challange1.domain.videos;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCriarVideos(
		Long idCategoria,
		@NotBlank
		String titulo,
		@NotBlank
		String descricao,
		@NotBlank
		@URL
		String url) {
	
}
