package br.com.leuxam.alura.challange1.domain.categorias;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizacaoCategoria(
		@NotNull
		Long id,
		String titulo,
		@Pattern(regexp = "^$|#[A-Z0-9]{6}")
		String cor) {
	
}
