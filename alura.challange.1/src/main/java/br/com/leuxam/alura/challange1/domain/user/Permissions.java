package br.com.leuxam.alura.challange1.domain.user;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Permissions")
@Table(name = "tb_permissions")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Permissions implements GrantedAuthority{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricao;

	@Override
	public String getAuthority() {
		return this.descricao;
	}
	
}
