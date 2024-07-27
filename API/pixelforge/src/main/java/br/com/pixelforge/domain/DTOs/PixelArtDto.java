package br.com.pixelforge.domain.DTOs;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PixelArtDto extends RepresentationModel<PixelArtDto> implements Serializable {

    @Serial
    private static final  long serialVersionUID = 1L;
    private Long key;
    private String name;
    private String description;
    private String originalFileName;
    private Boolean isFreeUse;
    private String userName;
}
