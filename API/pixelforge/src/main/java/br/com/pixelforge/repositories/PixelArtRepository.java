package br.com.pixelforge.repositories;

import br.com.pixelforge.domain.PixelArt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixelArtRepository extends JpaRepository<PixelArt, Long> {
}
