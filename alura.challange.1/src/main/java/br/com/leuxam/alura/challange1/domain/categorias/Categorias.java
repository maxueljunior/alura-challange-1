package br.com.leuxam.alura.challange1.domain.categorias;

import java.util.ArrayList;
import java.util.List;

import br.com.leuxam.alura.challange1.domain.videos.Videos;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Categorias")
@Table(name = "tb_categoria")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Categorias {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	private String cor;
	
	public Categorias(DadosCriacaoCategoria dados) {
		this.titulo = dados.titulo();
		this.cor = dados.cor();
	}

	public void atualizar(DadosAtualizacaoCategoria dados) {
		if(dados.titulo() != null) {
			this.titulo = dados.titulo();
		}
		
		if(dados.cor() != null) {
			this.cor = dados.cor();
		}
	}
}
