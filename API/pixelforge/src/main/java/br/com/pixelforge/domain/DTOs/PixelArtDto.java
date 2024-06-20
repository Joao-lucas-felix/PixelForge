package br.com.pixelforge.domain.DTOs;

import br.com.pixelforge.domain.User;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PixelArtDto  implements Serializable {

    @Serial
    private static final  long serialVersionUID = 1L;
    private String name;
    private String description;
    private String originalFileName;
    private Boolean isFreeUse;
    private String userName;
}
