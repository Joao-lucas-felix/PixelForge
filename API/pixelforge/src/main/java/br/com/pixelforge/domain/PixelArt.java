package br.com.pixelforge.domain;

import br.com.pixelforge.domain.DTOs.PixelArtDto;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

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
    @Column(name = "is_free_use")
    private Boolean isFreeUse;
    @Column(name = "file_path")
    private String filePath;

    private Set<String> artTags;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public PixelArt(@NotNull PixelArtDto dto){
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.isFreeUse = dto.getIsFreeUse();
        this.artTags = dto.getArtTags();
        this.user = dto.getUser();
    }
}
