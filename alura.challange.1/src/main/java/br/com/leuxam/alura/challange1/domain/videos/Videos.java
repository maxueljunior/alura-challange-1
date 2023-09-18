package br.com.leuxam.alura.challange1.domain.videos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	
	public Videos(DadosCriarVideos dados) {
		this.titulo = dados.titulo();
		this.descricao = dados.descricao();
		this.url = dados.url();
	}
}
