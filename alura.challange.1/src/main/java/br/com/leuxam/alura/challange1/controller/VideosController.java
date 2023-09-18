package br.com.leuxam.alura.challange1.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.leuxam.alura.challange1.domain.videos.DadosCriarVideos;
import br.com.leuxam.alura.challange1.domain.videos.DadosDetalhamentoVideos;
import br.com.leuxam.alura.challange1.domain.videos.Videos;
import br.com.leuxam.alura.challange1.domain.videos.VideosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/videos")
public class VideosController {
	
	@Autowired
	private VideosRepository repository;
	
	@GetMapping
	public ResponseEntity<List<DadosDetalhamentoVideos>> findAll() {
		var videos = repository.findAll().stream()
				.map(DadosDetalhamentoVideos::new)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(videos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoVideos> findById(
			@PathVariable(name = "id") Long id){
		var video = repository.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoVideos(video));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoVideos> save(@RequestBody @Valid DadosCriarVideos dados,
			UriComponentsBuilder uriBuilder){
		var video = new Videos(dados);
		repository.save(video);
		URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoVideos(video));
	}
}
