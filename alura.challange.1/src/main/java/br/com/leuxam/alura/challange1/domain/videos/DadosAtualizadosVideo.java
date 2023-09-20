package br.com.leuxam.alura.challange1.domain.videos;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizadosVideo(
		@NotNull
		Long id,
		Long idCategoria,
		String titulo,
		String descricao,
		String url) {
}
