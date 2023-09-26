package br.com.leuxam.alura.challange1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leuxam.alura.challange1.domain.user.CredenciaisException;
import br.com.leuxam.alura.challange1.domain.user.DadosLogin;
import br.com.leuxam.alura.challange1.domain.user.Users;
import br.com.leuxam.alura.challange1.infra.security.DadosAutenticacaoJWT;
import br.com.leuxam.alura.challange1.infra.security.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<DadosAutenticacaoJWT> login(
			@RequestBody @Valid DadosLogin dados){
		var authenticationToken = new UsernamePasswordAuthenticationToken(dados.username()
				, dados.password());
		try {
			var authentication = authenticationManager.authenticate(authenticationToken);
			var tokenJWT = tokenService.gerarToken((Users) authentication.getPrincipal());
			return ResponseEntity.ok().body(new DadosAutenticacaoJWT(tokenJWT));
		}catch(AuthenticationException ex) {
			throw new CredenciaisException("Usuario e senha incorretos");
		}
	}
}
