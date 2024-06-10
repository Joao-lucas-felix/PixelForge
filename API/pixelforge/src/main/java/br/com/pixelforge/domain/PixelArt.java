package br.com.pixelforge.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.MediaType;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pixel_arts")
public class PixelArt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column(name = "media_type")
    private String mediaType;
    @Column(name = "file_path")
    private String filePath;
}
