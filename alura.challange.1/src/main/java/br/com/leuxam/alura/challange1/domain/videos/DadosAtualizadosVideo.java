package br.com.leuxam.alura.challange1.domain.videos;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizadosVideo(
		@NotNull
		Long id,
		String titulo,
		String descricao,
		String url) {
}
