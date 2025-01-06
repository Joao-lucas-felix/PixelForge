package br.com.pixelforge.repositories;

import br.com.pixelforge.domain.PixelArt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PixelArtRepository extends JpaRepository<PixelArt, Long> {
    @Query("SELECT p FROM PixelArt p WHERE p.name LIKE LOWER(CONCAT ('%',:name,'%'))")
    Page<PixelArt> findByName(String name, Pageable pageable);
}
