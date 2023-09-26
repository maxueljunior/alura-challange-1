package br.com.leuxam.alura.challange1.integrationtest.deserialization;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.leuxam.alura.challange1.domain.categorias.DadosDetalhamentoCategorias;
import br.com.leuxam.alura.challange1.domain.videos.DadosDetalhamentoVideos;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WrapperVideos {
	
	@JsonProperty("content")
	List<DadosDetalhamentoVideos> content;
	
}
