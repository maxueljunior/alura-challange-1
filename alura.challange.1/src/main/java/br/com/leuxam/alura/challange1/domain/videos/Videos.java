package br.com.leuxam.alura.challange1.domain.videos;

import br.com.leuxam.alura.challange1.domain.categorias.Categorias;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Videos")
@Table(name = "tb_videos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Videos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	private String descricao;
	private String url;
	private Boolean ativo;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categorias categorias;
	
	public Videos(DadosCriarVideos dados, Categorias categoria) {
		this.titulo = dados.titulo();
		this.descricao = dados.descricao();
		this.url = dados.url();
		this.ativo = true;
		this.categorias = categoria;
	}

	public void atualizarDados(@Valid DadosAtualizadosVideo dados) {
		if(dados.titulo() != null) {
			this.titulo = dados.titulo();
		}
		if(dados.descricao() != null) {
			this.descricao = dados.descricao();
		}
		if(dados.url() != null) {
			this.url = dados.url();
		}
	}

	public void desativar() {
		if(this.ativo == false) {
			throw new VideoDesativadoException("O video já está desativado");
		}
		this.ativo = false;
	}
}







