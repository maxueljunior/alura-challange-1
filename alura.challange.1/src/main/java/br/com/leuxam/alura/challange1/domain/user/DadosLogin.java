package br.com.leuxam.alura.challange1.domain.user;

import jakarta.validation.constraints.NotBlank;

public record DadosLogin(
		@NotBlank
		String username,
		@NotBlank
		String password) {

}
