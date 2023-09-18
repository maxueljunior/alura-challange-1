package br.com.leuxam.alura.challange1.domain.videos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosRepository extends JpaRepository<Videos, Long>{
	
}
