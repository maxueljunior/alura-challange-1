package br.com.leuxam.alura.challange1.domain.categorias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCriacaoCategoria(
		@NotBlank
		String titulo,
		@NotBlank
		@Pattern(regexp = "#[A-Z0-9]{6}")
		String cor) {
	
}
