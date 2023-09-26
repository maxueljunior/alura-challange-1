package br.com.leuxam.alura.challange1.unittest.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.leuxam.alura.challange1.domain.user.Permissions;
import br.com.leuxam.alura.challange1.domain.user.Users;

class UsersTest {

	@Test
	@DisplayName("Cria um usuario corretamente")
	void test_cenario01() {
		var usuario = mock();
		
		assertEquals("maxuel", usuario.getUsername());
		assertEquals("12345", usuario.getPassword());
		
		System.out.println(usuario.getAuthorities());
		assertEquals("[ADMIN]", usuario.getAuthorities().toString());
	}
	
	private Users mock() {
		var permissao = new Permissions(1L, "ADMIN");
		var usuario = new Users(1L ,"maxuel", "12345", true, permissao);
		
		return usuario;
	}
}
